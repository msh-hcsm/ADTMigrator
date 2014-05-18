package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.Column;
import com.intellisoftkenya.onetooner.data.LookupValue;
import com.intellisoftkenya.onetooner.data.OneToOne;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Processes a {@link LookupValue} to retrieve the primary key and then resets
 * the value of the affected destination {@link Column#value} to the retrieved 
 * value.
 *
 * @author gitahi
 */
public class LookupValuePkProcessor implements ExtraProcessor {

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) throws Exception {
        for (Column source : oto.getColumnMappings().keySet()) {
            if (source.getName() == null) {
                Column destination = oto.getColumnMappings().get(source);
                LookupValue lookupValue = (LookupValue) destination.getValue();
                destination.setValue(readId(lookupValue.getTable(), 
                        lookupValue.getValue()));
            }
        }
    }

    private Integer readId(String table, String value) throws SQLException {
        return readId(table, "name", value);
    }

    private Integer readId(String table, String column, String value) throws SQLException {
        return readId(table, "id", column, value);
    }

    private Integer readId(String table, String pk, String column, String value) throws SQLException {
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
