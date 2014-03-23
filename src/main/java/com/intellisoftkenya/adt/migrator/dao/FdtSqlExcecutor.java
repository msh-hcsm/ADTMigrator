package com.intellisoftkenya.adt.migrator.dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A data access object for the FDT database.
 *
 * @author gitahi
 */
public class FdtSqlExcecutor extends SqlExecutor {

    protected static SqlExecutor instance;

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

    public static SqlExecutor getInstance() {
        if (instance == null) {
            instance = new FdtSqlExcecutor();
        }
        return instance;
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
}
