package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.business.AuditValues;
import com.intellisoftkenya.onetooner.business.LookupValueReader;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adds an account with the name 99 to the list of accounts. This account is
 * widely referenced by ADT stock transactions but is not in the ADT source/
 * destination lookup table.
 *
 * @author gitahi
 */
public class Account99Includer implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(Account99Includer.class.getName());

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();
    private final AuditValues auditValues = new AuditValues();

    @Override
    public void process(OneToOne oto) throws Exception {
        int adjustmentAccountType = new LookupValueReader().readId("account_type", "name", "Adjustment");
        String insert = "INSERT INTO `account`(name, account_type_id, uuid, created_by, created_on) "
                + "VALUES('99', " + adjustmentAccountType + ", '" + auditValues.uuid()
                + "'," + auditValues.createdBy() + " , '" + auditValues.createdOn() + "')";
        try {
            dse.executeUpdate(insert, false);
            LOGGER.log(Level.FINEST, "Added account named 99 with statement:\n''{0}''", insert);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to add account named 99 with statement: \n'" + insert + "'", ex);
        }
    }
}
