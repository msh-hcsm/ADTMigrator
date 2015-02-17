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
 *
 * Associates account types with the right transaction types.
 *
 * @author gitahi
 */
public class AccountTypeTxTypeSetter implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(VisitUpdater.class.getName());

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) throws Exception {

        LOGGER.log(Level.INFO, "Attempting to automatically map account "
                + "types to transaction types.");

        Map<String, List<String>> mapping = mapAccountTypesToTxTypes();

        Map<String, Integer> accountTypes = readAccountTypes();
        Map<String, Integer> txTypes = readTxTypes();

        int counter = 0;
        for (String accountTypeName : mapping.keySet()) {
            Integer accountTypeId = accountTypes.get(accountTypeName);
            if (accountTypeId == null) {
                LOGGER.log(Level.WARNING, "No account type named '{0}' in the database.",
                        new Object[]{accountTypeName});
                continue;
            }
            List<String> txTypeNames = mapping.get(accountTypeName);
            for (String txTypeName : txTypeNames) {
                Integer txTypeId = txTypes.get(txTypeName);
                String sql = "INSERT INTO `account_type_transaction_type`(`account_type_id`, "
                        + "`transaction_type_id`) VALUES(?, ?)";
                if (txTypeId == null) {
                    LOGGER.log(Level.WARNING, "No transaction type named '{0}' in the database.",
                            new Object[]{txTypeId});
                    continue;
                }
                List<Parameter> params = new ArrayList<>();
                params.add(new Parameter(accountTypeId, Types.INTEGER));
                params.add(new Parameter(txTypeId, Types.INTEGER));
                dse.executeUpdate(sql, params, false);
                counter++;
            }
        }
        LOGGER.log(Level.INFO, "{0} account type to transaction type mappings "
                + "were created automatically.",
                new Object[]{counter});
    }

    private Map<String, List<String>> mapAccountTypesToTxTypes() {
        Map<String, List<String>> mapping = new HashMap<>();
        {
            String accountType = "Supplier";
            List<String> txTypes = new ArrayList<>();
            txTypes.add("Received from");
            mapping.put(accountType, txTypes);
        }
        {
            String accountType = "Store";
            List<String> txTypes = new ArrayList<>();
            txTypes.add("Received from");
            txTypes.add("Returns from (+)");
            txTypes.add("Returns to (-)");
            txTypes.add("Issued To");
            txTypes.add("Losses(-)");
            txTypes.add("Expired(-)");
            txTypes.add("Ajustment (+)");
            txTypes.add("Ajustment (-)");
            mapping.put(accountType, txTypes);
        }
        {
            String accountType = "Patient";
            List<String> txTypes = new ArrayList<>();
            txTypes.add("Dispensed to Patients");
            mapping.put(accountType, txTypes);
        }
        {
            String accountType = "Adjustment";
            List<String> txTypes = new ArrayList<>();
            txTypes.add("Ajustment (+)");
            txTypes.add("Ajustment (-)");
            mapping.put(accountType, txTypes);
        }
        return mapping;
    }

    private Map<String, Integer> readAccountTypes() throws SQLException {
        Map<String, Integer> accountTypes = new HashMap<>();
        ResultSet rs = dse.executeQuery("SELECT `id`, `name` FROM `account_type`");
        while (rs.next()) {
            accountTypes.put(rs.getString("name"), rs.getInt("id"));
        }
        return accountTypes;
    }

    private Map<String, Integer> readTxTypes() throws SQLException {
        Map<String, Integer> accountTypes = new HashMap<>();
        ResultSet rs = dse.executeQuery("SELECT `id`, `name` FROM `transaction_type`");
        while (rs.next()) {
            accountTypes.put(rs.getString("name"), rs.getInt("id"));
        }
        return accountTypes;
    }
}
