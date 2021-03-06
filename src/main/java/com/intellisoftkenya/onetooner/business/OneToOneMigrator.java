package com.intellisoftkenya.onetooner.business;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SourceSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.Column;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.data.Parameter;
import com.intellisoftkenya.onetooner.data.Reference;
import com.intellisoftkenya.onetooner.data.WhereCondition;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gitahi
 */
public class OneToOneMigrator {

    private static final Logger LOGGER = LoggerFactory.getLoger(OneToOneMigrator.class.getName());

    /**
     * When set to TRUE, this variable mutes actual data migration and just runs
     * the {@link ExtraProcessor}s on the {@link OneToOne} object passed to 
     * {@link OneToOneMigrator#migrateOneToOne(com.intellisoftkenya.onetooner.data.OneToOne) }.
     *
     * Useful when testing {@link ExtraProcessor}s against data that has already
     * been migrated in order to save time. This variable artificially sets the
     * {@link OneToOne#requireEmpty} variable to FALSE.
     */
    private final boolean muteMigration = false;

    /**
     * Causes deletion of all data in a destination table if migration into it
     * fails for any reason.
     */
    private final boolean deleteOnFailure = true;

    private final SqlExecutor sse = SourceSqlExecutor.getInstance();
    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    private final Map<String, Integer> referenceCache = new HashMap<>();
    private final Map<String, PreparedStatement> preparedQueryCache = new HashMap<>();

    private final AuditValues auditValues = new AuditValues();
    private Integer borrowableValue = null;

    public boolean isDeleteOnFailure() {
        return deleteOnFailure;
    }

    /**
     * Migrate all tables that have a logical one-to-one mapping between the
     * Source and the Destination.
     *
     * @throws java.sql.SQLException if any database related problem occurs.
     */
    public void migrate() throws Exception {
        LOGGER.log(Level.INFO, "Process started.");

        for (OneToOne oneToOne : new TableConfigurator().configureTables()) {
            try {
                migrateOneToOne(oneToOne);
            } catch (Exception ex) {
                String source = oneToOne.getSourceTable().getName();
                String destination = oneToOne.getDestinationTable().getName();
                LOGGER.log(Level.SEVERE, "An error occured while migrating data from ''{0}'' to ''{1}''.",
                        new Object[]{source, destination});
                if (deleteOnFailure) {
                    LOGGER.log(Level.INFO, "Attempting to delete any data there may be in ''{0}''.",
                            new Object[]{destination});
                    try {
                        int affected = deleteOneToOne(oneToOne);
                        if (affected != -1) {
                            LOGGER.log(Level.INFO, "Deleted {0} records from ''{1}''. Fix the errors and re-run the process.",
                                    new Object[]{affected, destination});
                        }
                    } catch (SQLException sex) {
                        LOGGER.log(Level.INFO, "Deleting from from ''{0}'' failed. Manually delete records "
                                + "from ''{0}''. fix the errors and re-run the process.",
                                new Object[]{destination});
                        throw sex;
                    }
                }
                throw new MigrationException("Process aborted! See logs for details.",
                        oneToOne, ex);
            }
        }
        close();

        LOGGER.log(Level.INFO, "Process successfully completed!");
    }

