package com.intellisoftkenya.onetooner.dao;

import com.intellisoftkenya.onetooner.PropertyManager;
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

    private String driver = PropertyManager.getProperty("destination.driver");
    private String url = PropertyManager.getProperty("destination.url");
    private String username = PropertyManager.getProperty("destination.username");
    private String password = PropertyManager.getProperty("destination.password");

    private DestinationSqlExecutor() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
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

    public void executeUpdate(String update) throws SQLException {
        PreparedStatement pStmt = connection.prepareStatement(update);
        pStmt.executeUpdate();
        connection.commit();
    }

    @Override
    public void close() {
        super.close();
        instance = null;
    }
    
    
}
