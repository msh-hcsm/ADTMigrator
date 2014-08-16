package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.business.Constants;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fuzzily associates patient transactions with visits. The logic is a bit 
 * complicated and I haven't the time to document it now. Hopefully I'll not
 * neglect to add it. In the meantime please just read the code.
 * 
 * @author gitahi
 */
public class TransactionVisitUpdater implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(TransactionVisitUpdater.class.getName());

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) throws Exception {
        Map<String, List<Integer>> visitMap = fillVisits();
        Map<String, List<List<Integer>>> transactionMap = fillTransactions();

        List<Map<Integer, List<Integer>>> visitTxMapList = new ArrayList<>();

        for (String artId : transactionMap.keySet()) {
            visitTxMapList.add(matchVisitsToTransactions(visitMap.get(artId),
                    transactionMap.get(artId), artId));
        }

        updateTransactions(visitTxMapList);
    }

    private Map<String, List<Integer>> fillVisits() throws SQLException {
        Map<String, List<Integer>> visitMap = new LinkedHashMap<>();

        String selectVisit = "SELECT\n"
                + "`visit`.`id`,\n"
                + "`patient`.`legacy_pk` AS art_id\n"
                + "FROM\n"
                + "`visit`\n"
                + "INNER JOIN\n"
                + "`patient` ON `visit`.`patient_id` = `patient`.`id`\n"
                + "ORDER BY art_id, `visit`.`id`";

        ResultSet rs = dse.executeQuery(selectVisit);
        while (rs.next()) {
            String artId = rs.getString("art_id");
            int visitId = rs.getInt("id");
            List<Integer> visitIds = visitMap.get(artId);
            if (visitIds == null) {
                visitIds = new ArrayList<>();
                visitIds.add(visitId);
                visitMap.put(artId, visitIds);
            } else {
                visitIds.add(visitId);
            }
        }
        dse.close(rs);

        return visitMap;
    }

    private Map<String, List<List<Integer>>> fillTransactions() throws SQLException {
        Map<String, List<List<Integer>>> transactionsMap = new LinkedHashMap<>();

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
                + "art_id, `transaction`.`id`";

        ResultSet rs = dse.executeQuery(selectTransaction);
        while (rs.next()) {
            String artId = rs.getString("art_id");
            int txId = rs.getInt("id");
            List<List<Integer>> transactionIdGroups = transactionsMap.get(artId);
            if (transactionIdGroups == null) {
                transactionIdGroups = new ArrayList<>();

                List<Integer> txIdGroup = new ArrayList<>();
                txIdGroup.add(txId);

                transactionIdGroups.add(txIdGroup);

                transactionsMap.put(artId, transactionIdGroups);
            } else {
                List<Integer> lastTxGroup = transactionIdGroups.get(transactionIdGroups.size() - 1);
                int lastTxId = lastTxGroup.get(lastTxGroup.size() - 1);
                if (lastTxId + 1 == txId) {
                    lastTxGroup.add(txId);
                } else {
                    List<Integer> newTxGroup = new ArrayList<>();
                    newTxGroup.add(txId);
                    transactionIdGroups.add(newTxGroup);
                }
            }
        }
        dse.close(rs);

        return transactionsMap;
    }

    private Map<Integer, List<Integer>> matchVisitsToTransactions(List<Integer> visitIds,
            List<List<Integer>> transactionIdGroups, String artId) {
        if (visitIds == null || visitIds.isEmpty()) {
            LOGGER.log(Level.WARNING, "Transactions for Patient with ART ID: ''{0}'' "
                    + "could not be fuzzily be mapped to visits. No visits found.", new Object[]{artId});
            return null;
        }
        if (transactionIdGroups == null || transactionIdGroups.isEmpty()) {
            LOGGER.log(Level.WARNING, "Visits for Patient with ART ID: ''{0}'' "
                    + "could not be fuzzily be mapped to transactions. No transactions found.", new Object[]{artId});
            return null;
        }

        int n = transactionIdGroups.size();
        int m = visitIds.size();

        Map<Integer, List<Integer>> visitTxMap = new LinkedHashMap<>();

        Integer visitId = visitIds.get(0);
        List<Integer> transactionIdGroup;

        for (int i = 0; i < n; i++) {
            transactionIdGroup = transactionIdGroups.get(i);
            if (i <= (m - 1)) {
                visitId = visitIds.get(i);
                visitTxMap.put(visitId, transactionIdGroup);
            } else {
                visitTxMap.get(visitId).addAll(transactionIdGroup);
            }
        }
        return visitTxMap;
    }

    private void updateTransactions(List<Map<Integer, List<Integer>>> visitTxMapList) throws SQLException {
        String updateTransaction = "UPDATE `transaction` SET `visit_id` = ? WHERE `transaction`.`id` = ?";
        PreparedStatement pStmt = dse.createPreparedStatement(updateTransaction);
        int rowCount = 0;
        int batchNo = 1;

        for (Map<Integer, List<Integer>> map : visitTxMapList) {
            if (map != null) {
                for (Integer visitId : map.keySet()) {
                    for (Integer txId : map.get(visitId)) {
                        rowCount++;
                        pStmt.setInt(1, visitId);
                        pStmt.setInt(2, txId);
                        pStmt.addBatch();
                        dse.logPreparedStatement(pStmt);
                        if (rowCount != 0 && rowCount % SqlExecutor.TRANSACTION_BATCH_SIZE == 0) {
                            dse.executeBatch(pStmt);
                            LOGGER.log(Level.FINER, "Commited transaction batch #{0}.",
                                    new Object[]{batchNo});
                            batchNo++;
                        }
                    }
                }
            }
        }

        dse.executeBatch(pStmt);
        pStmt.clearBatch();
        LOGGER.log(Level.INFO, "Updated {0} transaction(s) with visit information.",
                new Object[]{rowCount});
    }
}
