package com.intellisoftkenya.adt.migrator.business;

import com.intellisoftkenya.adt.migrator.Main;
import com.intellisoftkenya.adt.migrator.dao.AdtSqlExecutor;
import com.intellisoftkenya.adt.migrator.dao.FdtSqlExcecutor;
import com.intellisoftkenya.adt.migrator.dao.SqlExecutor;
import com.intellisoftkenya.adt.migrator.data.Column;
import com.intellisoftkenya.adt.migrator.data.OneToOne;
import com.intellisoftkenya.adt.migrator.data.Reference;
import com.intellisoftkenya.adt.migrator.data.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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

    private final Map<String, Integer> referenceCache = new HashMap<String, Integer>();

    /**
     * Migrate all tables that have a one-to-one relationship between ADT and
     * FDT.
     *
     * @throws java.sql.SQLException
     */
    public void migrateOneToOnes() throws SQLException {
        for (OneToOne oneToOne : new TableKitchen().prepareTables()) {
            migrateOneToOne(oneToOne);
        }
        ase.close();
        fse.close();
    }

    /**
     * Migrate a given {@link OneToOne} table from ADT to its FDT equivalent.
     */
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
                for (Map.Entry<Column, Column> columnMapping
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

    /**
     * @return true if the destination (FDT) table is empty.
     */
    private boolean destinationIsEmpty(String fdtTable) throws SQLException {
        String select = "SELECT * FROM " + fdtTable;
        ResultSet rs = fse.executeQuery(select);
        return !rs.next();
    }

    /**
     * @return a key-value pair of <String, String> containing a select
     * statement to the ADT table and an insert statement to the FDT table in
     * that order.
     */
    private Map.Entry<String, String> createStatements(OneToOne oto) {
        String select = "SELECT " + createColumns(oto.getColumnMappings(), true, false)
                + " FROM " + oto.getAdtTable();
        String insert = "INSERT INTO "
                + oto.getFdtTable() + "(" + createColumns(oto.getColumnMappings(), false, false) + ") "
                + "VALUES(" + createColumns(oto.getColumnMappings(), false, true) + ")";
        return new AbstractMap.SimpleEntry<String, String>(select, insert);
    }

    /**
     * @return properly concatenated column lists. This method is called by 
     * {@link OneToOneMigrator#createStatements(com.intellisoftkenya.adt.migrator.data.OneToOne) }.
     *
     * @param select true if you mean to create columns for a select statement.
     * Otherwise insert statement is assumed.
     *
     * @param values true if you mean to create "columns" for insert statement
     * value parameters.
     */
    private String createColumns(Map<Column, Column> columnMappings,
            boolean select, boolean values) {
        String columns = "";
        int i = 1;
        int n = columnMappings.size();
        for (Map.Entry<Column, Column> entry : columnMappings.entrySet()) {
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

    /**
     * Sets a parameter for the FDT insert statement from the value read for
     * that column from ADT select statement.
     *
     * @param rs the ResultSet to the ADT table.
     * @param pStmt the prepared statement for inserting into the FDT table.
     * @param columnMapping the column mapping for the ADT table to the FDT
     * table
     * @param index the column index for which to set the parameter.
     */
    private boolean setParameter(ResultSet rs, PreparedStatement pStmt,
            Map.Entry<Column, Column> columnMapping, int index)
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

    /**
     * Sets a parameter for the FDT insert statement from a value deduced or created
     * based on a table relationship as described by a {@link Column} {@link Reference}.
     */
    private Integer setParamaterFromReference(Reference ref, String stringValue) throws SQLException {
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
