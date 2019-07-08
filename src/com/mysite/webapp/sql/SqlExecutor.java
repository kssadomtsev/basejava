package com.mysite.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutor<T> {
    T sqlExecute(PreparedStatement ps) throws SQLException;
}