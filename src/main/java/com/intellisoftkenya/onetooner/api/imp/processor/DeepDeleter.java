package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.PropertyManager;
import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * Performs a deep delete. Unlike other {@link ExtraProcessor}s, this
 * class is called directly at the end of the deletion/clearing
 * process for all the other tables.
 *
 * @author gitahi
 */
public class DeepDeleter implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(IdentifierTypeCreator.class.getName());

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    private final boolean deepDelete = Boolean.parseBoolean(PropertyManager.getProperty("deep.delete"));

    @Override
    public void process(OneToOne oto) throws Exception {
        LOGGER.log(Level.INFO, "Deep delete is disabled. Exiting...");
        if (!deepDelete) {
            return;
        }
        List<String> tables = new ArrayList<>();
        tables.add("drug");
        tables.add("drug_category");
        tables.add("drug_type");
        tables.add("dispensing_unit");
        tables.add("drug_form");
        tables.add("account_type");
        tables.add("cdrr_category");
        tables.add("identifier_type");
        tables.add("regimen_status");
        tables.add("service_type");

        LOGGER.log(Level.INFO, "Initiating deep delete for {0} tables.", new Object[]{tables.size()});

        for (String table : tables) {
            String delete = "DELETE FROM `" + table + "`";
            LOGGER.log(Level.FINEST, "Deep deleting {0} with statement: {1}.", new Object[]{table, delete});
            int affected;
            try {
                affected = dse.executeUpdate(delete, false);
                LOGGER.log(Level.INFO, "Deep deleted {0} records from table {1}.", new Object[]{affected, table});
            } catch (Exception ex) {
                LOGGER.log(Level.INFO, "An error occured when attemting deep delete for table {0}.", new Object[]{table});
                throw ex;
            }
        }

        LOGGER.log(Level.INFO, "Completed deep delete for {0} tables.", new Object[]{tables.size()});
    }
}
