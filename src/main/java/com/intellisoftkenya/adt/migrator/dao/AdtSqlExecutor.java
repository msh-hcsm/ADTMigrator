package com.intellisoftkenya.adt.migrator.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A data access object for the ADT database.
 * 
 * @author gitahi
 */
public class AdtSqlExecutor {

    private static AdtSqlExecutor instance;
    private Connection connection;

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

    public static AdtSqlExecutor getInstance() {
        if (instance == null) {
            instance = new AdtSqlExecutor();
        }
        return instance;
    }

    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            Statement stmt = createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(AdtSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                if (autoCloseable instanceof ResultSet) {
                    Statement stmt = ((ResultSet) autoCloseable).getStatement();
                    autoCloseable.close();
                    if (stmt != null) {
                        close(stmt);
                    }
                    return;
                }
                autoCloseable.close();
            } catch (Exception ex) {
                Logger.getLogger(AdtSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Statement createStatement() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(AdtSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stmt;
    }
}
