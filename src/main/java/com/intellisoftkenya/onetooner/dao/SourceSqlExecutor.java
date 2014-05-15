package com.intellisoftkenya.onetooner.dao;

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

    private SourceSqlExecutor() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            connection = DriverManager.getConnection("jdbc:odbc:Driver"
                    + "={Microsoft Access Driver (*.mdb, *.accdb)}; "
                    + "DBQ=C:\\ARVDatabase\\Datafiles\\ARVDispensingDatabase_be.mdb");
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
}
