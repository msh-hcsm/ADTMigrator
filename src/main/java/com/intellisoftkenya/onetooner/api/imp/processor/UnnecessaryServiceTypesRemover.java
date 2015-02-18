package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.data.Parameter;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Deletes duplicate service types from the FDT database.
 *
 * @author gitahi
 */
public class UnnecessaryServiceTypesRemover implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(TransactionVisitUpdater.class.getName());

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) throws Exception {
        Map<String, Integer> serviceTypes = readServiceTypes();
        if (!serviceTypes.isEmpty()) {
            List<Parameter> params = new ArrayList<>();
            String placeHolder = "";
            for (Integer id : serviceTypes.values()) {
                params.add(new Parameter(id, Types.INTEGER));
                placeHolder += "?,";
            }
            placeHolder = placeHolder.substring(0, placeHolder.length() - 1);
            String delete = "DELETE FROM `service_type` WHERE `id` NOT IN (" + placeHolder + ");";
            int affected = dse.executeUpdate(delete, params, false);
            LOGGER.log(Level.INFO, "Deleted {0} duplicated service types.",
                    new Object[]{affected});
        }
    }

    private Map<String, Integer> readServiceTypes() throws SQLException {
        Map<String, Integer> serviceTypes = new HashMap<>();
        ResultSet rs = dse.executeQuery("SELECT `id`, `name` FROM `service_type`");
        while (rs.next()) {
            String name = rs.getString("name");
            if (!serviceTypes.containsKey(name)) {
                serviceTypes.put(rs.getString("name"), rs.getInt("id"));
            }
        }
        return serviceTypes;
    }
}
