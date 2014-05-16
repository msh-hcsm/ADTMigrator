package com.intellisoftkenya.onetooner.dao;

import com.intellisoftkenya.onetooner.log.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLoger(SqlExecutor.class.getName());

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
     * @throws java.sql.SQLException
     */
    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = createStatement();
        ResultSet rs = stmt.executeQuery(query);
        LOGGER.log(Level.FINEST, query);
        return rs;
    }

    /**
     * Executes an insert or update statement. This method will call
     * connection.commit() if connection.getAutoCommit() returns false;
     *
     * @param update the statement to execute.
     * @param generatedValue whether or not to return the auto-generated integer
     * value of an auto-increment database column
     *
     * @return the auto-generated integer value if specified, the number of
     * affected rows otherwise.
     * @throws java.sql.SQLException
     */
    public int executeUpdate(String update, boolean generatedValue) throws SQLException {
        ResultSet rs;
        int ret;
        Statement stmt = createStatement();
        if (!generatedValue) {
            ret = stmt.executeUpdate(update);
        } else {
            ret = stmt.executeUpdate(update, Statement.RETURN_GENERATED_KEYS);
            LOGGER.log(Level.FINEST, update);
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                ret = rs.getInt(1);
            }
        }
        if (!connection.getAutoCommit()) {
            connection.commit();
        }
        return ret;
    }

    /**
     * Prepares an SQL statement.
     *
     * @param sql the statement to prepare
     *
     * @return the PreparedStatement
     *
     * @throws java.sql.SQLException
     */
    public PreparedStatement createPreparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    /**
     * Executes a batch of commands and then clears the batch thereafter. This
     * method will call connection.commit() if connection.getAutoCommit()
     * returns false;
     *
     * @param pStmt The PreparedStatement containing the batch.
     *
     * @return an array of integers containing the number of rows affected by
     * each command.
     * 
     * @throws java.sql.SQLException
     */
    public int[] executeBatch(PreparedStatement pStmt) throws SQLException {
        int[] ret;
        ret = pStmt.executeBatch();
        if (!connection.getAutoCommit()) {
            connection.commit();
        }
        pStmt.clearBatch();
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
                LOGGER.log(Level.SEVERE, null, ex);
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
     *
     * @throws java.sql.SQLException
     */
    protected Statement createStatement() throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt;
    }
}
