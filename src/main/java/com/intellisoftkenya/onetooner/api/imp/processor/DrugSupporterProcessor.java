package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.business.LookupValueReader;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SourceSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Migrates drug supporter data to the correct junction table in FDT.
 *
 * @author gitahi
 */
public class DrugSupporterProcessor implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(Account99Includer.class.getName());

    private final SqlExecutor sse = SourceSqlExecutor.getInstance();
    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) throws Exception {
        saveDestinationDrugMap(createDestinationDrugMap(fillSourceDrugMap()));
    }

    private Map<String, Set<Integer>> fillSourceDrugMap() throws SQLException {
        Map<String, Set<Integer>> sourceDrugMap = new LinkedHashMap<>();

        String query = "SELECT ARVDrugsID, SupportedBy, [Supported By1] AS SupportedBy1,\n"
                + "SupportedBy2, SupportedBy3, SupportedBy4 FROM tblARVDrugStockMain\n"
                + "ORDER BY ARVDrugsID ASC";
        ResultSet rs = sse.executeQuery(query);
        while (rs.next()) {
            String drudId = rs.getString("ARVDrugsID");

            Set<Integer> supporterIds = new LinkedHashSet<>();
            for (int i = 0; i < 5; i++) {
                String column = "SupportedBy";
                if (i != 0) {
                    column += i;
                }
                Integer supportedId = rs.getInt(column);
                if (supportedId != 0) {
                    supporterIds.add(supportedId);
                }
            }

            sourceDrugMap.put(drudId, supporterIds);
        }
        return sourceDrugMap;
    }

    private Map<Integer, Set<Integer>> createDestinationDrugMap(Map<String, Set<Integer>> sourceDrugMap) throws SQLException {
        Map<Integer, Set<Integer>> destinationDrugMap = new LinkedHashMap();
        for (String drudIdString : sourceDrugMap.keySet()) {
            Integer drudId = new LookupValueReader().readId("drug", drudIdString);

            Set<Integer> destSupporterIds = new LinkedHashSet<>();
            Set<Integer> sourceSupporterIds = sourceDrugMap.get(drudIdString);
            for (Integer sourceSupporterId : sourceSupporterIds) {
                Integer destSupporterId = new LookupValueReader()
                        .readId("supporting_organization", "legacy_pk", sourceSupporterId.toString());
                if (destSupporterId != null) {
                    destSupporterIds.add(destSupporterId);
                }
            }
            destinationDrugMap.put(drudId, destSupporterIds);
        }
        return destinationDrugMap;
    }

    private void saveDestinationDrugMap(Map<Integer, Set<Integer>> destinationDrugMap) throws SQLException {
        String insert = "INSERT INTO `drug_supporting_organization`(`drug_id`, `supporting_organization_id`) VALUES(?, ?)";

        PreparedStatement pStmt = dse.createPreparedStatement(insert);
        int rowCount = 0;
        int batchNo = 1;

        for (Integer drugId : destinationDrugMap.keySet()) {
            Set<Integer> supporterIds = destinationDrugMap.get(drugId);
            for (Integer supporterId : supporterIds) {
                rowCount++;
                pStmt.setInt(1, drugId);
                pStmt.setInt(2, supporterId);
                pStmt.addBatch();
                LOGGER.log(Level.FINEST, pStmt.toString());
                if (rowCount != 0 && rowCount % SqlExecutor.TRANSACTION_BATCH_SIZE == 0) {
                    dse.executeBatch(pStmt);
                    LOGGER.log(Level.FINER, "Commited transaction batch #{0}.",
                            new Object[]{batchNo});
                    batchNo++;
                }
            }
        }

        dse.executeBatch(pStmt);
        pStmt.clearBatch();
        LOGGER.log(Level.FINE, "Created {0} drug-to-supporting_organization associations.",
                new Object[]{rowCount});
    }
}
