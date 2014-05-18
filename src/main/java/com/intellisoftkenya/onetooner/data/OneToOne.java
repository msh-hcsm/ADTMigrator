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
     * Whether or not migration should require that the destination table be
     * empty before proceeding. Set to true by default.
     */
    private boolean requireEmpty = true;

    /**
     * A custom select query to be used to read from the Source table instead of
     * constructing one from the tables and column mappings described in this
     * instance.
     */
    private String query;

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

    public OneToOne(Integer deletionOrder, Table sourceTable, Table destinationTable) {
        this.deletionOrder = deletionOrder;
        this.sourceTable = sourceTable;
        this.destinationTable = destinationTable;
    }

    public OneToOne(Integer deletionOrder, Table sourceTable, Table destinationTable,
            boolean requireEmpty) {
        this(deletionOrder, sourceTable, destinationTable);
        this.requireEmpty = requireEmpty;
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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isRequireEmpty() {
        return requireEmpty;
    }

    public void setRequireEmpty(boolean requireEmpty) {
        this.requireEmpty = requireEmpty;
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

    @Override
    public int compareTo(OneToOne oto) {
        return this.deletionOrder.compareTo(oto.deletionOrder);
    }
}
