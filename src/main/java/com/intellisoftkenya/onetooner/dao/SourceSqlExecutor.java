package com.intellisoftkenya.onetooner.dao;

import com.intellisoftkenya.onetooner.PropertyManager;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A data access object for the Source database.
 *
 * @author gitahi
 */
public class SourceSqlExecutor extends SqlExecutor {

    private static final Logger LOGGER = LoggerFactory.getLoger(SourceSqlExecutor.class.getName());
    protected static SqlExecutor instance;

    private String driver = PropertyManager.getProperty("source.driver");
    private String url = PropertyManager.getProperty("source.url");
    private String username = PropertyManager.getProperty("source.username");
    private String password = PropertyManager.getProperty("source.password");

    private SourceSqlExecutor() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public static SqlExecutor getInstance() {
        if (instance == null) {
            instance = new SourceSqlExecutor();
        }
        return instance;
    }

    @Override
    public void close() {
        super.close();
        instance = null;
    }
}
