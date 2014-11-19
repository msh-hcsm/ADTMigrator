package com.intellisoftkenya.onetooner.api.imp.processor;

import au.com.bytecode.opencsv.CSVReader;
import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.data.Parameter;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
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
 * Maps drugs to DHIS UIDs based on a CSV file generated from the DHIS database
 * dataelement table. The file must contain only the two columns uid and name
 * and be named dhis-dataelements.csv.
 *
 * @author gitahi
 */
public class DhisIdMapper implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(MasterDrugListImporter.class.getName());

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) throws Exception {
        if (noDhisMappings()) {
            LOGGER.log(Level.INFO, "No DHIS ID mappings found. Attempting to map from from CSV file.");
            try {
                CSVReader reader = new CSVReader(new FileReader("dhis-dataelements.csv"));
                Map<String, String> dhisIdToNameMap = new HashMap<>();
                List<String[]> rows = reader.readAll();
                for (String[] row : rows) {
                    String dhisId = row[0];
                    String name = row[1];
                    if (dhisId != null && name != null) {
                        dhisIdToNameMap.put(dhisId, name);
                    }
                }
                update(dhisIdToNameMap);
            } catch (IOException | SQLException ex) {
                LOGGER.log(Level.SEVERE, "Failed to map DHIS IDs from CSV file.", ex);
                throw ex;
            }
        } else {
            LOGGER.log(Level.INFO, "DHIS ID mapping found. No need to map from CSV file. "
                    + "Abandoning mapping.");
        }
    }

    private boolean noDhisMappings() throws SQLException {
        return noOfDhisMappings() == 0;
    }

    private int noOfDhisMappings() throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(dhis_id) AS count FROM drug WHERE dhis_id IS NOT NULL";
        ResultSet rs = dse.executeQuery(query);
        if (rs.next()) {
            count = rs.getInt("count");
        }
        return count;
    }

    int skipped = 0;

    private int readDrugId(String name) throws SQLException {
        int id = -1;
        String query = "SELECT id FROM drug WHERE name = ?";
        List<Parameter> params = new ArrayList<>();
        params.add(new Parameter(name, Types.VARCHAR));
        try {
            ResultSet rs = dse.executeQuery(query, params);
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            int code = ex.getErrorCode();
            if (code != 1267) {
                throw ex;
            } else {
                LOGGER.log(Level.WARNING, "Skipping {0} due to encoding problems. "
                        + "Can't understand the characters.", new Object[]{name});
                skipped++;
            }
        }
        return id;
    }

    private void update(Map<String, String> dhisIdToNameMap) throws SQLException {
        String update = "UPDATE drug SET dhis_id = ? WHERE id = ?";
        PreparedStatement pStmt = dse.createPreparedStatement(update);
        int rowCount = 0;
        int batchNo = 1;
        for (String dhisId : dhisIdToNameMap.keySet()) {
            String name = dhisIdToNameMap.get(dhisId);
            int drugId = readDrugId(name);
            if (drugId > 0) {
                rowCount++;
                pStmt.setString(1, dhisId);
                pStmt.setInt(2, drugId);
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

        dse.executeBatch(pStmt);
        pStmt.clearBatch();
        LOGGER.log(Level.INFO, "Attempted to make {0} DHIS ID mappings from CSV file. Succeeded in making "
                + "{1} DHIS ID mappings. Skipped {2}.", new Object[]{rowCount, noOfDhisMappings(), skipped});
    }
}
