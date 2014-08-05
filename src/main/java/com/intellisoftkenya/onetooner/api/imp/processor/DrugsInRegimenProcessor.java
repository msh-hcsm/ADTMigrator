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
 * Migrates regimen drug data to the correct junction table in FDT.
 *
 * @author gitahi
 */
public class DrugsInRegimenProcessor implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(Account99Includer.class.getName());

    private final SqlExecutor sse = SourceSqlExecutor.getInstance();
    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) throws Exception {
        saveDestinationRegimenMap(createDestinationRegimenMap(fillSourceRegimenMap()));
    }

    private Map<String, Set<String>> fillSourceRegimenMap() throws SQLException {
        Map<String, Set<String>> sourceDrugMap = new LinkedHashMap<>();

        String query = "SELECT Regimencode AS Regimencode_, Combinations AS Combinations_\n"
                + "FROM tblDrugsInRegimen\n"
                + "ORDER BY Regimencode ASC";
        ResultSet rs = sse.executeQuery(query);

        String currentRegimenId = null;
        Set<String> drugIds = null;

        while (rs.next()) {
            String regimenId = rs.getString("Regimencode_");
            if (!regimenId.equals(currentRegimenId)) {
                currentRegimenId = regimenId;
                sourceDrugMap.put(regimenId, new LinkedHashSet<String>());
                drugIds = sourceDrugMap.get(regimenId);
            }
            String drugId = rs.getString("Combinations_");
            if (drugIds != null) {
                drugIds.add(drugId);
            }
        }
        return sourceDrugMap;
    }

    private Map<Integer, Set<Integer>> createDestinationRegimenMap(Map<String, Set<String>> sourceRegimenMap) throws SQLException {
        Map<Integer, Set<Integer>> destinationRegimenMap = new LinkedHashMap();
        int uncodedRegimens = 0;
        for (String regimenIdString : sourceRegimenMap.keySet()) {
            Integer regimenId = new LookupValueReader().readId("regimen", "code", regimenIdString);
            if (regimenId == null) {
                uncodedRegimens++;
                LOGGER.log(Level.FINE, "Skipping uncoded regimen {0}.",
                        new Object[]{regimenIdString});
                continue;
            }
            Set<Integer> drugIds = new LinkedHashSet<>();
            Set<String> sourceDrugIds = sourceRegimenMap.get(regimenIdString);
            for (String sourceDrugId : sourceDrugIds) {
                Integer destDrugId = new LookupValueReader()
                        .readId("drug", sourceDrugId);
                if (destDrugId != null) {
                    drugIds.add(destDrugId);
                }
            }
            destinationRegimenMap.put(regimenId, drugIds);
        }
        if (uncodedRegimens != 0) {
            LOGGER.log(Level.WARNING, "Skipped {0} regimen-to-drug associations because the affected regimens could not "
                    + "be found in the list of coded regimens.",
                    new Object[]{uncodedRegimens});
        }
        return destinationRegimenMap;
    }

    private void saveDestinationRegimenMap(Map<Integer, Set<Integer>> destinationRegimenMap) throws SQLException {
        String insert = "INSERT INTO `regimen_drug`(`regimen_id`, `drug_id`) VALUES(?, ?)";

        PreparedStatement pStmt = dse.createPreparedStatement(insert);
        int rowCount = 0;
        int batchNo = 1;

        for (Integer regimenId : destinationRegimenMap.keySet()) {
            Set<Integer> drugIds = destinationRegimenMap.get(regimenId);
            for (Integer drugId : drugIds) {
                rowCount++;
                pStmt.setInt(1, regimenId);
                pStmt.setInt(2, drugId);
                pStmt.addBatch();
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
        LOGGER.log(Level.FINE, "Created {0} regimen-to-drug associations.",
                new Object[]{rowCount});
    }
}
