package com.intellisoftkenya.onetooner.data;

import java.util.Map;

/**
 * Represents a one-to-one mapping between a Source table and a Destination
 * table.
 *
 * @author gitahi
 */
public class OneToOne {

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

    public OneToOne(Table sourceTable, Table destinationTable) {
        this.sourceTable = sourceTable;
        this.destinationTable = destinationTable;
    }

    public OneToOne(Table sourceTable, Table destinationTable, boolean requireEmpty) {
        this.sourceTable = sourceTable;
        this.destinationTable = destinationTable;
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
}
