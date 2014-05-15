package com.intellisoftkenya.onetooner.dao;

import com.intellisoftkenya.onetooner.log.LoggerFactory;
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
public class DestinationSqlExecutor extends SqlExecutor {

    private static final Logger LOGGER = LoggerFactory.getLoger(DestinationSqlExecutor.class.getName());
    protected static SqlExecutor instance;

    private DestinationSqlExecutor() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fdt", "root", "2806");
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public static SqlExecutor getInstance() {
        if (instance == null) {
            instance = new DestinationSqlExecutor();
        }
        return instance;
    }

    public void executeUpdate(String update) {
        PreparedStatement pStmt;
        try {
            pStmt = connection.prepareStatement(update);
            pStmt.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
