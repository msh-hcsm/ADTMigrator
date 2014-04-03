package com.intellisoftkenya.onetooner.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public static final int TRANSACTION_BATCH_SIZE = 1000;
    protected Connection connection;

    /**
     * Avails the connection associated with this class for use in ways not
     * provided here.
     * 
     * @return the connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Executes am SQL query.
     * 
     * @param query the query to execute
     * 
     * @return the ResultSet returned by the query
     */
    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            Statement stmt = createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(SourceSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    /**
     * Executes an insert or update statement. This method will call connection.commit()
     * if connection.getAutoCommit() returns false;
     * 
     * @param update the statement to execute.
     * @param generatedValue whether or not to return the auto-generated integer 
     * value of an auto-increment database column
     * 
     * @return the auto-generated integer value if specified, the number of affected
     * rows otherwise.
     */
    public int executeUpdate(String update, boolean generatedValue) {
        ResultSet rs;
        int ret = 0;
        try {
            Statement stmt = createStatement();
            if (!generatedValue) {
                ret = stmt.executeUpdate(update);
            } else {
                ret = stmt.executeUpdate(update, Statement.RETURN_GENERATED_KEYS);
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    ret = rs.getInt(1);
                }
            }
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SourceSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    /**
     * Prepares an SQL statement.
     * 
     * @param sql the statement to prepare
     * 
     * @return the PreparedStatement
     */
    public PreparedStatement createPreparedStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Executes a batch of commands and then clears the batch thereafter.
     * This method will call connection.commit() if connection.getAutoCommit() 
     * returns false;
     * 
     * @param pStmt The PreparedStatement containing the batch.
     * 
     * @return an array of integers containing the number of rows affected by
     * each command.
     */
    public int[] executeBatch(PreparedStatement pStmt) {
        int[] ret = null;
        try {
            ret = pStmt.executeBatch();
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            pStmt.clearBatch();
        } catch (SQLException ex) {
            Logger.getLogger(SqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    /**
     * Closes an AutoCloseable resource.
     * 
     * @param autoCloseable the AutoCloseable resource to close.
     */
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
                Logger.getLogger(SourceSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Closes the connection associated with this class.
     */
    public void close() {
        close(connection);
    }

    /**
     * Creates a Statement.
     * 
     * @return the Statement created
     */
    protected Statement createStatement() {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SourceSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stmt;
    }
}
