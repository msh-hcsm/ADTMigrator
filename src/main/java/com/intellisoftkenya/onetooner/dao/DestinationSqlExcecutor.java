package com.intellisoftkenya.onetooner.dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A data access object for the Destination database.
 *
 * @author gitahi
 */
public class DestinationSqlExcecutor extends SqlExecutor {

    protected static SqlExecutor instance;

    private DestinationSqlExcecutor() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fdt", "root", "2806");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SourceSqlExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static SqlExecutor getInstance() {
        if (instance == null) {
            instance = new DestinationSqlExcecutor();
        }
        return instance;
    }

    public void executeUpdate(String update) {
        PreparedStatement pStmt;
        try {
            pStmt = connection.prepareStatement(update);
            pStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DestinationSqlExcecutor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
