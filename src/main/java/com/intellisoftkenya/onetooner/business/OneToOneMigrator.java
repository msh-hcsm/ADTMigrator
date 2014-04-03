package com.intellisoftkenya.onetooner.business;

import com.intellisoftkenya.onetooner.Main;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExcecutor;
import com.intellisoftkenya.onetooner.dao.SourceSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.Column;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.data.Reference;
import com.intellisoftkenya.onetooner.data.Table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
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

    private final SqlExecutor sse = SourceSqlExecutor.getInstance();
    private final SqlExecutor dse = DestinationSqlExcecutor.getInstance();
    private final Connection connection = dse.getConnection();

    private final Map<String, Integer> referenceCache = new HashMap<>();
    private final Map<String, PreparedStatement> preparedQueryCache = new HashMap<>();

    private final AuditValues auditValues = new AuditValues();
    private Integer borrowableValue = null;

    /**
     * Migrate all tables that have a logical one-to-one mapping between the
     * Source and the Destination.
     *
     * @throws java.sql.SQLException
     */
    public void migrateOneToOnes() throws SQLException {
        for (OneToOne oneToOne : new TableConfigurator().configureTables()) {
            migrateOneToOne(oneToOne);
        }
        sse.close();
        dse.close();
    }

    /**
     * Migrate a given {@link OneToOne} table from the Source to its Destination
     * equivalent.
     */
    private void migrateOneToOne(OneToOne oto) throws SQLException {
        if (oto.isRequireEmpty() && !destinationIsEmpty(oto.getDestinationTable())) {
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, "Skipping migration for table ''{0}''. "
                    + "Destination table ''{1}'' is not empty.", new Object[]{oto.getSourceTable(), oto.getDestinationTable()});
            return;
        }

        Map.Entry<String, String> statements = createStatements(oto);
        String select = oto.getQuery() == null ? statements.getKey() : oto.getQuery();
        String insert = statements.getValue();

        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Begining migration from ''{0}'' to ''{1}.",
                new Object[]{oto.getSourceTable(), oto.getDestinationTable()});
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Using select statement: ''{0}''", select);
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Using insert statement: ''{0}''", insert);

        ResultSet rs = sse.executeQuery(select);
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
                        + "row(s) of the table ''{1}''. Nothing to migrate.", new Object[]{skippedRowCount, oto.getSourceTable()});
            }
            Logger.getLogger(Main.class.getName()).log(Level.INFO, "Migrated {0} row(s) from ''{1}'' "
                    + "to ''{2}''.", new Object[]{totalRowCount - skippedRowCount, oto.getSourceTable(), oto.getDestinationTable()});
        }
    }

    /**
     * @return true if the Destination table is empty.
     */
    private boolean destinationIsEmpty(Table destinationTable) throws SQLException {
        String select = "SELECT * FROM " + destinationTable.getName();
        ResultSet rs = dse.executeQuery(select);
        return !rs.next();
    }

    /**
     * @return a key-value pair of <String, String> containing a select
     * statement to the Source table and an insert statement to the Destination
     * table in that order.
     */
    private Map.Entry<String, String> createStatements(OneToOne oto) {
        String select = "SELECT DISTINCT " + createColumns(oto.getColumnMappings(), true, false)
                + " FROM " + oto.getSourceTable().getName();
        if (oto.getSourceTable().getOrderBy() != null) {
            select += " ORDER BY " + commify(oto.getSourceTable().getOrderBy(), null);
        }
        String insert = "INSERT INTO "
                + oto.getDestinationTable().getName() + "(" + createColumns(oto.getColumnMappings(), false, false) + ") "
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
    private String commify(Collection<String> columns, String append) {
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
     * Sets a parameter for the Destination insert statement from the value read
     * for that column from Source select statement.
     *
     * @param rs the ResultSet to the Source table.
     * @param pStmt the prepared statement for inserting into the Destination
     * table.
     * @param columnMapping the column mapping for the Source table to the
     * Destination table
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
     * Sets a parameter for the Destination insert statement from a value
     * deduced or created based on a table relationship as described by a
     * {@link Column} {@link Reference}.
     */
    private Integer setParameterFromReference(Reference ref, String stringValue) throws SQLException {
        String referenceKey = ref.getTable() + "-"
                + ref.getColumn() + stringValue;
        Integer value = referenceCache.get(referenceKey);
        if (value == null) {
            if (ref.getReferenceProcessor() != null) {
                stringValue = ref.getReferenceProcessor().translate(stringValue);
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
                if (ref.isBorrowable()) {
                    borrowableValue = value;
                }
            } else {
                if (ref.isCreatable()) {
                    String insert = "INSERT INTO "
                            + ref.getTable() + "(" + ref.getColumn() + ", uuid, created_by, created_on) "
                            + "VALUES('" + stringValue + "', '" + auditValues.uuid()
                            + "'," + auditValues.createdBy() + " , '" + auditValues.createdOn() + "')";

                    Logger.getLogger(Main.class.getName()).log(Level.INFO, "Adding parameter to reference using insert statement: ''{0}''", insert);

                    value = dse.executeUpdate(insert, true);
                    connection.commit();
                    referenceCache.put(referenceKey, value);
                } else {
                    if (ref.isBorrowable()) {
                        value = borrowableValue;
                        referenceCache.put(referenceKey, value);
                    }
                }
            }
        }
        return value;
    }

    /**
     * Supplies values for audit columns.
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
