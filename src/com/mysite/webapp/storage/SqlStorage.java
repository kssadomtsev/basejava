package com.mysite.webapp.storage;

import com.mysite.webapp.exception.ExistStorageException;
import com.mysite.webapp.exception.NotExistStorageException;
import com.mysite.webapp.exception.StorageException;
import com.mysite.webapp.model.Resume;
import com.mysite.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume r) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid =?")) {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }


    @Override
    public void save(Resume r) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?) ON CONFLICT DO NOTHING")) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new ExistStorageException(r.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }

    }

    @Override
    public void delete(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid =?")) {
            ps.setString(1, uuid);
            int rs = ps.executeUpdate();
            if (rs == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume")) {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>(size());
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            Collections.sort(list);
            return list;

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM resume")) {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
