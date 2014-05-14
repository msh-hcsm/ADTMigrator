package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.business.Constants;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gitahi
 */
public class TransactionVisitUpdater implements ExtraProcessor {

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) {
        String selectVisit = "SELECT\n"
                + "`visit`.`id`,\n"
                + "`patient`.`legacy_pk` AS art_id\n"
                + "FROM\n"
                + "`visit`\n"
                + "INNER JOIN\n"
                + "`patient` ON `visit`.`patient_id` = `patient`.`id`\n"
                + "ORDER BY `visit`.`id`";
        String selectTransaction = "SELECT\n"
                + "`transaction`.`id`,\n"
                + "SUBSTRING(`comments`, " 
                + (Constants.DISPENSED_TO_ART_ID_PREFIX.length() + 1) 
                + ") AS art_id\n"
                + "FROM\n"
                + "`transaction`\n"
                + "INNER JOIN\n"
                + "`transaction_item` ON `transaction`.`id` = `transaction_item`.`transaction_id`\n"
                + "WHERE \n"
                + "`comments` LIKE '" + Constants.DISPENSED_TO_ART_ID_PREFIX + "%'\n"
                + "ORDER BY\n"
                + "`transaction`.`id`;";
        String updateTransaction = "UPDATE `transaction` SET `visit_id` = ? WHERE `transaction`.`id` = ?";
        PreparedStatement pStmt = dse.createPreparedStatement(updateTransaction);

        ResultSet visitRs = null;
        ResultSet transactionRs = null;

        try {
            int rowCount = 0;
            int visitCount = 1;

            visitRs = dse.executeQuery(selectVisit);
            transactionRs = dse.executeQuery(selectTransaction);

            visitRs.next();
            while (transactionRs.next()) {
                if (visitCount == 67) {
                    System.out.println("Pause");
                }
                int visitId = visitRs.getInt("id");
                String visitArtId = visitRs.getString("art_id");

                int transactionId = transactionRs.getInt("id");
                String transactionArtId = transactionRs.getString("art_id");

                int attempts = 0;
                while (true) {
                    if (attempts > 1) {
                        System.out.println("Attempts > 1");
                        break;
                    } else {
                        if (moveToNextVisit(visitArtId, transactionArtId)) {
                            if (!visitRs.next()) {
                                System.out.println("No more visit records");
                                return;
                            } else {
                                attempts++;
                                visitCount++;
                                visitId = visitRs.getInt("id");
                                visitArtId = visitRs.getString("art_id");
                            }
                        } else {
                            rowCount++;
                            System.out.println("Row Count: " + rowCount + " and Visit Count: " + visitCount);
                            if (rowCount == 1000) {
                                System.out.println("");
                            }
                            pStmt.setInt(1, visitId);
                            pStmt.setInt(2, transactionId);
                            pStmt.addBatch();
                            if (rowCount != 0 && rowCount % SqlExecutor.TRANSACTION_BATCH_SIZE == 0) {
                                dse.executeBatch(pStmt);
                            }
                            System.out.println("Transaction: " + transactionId + " belongs to Visit: " + visitId);
                            break;
                        }
                    }
                }
            }
            dse.executeBatch(pStmt);
            pStmt.clearBatch();
        } catch (SQLException ex) {
            Logger.getLogger(TransactionVisitUpdater.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            dse.close(visitRs);
            dse.close(transactionRs);
        }
    }

    private boolean moveToNextVisit(String visitArtId, String transactionArtId) {
        return !visitArtId.equals(transactionArtId);
    }
}