    /**
     * Migrate a given {@link OneToOne} table from the Source to its Destination
     * equivalent.
     *
     * @param oto the {@link OneToOne} object representing the migration
     * table/column mappings
     *
     * @throws java.lang.Exception if migration fails
     */
    public void migrateOneToOne(OneToOne oto) throws Exception {

        if (!isEmptyEnough(oto)) {
            if (!muteMigration) {
                LOGGER.log(Level.WARNING, "Skipped migration for table ''{0}''. "
                        + "Destination table ''{1}'' is not empty enough.", new Object[]{oto.getSourceTable(), oto.getDestinationTable()});
            }
            return;
        }

        if (!oto.getPreProcessors().isEmpty()) {
            for (ExtraProcessor preProcessor : oto.getPreProcessors()) {
                LOGGER.log(Level.FINE, "Pre processor ''{0}'' begins.",
                        new Object[]{preProcessor.getClass().getName()});
                preProcessor.process(oto);
                LOGGER.log(Level.FINE, "Pre processor ''{0}'' completes.",
                        new Object[]{preProcessor.getClass().getName()});
            }
        }

        Map.Entry<String, String> statements = createStatements(oto);

        boolean useCustomQuery = (oto.getParameterizedQuery() != null);

        String select = useCustomQuery ? oto.getParameterizedQuery().getSql()
                : statements.getKey();
        String insert = statements.getValue();

        LOGGER.log(Level.FINE, "Beginning migration from ''{0}'' to ''{1}.",
                new Object[]{oto.getSourceTable(), oto.getDestinationTable()});

        ResultSet rs;

        if (useCustomQuery) {
            rs = sse.executeQuery(select, oto.getParameterizedQuery().getParameters());
        } else {
            rs = sse.executeQuery(select);
        }
        if (rs != null) {
            PreparedStatement pStmt = dse.createPreparedStatement(insert);

            int totalRowCount = 0;
            int batchNo = 1;
            int skippedRowCount = 0;

            if (!muteMigration) {
                while (rs.next()) {
                    totalRowCount++;
                    boolean execute = false;
                    int index = 1;
                    Map<String, Object> readValues = new HashMap<>();

                    for (Map.Entry<Column, Column> columnMapping
                            : oto.getColumnMappings().entrySet()) {
                        execute = setParameter(rs, pStmt, columnMapping, index, readValues, oto) || execute;
                        index++;
                    }
                    pStmt.setString(index++, auditValues.uuid());
                    pStmt.setInt(index++, auditValues.createdBy());
                    pStmt.setDate(index++, auditValues.createdOn());
                    if (execute) {
                        pStmt.addBatch();
                        dse.logPreparedStatement(pStmt);
                    } else {
                        skippedRowCount++;
                    }
                    if (totalRowCount % SqlExecutor.TRANSACTION_BATCH_SIZE == 0) {
                        dse.executeBatch(pStmt);
                        LOGGER.log(Level.FINER, "Commited transaction batch #{0}.",
                                new Object[]{batchNo});
                        batchNo++;
                    }
                }
                dse.executeBatch(pStmt);
                pStmt.clearBatch();
            }

            if (!oto.getPostProcessors().isEmpty()) {
                for (ExtraProcessor postProcessor : oto.getPostProcessors()) {
                    LOGGER.log(Level.FINE, "Post processor ''{0}' begins.",
                            new Object[]{postProcessor.getClass().getName()});
                    postProcessor.process(oto);
                    LOGGER.log(Level.FINE, "Post processor ''{0}'' completes.",
                            new Object[]{postProcessor.getClass().getName()});
                }
            }

            if (skippedRowCount > 0) {
                LOGGER.log(Level.WARNING, "Skipped {0} "
                        + "row(s) of the table ''{1}''. Nothing to migrate.", new Object[]{skippedRowCount, oto.getSourceTable()});
            }
            LOGGER.log(Level.INFO, "Migrated {0} row(s) from ''{1}'' "
                    + "to ''{2}''.", new Object[]{totalRowCount - skippedRowCount, oto.getSourceTable(), oto.getDestinationTable()});
        }
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
            Map.Entry<Column, Column> columnMapping, int index, Map<String, Object> alreadyRead, OneToOne oto)
            throws SQLException, Exception {

        Column sourceColumn = columnMapping.getKey();
        Column destinationColumn = columnMapping.getValue();

        String sourceColumnName = sourceColumn.getName();
        Object value;
        if (sourceColumn.getName() == null) {
            value = destinationColumn.getValue();
        } else {
            if (alreadyRead.containsKey(sourceColumnName)) {
                value = alreadyRead.get(sourceColumnName);
            } else {
                value = rs.getObject(sourceColumnName);
                alreadyRead.put(sourceColumnName, value);
            }
        }
        if (destinationColumn.getReference() != null) {
            if (value != null) {
                value = setParameterFromReference(destinationColumn.getReference(), value.toString());
            }
        }

        if (value == null) {
            pStmt.setNull(index, Types.INTEGER);
        } else {
            if (destinationColumn.getPrefix() != null) {
                if (destinationColumn.getType() != Types.CHAR
                        && destinationColumn.getType() != Types.VARCHAR
                        && destinationColumn.getType() != Types.NVARCHAR) {
                    throw new RuntimeException("Only database columns that support Strings i.e. (java.sql.Types "
                            + Types.CHAR + ", " + Types.VARCHAR + ", and " + Types.NVARCHAR
                            + ") can have prefix values defined. The column '" + destinationColumn.getName()
                            + "' is of java.sql.Type(s) " + destinationColumn.getType());
                }
                value = destinationColumn.getPrefix() + value.toString();
            }
            pStmt.setObject(index, value, destinationColumn.getType());
        }
        if (destinationColumn.getName().equals("legacy_pk")) {//don't mark insert for execution on account of source table primary key
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
    private Integer setParameterFromReference(Reference ref, String stringValue) throws SQLException, Exception {
        String referenceKey = ref.getTable() + "-"
                + ref.getColumn() + stringValue;
        Integer value = referenceCache.get(referenceKey);
        if (value == null) {
            if (ref.getValueTranslator() != null) {
                stringValue = ref.getValueTranslator().translate(stringValue);
            }
            String select = "SELECT " + ref.getPk() + ", " + ref.getColumn()
                    + " FROM " + ref.getTable()
                    + " WHERE " + ref.getColumn() + " = ?";

            PreparedStatement pStmt = preparedQueryCache.get(select);
            if (pStmt == null) {
                pStmt = dse.createPreparedStatement(select);
                preparedQueryCache.put(select, pStmt);
            }
            if (ref.getPrefix() != null) {
                stringValue = ref.getPrefix() + stringValue;
            }
            pStmt.setString(1, stringValue);

            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                value = rs.getInt(ref.getPk());
                referenceCache.put(referenceKey, value);
                if (ref.isBorrowable()) {
                    borrowableValue = value;
                }
            } else {
                if (ref.isInferable() && ref.getValueInferrer() != null) {
                    value = ref.getValueInferrer().infer(stringValue);
                }
                if (value == null && ref.isCreatable()) {
                    String insert = "INSERT INTO `"
                            + ref.getTable() + "`(`" + ref.getColumn() + "`, `uuid`, `created_by`, `created_on`) "
                            + "VALUES(?, ?, ? ,?)";
                    List<Parameter> params = new ArrayList<>();
                    params.add(new Parameter(stringValue, Types.VARCHAR));
                    params.add(new Parameter(auditValues.uuid(), Types.VARCHAR));
                    params.add(new Parameter(auditValues.createdBy(), Types.INTEGER));
                    params.add(new Parameter(auditValues.createdOn(), Types.DATE));

                    value = dse.executeUpdate(insert, params, true);
                    referenceCache.put(referenceKey, value);
                }
                if (value == null && ref.isBorrowable()) {
                    value = borrowableValue;
                    referenceCache.put(referenceKey, value);
                }
                if (value == null) {
                    if (ref.getNullAction() == Reference.NullAction.THROW_EXCEPTION) {
                        LOGGER.log(Level.SEVERE, "The referenced value ''{0}'' from the table ''{1}'' is missing and could not be "
                                + "inferred, created or borrowed. Could the record have been skipped?",
                                new Object[]{stringValue, ref.getTable()});
                        throw new Exception("Required reference to lookup table value not satisfied.");
                    } else if (ref.getNullAction() == Reference.NullAction.LOG_WARNING) {
                        LOGGER.log(Level.WARNING, "The referenced value ''{0}'' from the table ''{1}'' is missing. Null will be used instead.",
                                new Object[]{stringValue, ref.getTable()});
                    }
                }
            }
        }
        return value;
    }

    public int deleteOneToOne(OneToOne oto) throws SQLException {
        if (!oto.getPreUpdates().isEmpty()) {
            for (String del : oto.getPreUpdates()) {
                dse.executeUpdate(del, false);
            }
        }
        if (oto.isDeletable()) {
            String delete = "DELETE FROM " + oto.getDestinationTable().getName();
            List<Parameter> params = new ArrayList<>();
            delete += " ";
            delete += createWhereClause(oto, params);
            return dse.executeUpdate(delete, params, false);
        }
        return -1;
    }

    public void close() {
        sse.close();
        dse.close();
    }

    private boolean isEmptyEnough(OneToOne oto) throws SQLException {
        if (muteMigration) {
            LOGGER.log(Level.WARNING, "Migration from {0} to {1} is muted. Only ExtraProcessors will run.",
                    new Object[]{oto.getSourceTable().getName(), oto.getDestinationTable().getName()});
            return false;
        }

        String skipIfQuery = "SELECT * FROM " + oto.getDestinationTable().getName();
        List<Parameter> params = new ArrayList<>();
        skipIfQuery += " ";
        skipIfQuery += createWhereClause(oto, params);

        ResultSet rs = dse.executeQuery(skipIfQuery, params);
        boolean emptyEnough = !rs.next();
        dse.close(rs);
        return emptyEnough;
    }

    /**
     * Creates an SQL WHERE clause from a list of {@link WhereCondition}s and
     * adds the requisite parameters to the parameter map.
     *
     * @param whereConditions the {@link WhereCondition}s
     * @param params the parameter map
     */
    private String createWhereClause(OneToOne oto,
            List<Parameter> params) {
        String whereClause = oto.getExplicitWhereConditions();
        if (whereClause != null) {
            return whereClause;
        } else {
            if (oto.getWhereConditions().isEmpty()) {
                return "";
            } else {
                if (oto.getWhereConditions().size() > 1) {
                    throw new UnsupportedOperationException("Multiple where conditions not supported yet!");
                } else {
                    for (WhereCondition wc : oto.getWhereConditions()) {
                        whereClause = ("WHERE " + wc.getColumnAndOperator() + " ?");
                        params.add(new Parameter(wc.getValue(), wc.getValueType()));
                    }
                    return whereClause;
                }
            }
        }
    }
}
