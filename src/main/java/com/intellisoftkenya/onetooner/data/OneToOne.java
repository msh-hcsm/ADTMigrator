package com.intellisoftkenya.onetooner.data;

import java.util.Map;

/**
 * Represents a one-to-one mapping between a Source table and a Destination table.
 *
 * @author gitahi
 */
public class OneToOne {

    /**
     * The name of the table in the Source database.
     */
    private final String adtTable;

    /**
     * The name of the table in the Destination database.
     */
    private final String fdtTable;

    /**
     * The column mappings between the Source and the Destination table..
     */
    private Map<Column, Column> columnMappings;

    /**
     * A custom select query to be used to read from the Source table instead of
     * constructing one from the tables and column mappings described in this
     * instance.
     */
    private String query;

    public OneToOne(String adtTable, String fdtTable) {
        this.adtTable = adtTable;
        this.fdtTable = fdtTable;
    }

    public String getAdtTable() {
        return adtTable;
    }

    public String getFdtTable() {
        return fdtTable;
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
}
