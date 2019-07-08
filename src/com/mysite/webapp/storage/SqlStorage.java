package com.mysite.webapp.storage;

import com.mysite.webapp.exception.ExistStorageException;
import com.mysite.webapp.exception.NotExistStorageException;
import com.mysite.webapp.model.Resume;
import com.mysite.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
        return sqlHelper.sqlExecution("SELECT * FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.sqlExecution("UPDATE resume SET full_name=? WHERE uuid =?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }


    @Override
    public void save(Resume r) {
        sqlHelper.sqlExecution("INSERT INTO resume (uuid, full_name) VALUES (?,?) ON CONFLICT DO NOTHING", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new ExistStorageException(r.getUuid());
            }
            return null;
        });
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
        return sqlHelper.sqlExecution("SELECT * FROM resume ORDER BY full_name, uuid ASC", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>(size());
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
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
}
