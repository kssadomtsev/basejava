package com.mysite.webapp.storage.strategies;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlHelper {

    void sqlExecute(PreparedStatement ps) throws SQLException;
}
