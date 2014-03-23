package com.intellisoftkenya.adt.migrator.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Super class for other SqlExecutors. Provides basic functionality for database
 * interaction.
 *
 * @author gitahi
 */
public abstract class SqlExecutor {

    protected Connection connection;

    public Connection getConnection() {
        return connection;
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

    public int executeUpdate(String update, boolean generatedValue) {
        ResultSet rs;
        int ret = 0;
        try {
            Statement stmt = createStatement();
            if (!generatedValue) {
                ret = stmt.executeUpdate(update);
            } else {
                stmt.executeUpdate(update, Statement.RETURN_GENERATED_KEYS);
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    ret = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdtSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
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

    public void close() {
        close(connection);
    }

    protected Statement createStatement() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(AdtSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stmt;
    }
}
