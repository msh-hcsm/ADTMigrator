package com.intellisoftkenya.onetooner.data;

import com.intellisoftkenya.onetooner.business.OneToOneMigrator;
import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a one-to-one mapping between a Source table and a Destination
 * table.
 *
 * @author gitahi
 */
public class OneToOne implements Comparable<OneToOne> {

    /**
     * The Source {@link Table}.
     */
    private final Table sourceTable;

    /**
     * The Destination {@link Table}.
     */
    private final Table destinationTable;

    /**
     * The column mappings between the Source and the Destination table..
     */
    private Map<Column, Column> columnMappings;

    /**
     * The column = value to be used in an SQL where clause for the destination
     * table associated with this object to determine if the table is
     * sufficiently empty for migration to proceed. See 
     * {@link OneToOneMigrator#isEmptyEnough(com.intellisoftkenya.onetooner.data.OneToOne) }
     */
    private List<WhereCondition> whereConditions;

    /**
     * A custom select parameterizedQuery to be used to read from the Source
     * table instead of constructing one from the tables and column mappings
     * described in this instance.
     */
    private ParameterizedQuery parameterizedQuery;

    /**
     * The {@link ExtraProcessor} to execute before running
     * {@link OneToOneMigrator}
     */
    private List<ExtraProcessor> preProcessors;

    /**
     * The {@link ExtraProcessor} to execute after running
     * {@link OneToOneMigrator}
     */
    private List<ExtraProcessor> postProcessors;

    /**
     * The order for the destination table for this object when deleting a bunch
     * of OneToOne tables. Should be based on table relationships to prevent
     * foreign key constraint violations.
     */
    private final Integer deletionOrder;

    /**
     * A list of delete queries to execute before deleting this {@link OneToOne}
     * object. Typically this would be junction tables that depend on the table
     * represented by this object.
     */
    private List<String> preDeletes;
    
    /**
     * Whether or not the associated destination table should actually be deleted
     * when clearing the database for migration. Useful for universally maintained
     * metadata that is not migrated for each database but is preset instead.
     * 
     * Set to true by default.
     */
    private boolean deletable = true;
    
    public OneToOne(Integer deletionOrder, Table sourceTable, Table destinationTable) {
        this.deletionOrder = deletionOrder;
        this.sourceTable = sourceTable;
        this.destinationTable = destinationTable;
    }

    public Table getSourceTable() {
        return sourceTable;
    }

    public Table getDestinationTable() {
        return destinationTable;
    }

    public Map<Column, Column> getColumnMappings() {
        return columnMappings;
    }

    public void setColumnMappings(Map<Column, Column> columnMappings) {
        this.columnMappings = columnMappings;
    }

    public ParameterizedQuery getParameterizedQuery() {
        return parameterizedQuery;
    }

    public void setParameterizedQuery(ParameterizedQuery parameterizedQuery) {
        this.parameterizedQuery = parameterizedQuery;
    }

    /**
     * Sets {@link OneToOne#parameterizedQuery} with no parameters.
     *
     * @param sql the SQL for the {@link OneToOne#parameterizedQuery}
     */
    public void setParameterizedQuery(String sql) {
        this.parameterizedQuery = new ParameterizedQuery(sql);
    }

    public List<WhereCondition> getWhereConditions() {
        if (whereConditions == null) {
            whereConditions = new ArrayList<>();
        }
        return whereConditions;
    }

    public void addWhereCondition(WhereCondition whereCondition) {
        getWhereConditions().add(whereCondition);
    }

    public List<ExtraProcessor> getPreProcessors() {
        if (preProcessors == null) {
            preProcessors = new ArrayList<>();
        }
        return preProcessors;
    }

    public void addPreProcessor(ExtraProcessor preProcessor) {
        getPreProcessors().add(preProcessor);
    }

    public List<ExtraProcessor> getPostProcessors() {
        if (postProcessors == null) {
            postProcessors = new ArrayList<>();
        }
        return postProcessors;
    }

    public void addPostProcessor(ExtraProcessor postProcessor) {
        getPostProcessors().add(postProcessor);
    }

    public List<String> getPreDeletes() {
        if (preDeletes == null) {
            preDeletes = new ArrayList<>();
        }
        return preDeletes;
    }

    public void addPreDelete(String preDelete) {
        getPreDeletes().add(preDelete);
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    @Override
    public int compareTo(OneToOne oto) {
        return this.deletionOrder.compareTo(oto.deletionOrder);
    }
}
