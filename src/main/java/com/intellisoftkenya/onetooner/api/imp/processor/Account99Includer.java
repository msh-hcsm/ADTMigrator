package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.business.AuditValues;
import com.intellisoftkenya.onetooner.business.LookupValueReader;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.data.Parameter;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
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
        int adjustmentAccountType = new LookupValueReader().readId("account_type", "name", "Store");
        String insert = "INSERT INTO `account`(`name`, `account_type_id`, `uuid`, `created_by`, `created_on`) "
                + "VALUES(?, ?, ?, ?, ?)";
        List<Parameter> params = new ArrayList<>();
        params.add(new Parameter("99", Types.VARCHAR));
        params.add(new Parameter(adjustmentAccountType, Types.INTEGER));
        params.add(new Parameter(auditValues.uuid(), Types.VARCHAR));
        params.add(new Parameter(auditValues.createdBy(), Types.INTEGER));
        params.add(new Parameter(auditValues.createdOn(), Types.DATE));
        try {
            dse.executeUpdate(insert, params, false);
            LOGGER.log(Level.FINEST, "Added account named 99");
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to add account named 99");
            throw ex;
        }
    }
}
