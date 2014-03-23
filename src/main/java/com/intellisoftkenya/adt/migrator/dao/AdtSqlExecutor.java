package com.intellisoftkenya.adt.migrator.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A data access object for the ADT database.
 *
 * @author gitahi
 */
public class AdtSqlExecutor extends SqlExecutor {

    protected static SqlExecutor instance;

    private AdtSqlExecutor() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            connection = DriverManager.getConnection("jdbc:odbc:adt_dsn");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdtSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdtSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static SqlExecutor getInstance() {
        if (instance == null) {
            instance = new AdtSqlExecutor();
        }
        return instance;
    }
}
