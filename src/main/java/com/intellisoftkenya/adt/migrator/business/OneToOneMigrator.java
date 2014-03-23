package com.intellisoftkenya.adt.migrator.business;

import com.intellisoftkenya.adt.migrator.Main;
import com.intellisoftkenya.adt.migrator.dao.AdtSqlExecutor;
import com.intellisoftkenya.adt.migrator.dao.FdtSqlExcecutor;
import com.intellisoftkenya.adt.migrator.dao.SqlExecutor;
import com.intellisoftkenya.adt.migrator.data.OneToOne;
import com.intellisoftkenya.adt.migrator.data.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gitahi
 */
public class OneToOneMigrator {

    private final SqlExecutor ase = AdtSqlExecutor.getInstance();
    private final SqlExecutor fse = FdtSqlExcecutor.getInstance();
    private final Connection connection = fse.getConnection();

    private final Map<String, Integer> referenceCache = new HashMap<String, Integer>();

    /**
     * Migrate all tables that have a one-to-one relationship between ADT and
     * FDT.
     *
     * @throws java.sql.SQLException
     */
    public void migrateOneToOnes() throws SQLException {
        {
            OneToOne oto = new OneToOne("tblDose", "dosage");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new LinkedHashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("dose", Types.VARCHAR), new OneToOne.Column("name", Types.VARCHAR));
            columnMappings.put(
                    new OneToOne.Column("value", Types.DECIMAL), new OneToOne.Column("value", Types.DECIMAL));
            columnMappings.put(
                    new OneToOne.Column("frequency", Types.INTEGER), new OneToOne.Column("frequency", Types.INTEGER));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblClientSupportDetails", "supporting_organization");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new LinkedHashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("ClientSupportDesciption", Types.VARCHAR), new OneToOne.Column("name", Types.VARCHAR));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblGenericName", "generic_name");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new LinkedHashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("GenericName", Types.VARCHAR), new OneToOne.Column("name", Types.VARCHAR));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblReasonforChange", "regimen_change_reason");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new LinkedHashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("ReasonforChange", Types.VARCHAR), new OneToOne.Column("name", Types.VARCHAR));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblRegimenCategory", "regimen_type");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new LinkedHashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("CategoryName", Types.VARCHAR), new OneToOne.Column("name", Types.VARCHAR));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblSourceOfClient", "patient_source");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new LinkedHashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("SourceOfClient", Types.VARCHAR), new OneToOne.Column("name", Types.VARCHAR));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblTypeOfService", "service_type");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new LinkedHashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("TypeofService", Types.VARCHAR), new OneToOne.Column("name", Types.VARCHAR));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblUnit", "dispensing_unit");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new LinkedHashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("Unit", Types.VARCHAR), new OneToOne.Column("name", Types.VARCHAR));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblVisitTransaction", "visit_type");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new LinkedHashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("VisitTranName", Types.VARCHAR), new OneToOne.Column("name", Types.VARCHAR));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }

        {
            OneToOne oto = new OneToOne("tblARVDrugStockMain", "drug");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new LinkedHashMap<OneToOne.Column, OneToOne.Column>();
            
            columnMappings.put(new OneToOne.Column("ARVDrugsID", Types.VARCHAR), new OneToOne.Column("name", Types.VARCHAR));

            columnMappings.put(new OneToOne.Column("Packsizes", Types.INTEGER), new OneToOne.Column("pack_size", Types.INTEGER));
            
            columnMappings.put(new OneToOne.Column("ReorderLevel", Types.INTEGER), new OneToOne.Column("reorder_point", Types.INTEGER));

            OneToOne.Column category = new OneToOne.Column("drug_category_id", Types.INTEGER);
            category.setReference(new OneToOne.Reference("drug_category", true));
            columnMappings.put(new OneToOne.Column("DrugCategory", Types.INTEGER), category);

            OneToOne.Column unit = new OneToOne.Column("dispensing_unit_id", Types.INTEGER);
            unit.setReference(new OneToOne.Reference("dispensing_unit"));
            columnMappings.put(new OneToOne.Column("Unit", Types.VARCHAR), unit);

            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        ase.close();
        fse.close();
    }

    private void migrateOneToOne(OneToOne oto) throws SQLException {
        if (!destinationIsEmpty(oto.getFdtTable())) {
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, "Skipping migration for table ''{0}''. "
                    + "Destination table ''{1}'' is not empty.", new Object[]{oto.getAdtTable(), oto.getFdtTable()});
            return;
        }

        Map.Entry<String, String> statements = createStatements(oto);
        String select = statements.getKey();
        String insert = statements.getValue();

        ResultSet rs = ase.executeQuery(select);
        if (rs != null) {
            connection.setAutoCommit(false);
            PreparedStatement pStmt = connection.prepareCall(insert);

            int totalRowCount = 0;
            int skippedRowCount = 0;

            while (rs.next()) {
                totalRowCount++;
                boolean execute = false;
                int index = 1;
                for (Map.Entry<OneToOne.Column, OneToOne.Column> columnMapping
                        : oto.getColumnMappings().entrySet()) {
                    execute = setParameter(rs, pStmt, columnMapping, index) || execute;
                    index++;
                }
                pStmt.setString(index++, UUID.randomUUID().toString());
                pStmt.setInt(index++, new User(1).getId());
                pStmt.setDate(index++, new java.sql.Date(new Date().getTime()));
                if (execute) {
                    pStmt.executeUpdate();
                } else {
                    skippedRowCount++;
                }
            }
            connection.commit();
            if (skippedRowCount > 0) {
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, "Skipped {0}. "
                        + "row(s) of the table ''{1}''. Nothing to migrate.", new Object[]{skippedRowCount, oto.getAdtTable()});
            }
            Logger.getLogger(Main.class.getName()).log(Level.INFO, "Migrated {0} row(s) from ''{1}'' "
                    + "to ''{2}''.", new Object[]{totalRowCount - skippedRowCount, oto.getAdtTable(), oto.getFdtTable()});
        }
    }

    private boolean destinationIsEmpty(String fdtTable) throws SQLException {
        String select = "SELECT * FROM " + fdtTable;
        ResultSet rs = fse.executeQuery(select);
        return !rs.next();
    }

    private Map.Entry<String, String> createStatements(OneToOne oto) {
        String select = "SELECT " + createColumns(oto.getColumnMappings(), true, false)
                + " FROM " + oto.getAdtTable();
        String insert = "INSERT INTO "
                + oto.getFdtTable() + "(" + createColumns(oto.getColumnMappings(), false, false) + ") "
                + "VALUES(" + createColumns(oto.getColumnMappings(), false, true) + ")";
        return new AbstractMap.SimpleEntry<String, String>(select, insert);
    }

    private String createColumns(Map<OneToOne.Column, OneToOne.Column> columnMappings,
            boolean select, boolean values) {
        String columns = "";
        int i = 1;
        int n = columnMappings.size();
        for (Map.Entry<OneToOne.Column, OneToOne.Column> entry : columnMappings.entrySet()) {
            if (values) {
                columns += "?";
            } else {
                columns += select ? entry.getKey().getName() : entry.getValue().getName();
            }
            if (i < n) {
                columns += ", ";
                i++;
            } else {
                if (!select) {
                    columns += values ? ", ?, ?, ?" : ", uuid, created_by, created_on";
                }
            }
        }
        return columns;
    }

    private boolean setParameter(ResultSet rs, PreparedStatement pStmt,
            Map.Entry<OneToOne.Column, OneToOne.Column> columnMapping, int index)
            throws SQLException {
        Object value = rs.getObject(columnMapping.getKey().getName());
        if (columnMapping.getValue().getReference() != null) {
            if (value != null) {
                value = setParamaterFromReference(columnMapping.getValue().getReference(), value.toString());
            }
        }

        if (value == null) {
            pStmt.setNull(index, Types.INTEGER);
        } else {
            pStmt.setObject(index, value, columnMapping.getValue().getType());
        }
        return (value != null);
    }

    private Integer setParamaterFromReference(OneToOne.Reference ref, String stringValue) throws SQLException {
        String referenceKey = ref.getTable() + "-"
                + ref.getColumn() + stringValue;
        Integer value = referenceCache.get(referenceKey);
        if (value == null) {
            String select = "SELECT " + ref.getPk() + ", " + ref.getColumn()
                    + " FROM " + ref.getTable()
                    + " WHERE " + ref.getColumn() + " = '" + stringValue + "'";
            ResultSet rs = fse.executeQuery(select);
            if (rs.next()) {
                value = rs.getInt(ref.getPk());
                referenceCache.put(referenceKey, value);
            } else {
                if (ref.isCreatable()) {
                    String insert = "INSERT INTO "
                            + ref.getTable() + "(" + ref.getColumn() + ") "
                            + "VALUES('" + stringValue + "')";
                    value = fse.executeUpdate(insert, true);
                    connection.commit();
                    referenceCache.put(referenceKey, value);
                }
            }
        }
        return value;
    }
}
