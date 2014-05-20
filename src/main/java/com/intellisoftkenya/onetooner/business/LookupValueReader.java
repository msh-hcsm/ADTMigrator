package com.intellisoftkenya.onetooner.business;

import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Reads the primary key of a given item in a destination database lookup table.
 *
 * @author gitahi
 */
public class LookupValueReader {

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    /**
     * Reads the integer primary key value of a lookup table. The primary key
     * column name is assumed to be id. The column name by which to query for 
     * value is assumed to be name.
     * 
     * @param table the lookup table
     * @param value the value to look for in column
     * @return the integer primary value
     * @throws java.sql.SQLException
     */
    public Integer readId(String table, String value) throws SQLException {
        return readId(table, "name", value);
    }

    /**
     * Reads the integer primary key value of a lookup table. The primary key
     * column name is assumed to be id.
     * 
     * @param table the lookup table
     * @param column the column by which to query for value
     * @param value the value to look for in column
     * @return the integer primary value
     * @throws java.sql.SQLException
     */
    public Integer readId(String table, String column, String value) throws SQLException {
        return readId(table, "id", column, value);
    }

    /**
     * Reads the integer primary key value of a lookup table.
     * 
     * @param table the lookup table
     * @param pk the name of the primary key column
     * @param column the column by which to query for value
     * @param value the value to look for in column
     * @return the integer primary value
     * @throws java.sql.SQLException
     */
    public Integer readId(String table, String pk, String column, String value) throws SQLException {
        Integer id = null;
        String query = "SELECT " + pk + " FROM " + table
                + " WHERE " + column + " = '" + value + "'";
        ResultSet rs = dse.executeQuery(query);
        if (rs.next()) {
            id = rs.getInt("id");
        }
        dse.close(rs);
        return id;
    }
}
