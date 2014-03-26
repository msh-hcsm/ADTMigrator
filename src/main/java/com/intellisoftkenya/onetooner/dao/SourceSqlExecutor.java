package com.intellisoftkenya.onetooner.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A data access object for the Source database.
 *
 * @author gitahi
 */
public class SourceSqlExecutor extends SqlExecutor {

    protected static SqlExecutor instance;

    private SourceSqlExecutor() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            connection = DriverManager.getConnection("jdbc:odbc:adt_dsn");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SourceSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static SqlExecutor getInstance() {
        if (instance == null) {
            instance = new SourceSqlExecutor();
        }
        return instance;
    }
}
