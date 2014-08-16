package com.intellisoftkenya.onetooner.api.imp.processor;

import au.com.bytecode.opencsv.CSVReader;
import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.business.AuditValues;
import com.intellisoftkenya.onetooner.business.LookupValueReader;
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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Imports into the drug table drugs and commodities from the master list CSV file
 * supplied by MSH. All drugs imported this way are marked standard = true. Data
 * for referenced tables such as drug types and dispensing units is created
 * if not found. All data created for referenced tables that are part of the
 * "universal metadata" is also marked standard = true.
 * 
 * The CSV file must be located in the same directory as the migrator executable
 * and named 'msh-drug-list.csv' in order to work.
 * 
 *
 * @author gitahi
 */
public class MasterDrugListImporter implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(MasterDrugListImporter.class.getName());

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();
    private final AuditValues auditValues = new AuditValues();

    public static void main(String[] args) throws Exception {
        MasterDrugListImporter importer = new MasterDrugListImporter();
        importer.process(null);
    }

    @Override
    public void process(OneToOne oto) throws Exception {
        if (noStandardDrugs()) {
            LOGGER.log(Level.INFO, "Standard drugs NOT found. Attempting to imprort master list from CSV file.");
            try {
                CSVReader reader = new CSVReader(new FileReader("msh-drug-list.csv"));
                List<String[]> drugRows = reader.readAll();
                drugRows.remove(0);//remove header row
                insert(drugRows);
            } catch (IOException | SQLException ex) {
                LOGGER.log(Level.SEVERE, "Failed to import master list from CSV file.", ex);
                throw ex;
            }
        } else {
            LOGGER.log(Level.INFO, "Standard drugs found. No need to imprort master list from CSV file. "
                    + "Abandoning import.");
        }
    }

    private boolean noStandardDrugs() throws SQLException {
        String query = "SELECT id FROM drug WHERE standard = 1";
        ResultSet rs = dse.executeQuery(query);
        return !rs.next();
    }

    private void insert(List<String[]> drugRows) throws SQLException {
        String insert = "INSERT INTO `fdt`.`drug`\n"
                + "(`name`, `kemsa_name`, `sca1_name`, `sca2_name`, `sca3_name`,\n"
                + "`abbreviation`, `drug_category_id`, `drug_type_id`, `drug_form_id`,\n"
                + "`pack_size`, `dispensing_unit_id`, `reorder_point`, `service_type_id`,\n"
                + "`cdrr_name`, `standard`, `uuid`, `created_by`, `created_on`)\n"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pStmt = dse.createPreparedStatement(insert);
        int rowCount = 0;
        int batchNo = 1;

        List<Integer> exclude = new ArrayList<>(Arrays.asList(7, 8, 9, 11, 13, 15, 16, 17, 18));
        for (String[] drugRow : drugRows) {
            rowCount++;
            for (int i = 0; i < drugRow.length; i++) {
                int index = i + 1;
                if (!exclude.contains(index)) {
                    String value = drugRow[i];
                    if ("".equals(value)) {
                        value = null;
                    } else {
                        if (index == 10) {
                            try {
                                Integer.parseInt(value);
                            } catch (NumberFormatException ex) {
                                LOGGER.log(Level.WARNING, "Value {0} for column pack_size could not "
                                        + "be converted to integer. Ignoring value.", new Object[]{value});
                                value = null;
                            }
                        }
                        if (index == 12) {
                            try {
                                Double.parseDouble(value);
                            } catch (NumberFormatException ex) {
                                LOGGER.log(Level.WARNING, "Value {0} for column reorder_point could not "
                                        + "be converted to double. Ignoring value.", new Object[]{value});
                                value = null;
                            }
                        }
                    }
                    pStmt.setObject(index, value);
                }
            }
            pStmt.setObject(7, getLookupValue("drug_category", drugRow[6]));
            pStmt.setObject(8, getLookupValue("drug_type", drugRow[7]));
            pStmt.setObject(9, getLookupValue("drug_form", drugRow[8]));
            pStmt.setObject(11, getLookupValue("dispensing_unit", drugRow[10]));
            pStmt.setObject(13, getLookupValue("service_type", drugRow[12]));
            pStmt.setObject(15, true);
            pStmt.setObject(16, auditValues.uuid());
            pStmt.setObject(17, auditValues.createdBy());
            pStmt.setObject(18, auditValues.createdOn());

            pStmt.addBatch();
            LOGGER.log(Level.FINEST, pStmt.toString());
            if (rowCount != 0 && rowCount % SqlExecutor.TRANSACTION_BATCH_SIZE == 0) {
                dse.executeBatch(pStmt);
                LOGGER.log(Level.FINER, "Commited transaction batch #{0}.",
                        new Object[]{batchNo});
                batchNo++;
            }
        }

        dse.executeBatch(pStmt);
        pStmt.clearBatch();
        LOGGER.log(Level.FINE, "Migrated {0} drugs and commodities from CSV file.",
                new Object[]{rowCount});
    }

    private Integer getLookupValue(String table, String value) throws SQLException {
        Integer id = null;
        if (value != null && !"".equals("value") && !"TBD".equals("value")) {
            id = new LookupValueReader().readId(table, value);
            if (id == null) {
                List<Parameter> params = new ArrayList<>();
                params.add(new Parameter(value, Types.VARCHAR));
                params.add(new Parameter(auditValues.uuid(), Types.VARCHAR));
                params.add(new Parameter(auditValues.createdBy(), Types.INTEGER));
                params.add(new Parameter(auditValues.createdOn(), Types.DATE));

                String insert = "INSERT INTO `"
                        + table + "`(`" + "name" + "`, `uuid`, `created_by`, `created_on`) "
                        + "VALUES(?, ?, ? , ?)";
                if ("dispensing_unit".equals(table)) {
                    insert = "INSERT INTO `"
                            + table + "`(`" + "name" + "`, `uuid`, `created_by`, `created_on`, `standard`) "
                            + "VALUES(?, ?, ? , ?, ?)";
                    params.add(new Parameter(true, Types.BOOLEAN));
                }
                id = dse.executeUpdate(insert, params, true);
            }
        }
        return id;
    }
}
