package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Goes through each transaction item and determines if it represents drug units
 * in or drug units out. That determination is made by examining the transaction
 * type involved. The values are then updated accordingly.
 *
 * This is necessary since the default migration puts the same drug units value
 * under units in and units out whereas only one of those attributes should have
 * a value for any given transaction item.
 *
 * @author gitahi
 */
public class UnitsInOutUpdater implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(UnitsInOutUpdater.class.getName());

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) throws Exception {
        String select = "SELECT \n"
                + "`transaction_item`.`id`, `transaction_type`.`name` AS transaction_type\n"
                + "FROM \n"
                + "`transaction`, `transaction_item`, `transaction_type`\n"
                + "WHERE \n"
                + "`transaction`.`id` = `transaction_item`.`transaction_id`\n"
                + "AND\n"
                + "`transaction_type`.`id` = `transaction`.`transaction_type_id`";
        String nullifyUnitsIn = "UPDATE transaction_item SET units_in = NULL WHERE id = ?";
        String nullifyUnitsOut = "UPDATE transaction_item SET units_out = NULL WHERE id = ?";

        PreparedStatement inPStmt = dse.createPreparedStatement(nullifyUnitsIn);
        PreparedStatement outPStmt = dse.createPreparedStatement(nullifyUnitsOut);

        ResultSet rs = null;
        try {
            int inRowCount = 0;
            int outRowCount = 0;
            
            int inBatchNo = 1;
            int outBatchNo = 1;

            rs = dse.executeQuery(select);

            while (rs.next()) {
                int txItemId = rs.getInt("id");
                String txType = rs.getString("transaction_type");
                if (nullifyUnitsIn(txType)) {
                    inRowCount++;
                    inPStmt.setInt(1, txItemId);
                    inPStmt.addBatch();
                    LOGGER.log(Level.FINEST, inPStmt.toString());
                } else {
                    outRowCount++;
                    outPStmt.setInt(1, txItemId);
                    outPStmt.addBatch();
                    LOGGER.log(Level.FINEST, outPStmt.toString());
                }
                if (inRowCount != 0 && inRowCount % SqlExecutor.TRANSACTION_BATCH_SIZE == 0) {
                    dse.executeBatch(inPStmt);
                    LOGGER.log(Level.FINER, "Commited transaction batch #{0}.",
                            new Object[]{inBatchNo});
                    inBatchNo++;
                }
                if (outRowCount != 0 && outRowCount % SqlExecutor.TRANSACTION_BATCH_SIZE == 0) {
                    dse.executeBatch(outPStmt);
                    LOGGER.log(Level.FINER, "Commited transaction batch #{0}.",
                            new Object[]{outBatchNo});
                    outBatchNo++;
                }
            }
            dse.executeBatch(inPStmt);
            dse.executeBatch(outPStmt);
            inPStmt.clearBatch();
            outPStmt.clearBatch();

            LOGGER.log(Level.FINE, "Nullified {0} transaction_item units_in values.",
                    new Object[]{inRowCount});

            LOGGER.log(Level.FINE, "Nullified {0} transaction_item units_out values.",
                    new Object[]{outRowCount});
        } finally {
            dse.close(rs);
        }
    }

    private boolean nullifyUnitsIn(String txType) {
        return "Received from".equalsIgnoreCase(txType)
                || "Ajustment (-)".equalsIgnoreCase(txType)
                || "Returns to (-)".equalsIgnoreCase(txType)
                || "Losses(-)".equalsIgnoreCase(txType)
                || "Expired(-)".equalsIgnoreCase(txType);
    }
}
