package com.mysite.webapp.storage;

import com.mysite.webapp.exception.ExistStorageException;
import com.mysite.webapp.exception.NotExistStorageException;
import com.mysite.webapp.model.ContactType;
import com.mysite.webapp.model.Resume;
import com.mysite.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.sqlExecution("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.sqlExecution("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String value = rs.getString("value");
                        if (value != null) {
                            ContactType type = ContactType.valueOf(rs.getString("type"));
                            r.setContact(type, value);
                        }
                    } while (rs.next());

                    return r;
                });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid =?")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        int rs = ps.executeUpdate();
                        if (rs == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                    }
                    deleteContacts(r, conn);
                    addContacts(r, conn);
                    return null;
                }
        );
    }


    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?) ON CONFLICT DO NOTHING")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        int rs = ps.executeUpdate();
                        if (rs == 0) {
                            throw new ExistStorageException(r.getUuid());
                        }
                    }
                    addContacts(r, conn);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.sqlExecution("DELETE FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.sqlExecution("" +
                "    SELECT * FROM resume r " +
                " LEFT JOIN contact c " +
                "        ON r.uuid = c.resume_uuid " +
                " ORDER BY full_name, uuid ASC", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>(size());
            String uuid = null;
            Resume r = null;
            while (rs.next()) {
                String uuid_new = rs.getString("uuid").trim();
                if (!uuid_new.equals(uuid)) {
                    uuid = rs.getString("uuid").trim();
                    r = new Resume(uuid, rs.getString("full_name"));
                    list.add(r);
                }
                setContact(r, rs);
            }
            return list;
        });
    }

    @Override
    public int size() {
        return sqlHelper.sqlExecution("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private void addContacts(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void setContact(Resume r, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.setContact(type, value);
        }
    }
}
