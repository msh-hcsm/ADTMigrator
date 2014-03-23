package com.intellisoftkenya.adt.migrator.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A data access object for the FDT database.
 * 
 * @author gitahi
 */
public class FdtSqlExcecutor {

    private static FdtSqlExcecutor instance;
    private Connection connection;

    private FdtSqlExcecutor() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fdt", "root", "2806");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdtSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdtSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static FdtSqlExcecutor getInstance() {
        if (instance == null) {
            instance = new FdtSqlExcecutor();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public static Connection connection() {
        return getInstance().getConnection();
    }

    public void executeUpdate(String update) {
        PreparedStatement pStmt;
        try {
            pStmt = connection.prepareStatement(update);
            pStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FdtSqlExcecutor.class.getName()).log(Level.SEVERE, null, ex);
        }

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
