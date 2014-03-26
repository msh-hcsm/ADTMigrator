package com.intellisoftkenya.adt.migrator.business;

import com.intellisoftkenya.adt.migrator.Main;
import com.intellisoftkenya.adt.migrator.dao.AdtSqlExecutor;
import com.intellisoftkenya.adt.migrator.dao.FdtSqlExcecutor;
import com.intellisoftkenya.adt.migrator.dao.SqlExecutor;
import com.intellisoftkenya.adt.migrator.data.Column;
import com.intellisoftkenya.adt.migrator.data.OneToOne;
import com.intellisoftkenya.adt.migrator.data.Reference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    private final Map<String, Integer> referenceCache = new HashMap<>();
    private final Map<String, PreparedStatement> preparedQueryCache = new HashMap<>();

    private final AuditValues auditValues = new AuditValues();

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
        String select = oto.getSelectQuery() == null ? statements.getKey() : oto.getSelectQuery();
        String insert = statements.getValue();

        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Begining migration from ''{0}'' to ''{1}.", 
                new Object[]{oto.getAdtTable(), oto.getFdtTable()});
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Using select statement: ''{0}''", select);
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Using insert statement: ''{0}''", insert);

        ResultSet rs = ase.executeQuery(select);
        if (rs != null) {
            connection.setAutoCommit(false);
            PreparedStatement pStmt = connection.prepareStatement(insert);

            int totalRowCount = 0;
            int batchNo = 1;
            int skippedRowCount = 0;
            while (rs.next()) {
                totalRowCount++;
                boolean execute = false;
                int index = 1;
                Map<String, Object> readValues = new HashMap<>();

                for (Map.Entry<Column, Column> columnMapping
                        : oto.getColumnMappings().entrySet()) {
                    execute = setParameter(rs, pStmt, columnMapping, index, readValues) || execute;
                    index++;
                }
                pStmt.setString(index++, auditValues.uuid());
                pStmt.setInt(index++, auditValues.createdBy());
                pStmt.setDate(index++, auditValues.createdOn());
                if (execute) {
                    pStmt.executeUpdate();
                } else {
                    skippedRowCount++;
                }
                if (totalRowCount % SqlExecutor.TRANSACTION_BATCH_SIZE == 0) {

                    connection.commit();
                    Logger.getLogger(Main.class.getName()).log(Level.INFO, "Commited transaction batch #{0}.",
                            new Object[]{batchNo});
                    batchNo++;
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
        return new AbstractMap.SimpleEntry<>(select, insert);
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
        List<String> added = new ArrayList<>();
        String append = null;
        for (Map.Entry<Column, Column> entry : columnMappings.entrySet()) {
            if (values) {
                added.add("?");
                append = ", ?, ?, ?";
            } else {
                if (select) {
                    String column = entry.getKey().getName();
                    if (column == null) {
                        continue;
                    }
                    //avoid adding the same column to select statement more than once
                    if (!added.contains(column)) {
                        added.add(column);
                    }
                } else {
                    added.add(entry.getValue().getName());
                    append = ", uuid, created_by, created_on";
                }
            }
        }
        return commify(added, append);
    }

    /**
     * Returns a comma separated list of columns.
     *
     * @param columns the set of columns to be commified.
     * @param append any string to be appended after commifying
     */
    private String commify(List<String> columns, String append) {
        String commified = "";
        int i = 0;
        int n = columns.size() - 1;
        for (String column : columns) {
            commified += column;
            if (i < n) {
                commified += ", ";
                i++;
            }
        }
        return commified += (append != null ? append : "");
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
     * @param alreadyRead a map of values already read from columns in this row.
     * Prevents the ResultSet from attempting to re-read values that it has read
     * previously because this results in an exception.
     */
    private boolean setParameter(ResultSet rs, PreparedStatement pStmt,
            Map.Entry<Column, Column> columnMapping, int index, Map<String, Object> alreadyRead)
            throws SQLException {
        String adtColumnName = columnMapping.getKey().getName();
        Object value;
        if (adtColumnName == null) {
            value = columnMapping.getValue().getValue();
        } else {
            if (alreadyRead.containsKey(adtColumnName)) {
                value = alreadyRead.get(adtColumnName);
            } else {
                value = rs.getObject(adtColumnName);
                alreadyRead.put(adtColumnName, value);
            }
        }
        if (columnMapping.getValue().getReference() != null) {
            if (value != null) {
                value = setParameterFromReference(columnMapping.getValue().getReference(), value.toString());
            }
        }

        if (value == null) {
            pStmt.setNull(index, Types.INTEGER);
        } else {
            pStmt.setObject(index, value, columnMapping.getValue().getType());
        }
        if (columnMapping.getValue().getName().equals("legacy_pk")) {//don't mark insert for execution on account of source table primary key
            return false;
        } else {
            return (value != null);
        }
    }

    /**
     * Sets a parameter for the FDT insert statement from a value deduced or
     * created based on a table relationship as described by a
     * {@link Column} {@link Reference}.
     */
    private Integer setParameterFromReference(Reference ref, String stringValue) throws SQLException {
        String referenceKey = ref.getTable() + "-"
                + ref.getColumn() + stringValue;
        Integer value = referenceCache.get(referenceKey);
        if (value == null) {
            if (ref.getReferenceProcessor() != null) {
                stringValue = ref.getReferenceProcessor().process(stringValue);
            }
            String select = "SELECT " + ref.getPk() + ", " + ref.getColumn()
                    + " FROM " + ref.getTable()
                    + " WHERE " + ref.getColumn() + " = ?";
            
            PreparedStatement pStmt = preparedQueryCache.get(select);
            if (pStmt == null) {
                pStmt = connection.prepareStatement(select);
                preparedQueryCache.put(select, pStmt);
            }
            pStmt.setString(1, stringValue);
            
            Logger.getLogger(Main.class.getName()).log(Level.INFO, "Setting parameter from reference using select statement: ''{0}''", select);

            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                value = rs.getInt(ref.getPk());
                referenceCache.put(referenceKey, value);
            } else {
                if (ref.isCreatable()) {
                    String insert = "INSERT INTO "
                            + ref.getTable() + "(" + ref.getColumn() + ", uuid, created_by, created_on) "
                            + "VALUES('" + stringValue + "', '" + auditValues.uuid()
                            + "'," + auditValues.createdBy() + " , '" + auditValues.createdOn() + "')";

                    Logger.getLogger(Main.class.getName()).log(Level.INFO, "Adding parameter to reference using insert statement: ''{0}''", insert);

                    value = fse.executeUpdate(insert, true);
                    connection.commit();
                    referenceCache.put(referenceKey, value);
                }
            }
        }
        return value;
    }

    /**
     * Supplies values fro audit columns.
     */
    private class AuditValues {

        /**
         * @return a random UUID.
         */
        private String uuid() {
            return UUID.randomUUID().toString();
        }

        /**
         * @return an imaginary user id of 1.
         */
        public int createdBy() {
            return 1;
        }

        /**
         * @return the time now.
         */
        private java.sql.Date createdOn() {
            return new java.sql.Date(new Date().getTime());
        }
    }
}
