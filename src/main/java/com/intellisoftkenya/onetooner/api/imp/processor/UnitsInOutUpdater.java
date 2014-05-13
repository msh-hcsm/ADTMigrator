package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
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

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) {
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

            rs = dse.executeQuery(select);

            while (rs.next()) {
                int txItemId = rs.getInt("id");
                String txType = rs.getString("transaction_type");
                if (nullifyUnitsIn(txType)) {
                    inRowCount++;
                    inPStmt.setInt(1, txItemId);
                    inPStmt.addBatch();
                } else {
                    outRowCount++;
                    outPStmt.setInt(1, txItemId);
                    outPStmt.addBatch();
                }
                if (inRowCount != 0 && inRowCount % SqlExecutor.TRANSACTION_BATCH_SIZE == 0) {
                    dse.executeBatch(inPStmt);
                }
                if (outRowCount != 0 && outRowCount % SqlExecutor.TRANSACTION_BATCH_SIZE == 0) {
                    dse.executeBatch(outPStmt);
                }
            }
            dse.executeBatch(inPStmt);
            dse.executeBatch(outPStmt);
            inPStmt.clearBatch();
            outPStmt.clearBatch();
        } catch (SQLException ex) {
            Logger.getLogger(UnitsInOutUpdater.class.getName()).log(Level.SEVERE, null, ex);
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
