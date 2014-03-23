package com.intellisoftkenya.adt.migrator.business;

import com.intellisoftkenya.adt.migrator.Main;
import com.intellisoftkenya.adt.migrator.dao.AdtSqlExecutor;
import com.intellisoftkenya.adt.migrator.dao.FdtSqlExcecutor;
import com.intellisoftkenya.adt.migrator.dao.SqlExecutor;
import com.intellisoftkenya.adt.migrator.data.OneToOne;
import com.intellisoftkenya.adt.migrator.data.User;
import com.intellisoftkenya.adt.migrator.exceptions.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
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

    /**
     * Migrate all tables that have a one-to-one relationship between ADT and
     * FDT.
     *
     * @throws
     * com.intellisoftkenya.adt.migrator.exceptions.UnsupportedDataTypeException
     */
    public void migrateOneToOnes() throws UnsupportedDataTypeException, SQLException {
        {
            OneToOne oto = new OneToOne("tblDose", "dosage");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new HashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("dose", String.class), new OneToOne.Column("name", String.class));
            columnMappings.put(
                    new OneToOne.Column("value", BigDecimal.class), new OneToOne.Column("value", BigDecimal.class));
            columnMappings.put(
                    new OneToOne.Column("frequency", Integer.class), new OneToOne.Column("frequency", Integer.class));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblClientSupportDetails", "supporting_organization");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new HashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("ClientSupportDesciption", String.class), new OneToOne.Column("name", String.class));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblGenericName", "generic_name");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new HashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("GenericName", String.class), new OneToOne.Column("name", String.class));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblReasonforChange", "regimen_change_reason");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new HashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("ReasonforChange", String.class), new OneToOne.Column("name", String.class));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblRegimenCategory", "regimen_type");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new HashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("CategoryName", String.class), new OneToOne.Column("name", String.class));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblSourceOfClient", "patient_source");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new HashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("SourceOfClient", String.class), new OneToOne.Column("name", String.class));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblTypeOfService", "service_type");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new HashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("TypeofService", String.class), new OneToOne.Column("name", String.class));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblUnit", "dispensing_unit");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new HashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("Unit", String.class), new OneToOne.Column("name", String.class));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        {
            OneToOne oto = new OneToOne("tblVisitTransaction", "visit_type");
            Map<OneToOne.Column, OneToOne.Column> columnMappings = new HashMap<OneToOne.Column, OneToOne.Column>();
            columnMappings.put(
                    new OneToOne.Column("VisitTranName", String.class), new OneToOne.Column("name", String.class));
            oto.setColumnMappings(columnMappings);
            migrateOneToOne(oto);
        }
        ase.close();
        fse.close();
    }

    private void migrateOneToOne(OneToOne oto) throws UnsupportedDataTypeException, SQLException {
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
            throws SQLException, UnsupportedDataTypeException {
        Object value = null;
        if (columnMapping.getKey().getType() == String.class) {
            value = rs.getString(columnMapping.getKey().getName());
        } else if (columnMapping.getKey().getType() == Integer.class) {
            value = rs.getInt(columnMapping.getKey().getName());
        } else if (columnMapping.getKey().getType() == BigDecimal.class) {
            value = rs.getBigDecimal(columnMapping.getKey().getName());
        } else {
            throw new UnsupportedDataTypeException();
        }

        if (columnMapping.getValue().getType() == String.class) {
            pStmt.setString(index, (String) value);
        } else if (columnMapping.getValue().getType() == Integer.class) {
            pStmt.setInt(index, (Integer) value);
        } else if (columnMapping.getValue().getType() == BigDecimal.class) {
            pStmt.setBigDecimal(index, (BigDecimal) value);
        } else {
            throw new UnsupportedDataTypeException();
        }

        return (value != null);
    }
}
