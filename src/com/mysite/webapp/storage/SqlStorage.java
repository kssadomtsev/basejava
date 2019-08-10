package com.mysite.webapp.storage;

import com.mysite.webapp.exception.ExistStorageException;
import com.mysite.webapp.exception.NotExistStorageException;
import com.mysite.webapp.model.*;
import com.mysite.webapp.sql.SqlHelper;
import com.mysite.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

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
        return sqlHelper.transactionalExecute(conn -> {
                    Resume r;
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        if (!rs.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        r = new Resume(uuid, rs.getString("full_name"));
                    }
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            setContact(r, rs);
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            setSection(r, rs);
                        }
                    }
                    return r;
                }
        );
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
                    deleteProperties(r, conn, "DELETE FROM contact WHERE resume_uuid=?");
                    deleteProperties(r, conn, "DELETE FROM section WHERE resume_uuid=?");
                    addContacts(r, conn);
                    addSections(r, conn);
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
                    addSections(r, conn);
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
        return sqlHelper.transactionalExecute(conn -> {
                    Map<String, Resume> map = new LinkedHashMap<>();
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid ASC")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            String uuid = rs.getString("uuid").trim();
                            Resume r = new Resume(uuid, rs.getString("full_name"));
                            map.put(uuid, r);
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            Resume r = map.get(rs.getString("resume_uuid").trim());
                            setContact(r, rs);
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            Resume r = map.get(rs.getString("resume_uuid").trim());
                            setSection(r, rs);
                        }
                    }
                    return new ArrayList<>(map.values());
                }
        );
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

    private void addSections(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                AbstractSection section = e.getValue();
                ps.setString(3, JsonParser.write(section, AbstractSection.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteProperties(Resume r, Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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

    private void setSection(Resume r, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType sectionType = SectionType.valueOf(rs.getString("type"));
            r.setSection(sectionType, JsonParser.read(value, AbstractSection.class));
        }
    }
}
