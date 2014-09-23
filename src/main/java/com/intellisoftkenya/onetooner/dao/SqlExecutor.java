package com.intellisoftkenya.onetooner.dao;

import com.intellisoftkenya.onetooner.data.Parameter;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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
     * Executes an SQL query using a PreparedStatement.
     *
     * @param sql the SQL query
     * @param params the parameters for this SQL query
     *
     * @return the ResultSet returned by the query
     *
     * @throws java.sql.SQLException if any database related problem occures
     */
    public ResultSet executeQuery(String sql, List<Parameter> params) throws SQLException {
        PreparedStatement pStmt = connection.prepareStatement(sql);
        String paramString = "";
        if (params != null) {
            int i = 1;
            for (Parameter param : params) {
                pStmt.setObject(i, param.getValue(), param.getType());
                paramString += param.toString() + ", ";
                i++;
            }
        }

        if (!logPreparedStatement(pStmt)) {
            LOGGER.log(Level.FINEST, "{0} : {1}", new Object[]{sql, paramString});
        }

        ResultSet rs = pStmt.executeQuery();
        return rs;
    }

    /**
     * Executes an SQL query using a PreparedStatement with no parameters.
     *
     * @param sql the SQL query
     *
     * @return the ResultSet returned by the query
     *
     * @throws java.sql.SQLException if any database related problem occures
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        return executeQuery(sql, null);
    }

    /**
     * Executes an insert or sql statement using a PreparedStatement. This
     * method will call connection.commit() if connection.getAutoCommit()
     * returns false.
     *
     * @param sql the statement to execute.
     * @param params the parameter values for this statement mapped to their SQL
     * types
     * @param generatedValue whether or not to return the auto-generated integer
     * value of an auto-increment database column
     *
     * @return the auto-generated integer value if specified, the number of
     * affected rows otherwise.
     * @throws java.sql.SQLException if any database related problem occures
     */
    public int executeUpdate(String sql, List<Parameter> params,
            boolean generatedValue) throws SQLException {
        ResultSet rs;
        int ret;
        PreparedStatement pStmt;
        String paramString = "";
        if (generatedValue) {
            pStmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            pStmt = connection.prepareStatement(sql);
        }
        if (params != null) {
            int i = 1;
            for (Parameter param : params) {
                pStmt.setObject(i, param.getValue(), param.getType());
                paramString += param.toString() + ", ";
                i++;
            }
        }

        if (!logPreparedStatement(pStmt)) {
            LOGGER.log(Level.FINEST, "{0} : {1}", new Object[]{sql, paramString});
        }
        ret = pStmt.executeUpdate();
        if (generatedValue) {
            rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                ret = rs.getInt(1);
            }
        }
        if (!connection.getAutoCommit()) {
            connection.commit();
        }
        close(pStmt);
        return ret;
    }

    /**
     * Executes an insert or select statement using a PreparedStatement with no
     * parameters. This method will call connection.commit() if
     * connection.getAutoCommit() returns false.
     *
     * @param sql the statement to execute.
     * @param generatedValue whether or not to return the auto-generated integer
     * value of an auto-increment database column
     *
     * @return the auto-generated integer value if specified, the number of
     * affected rows otherwise.
     * @throws java.sql.SQLException if any database related problem occures
     */
    public int executeUpdate(String sql,
            boolean generatedValue) throws SQLException {
        return executeUpdate(sql, null, generatedValue);
    }

    /**
     * Prepares an SQL statement.
     *
     * @param sql the statement to prepare
     *
     * @return the PreparedStatement
     *
     * @throws java.sql.SQLException if any database related problem occures
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
     * @throws java.sql.SQLException if any database related problem occurs.
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
     * Logs the SQL statement returned by pStmt.toString() if available.
     *
     * @param pStmt the PreparedStatement to log
     *
     * @return true if logging was successful and false otherwise.
     */
    public boolean logPreparedStatement(PreparedStatement pStmt) {
        String[] tokens = pStmt.toString().split(":");
        if (tokens.length == 2) {
            LOGGER.log(Level.FINEST, tokens[1]);
            return true;
        }
        return false;
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
     * @throws java.sql.SQLException if any database related problem occurs.
     */
    protected Statement createStatement() throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt;
    }
}
