package com.intellisoftkenya.adt.migrator.data;

import java.util.Map;

/**
 * Represents a one-to-one mapping between an ADT table and an FDT table.
 *
 * @author gitahi
 */
public class OneToOne {

    /**
     * Represents a column with it's name and data type.
     */
    public static class Column {

        public Column(String name, Class type) {
            this.name = name;
            this.type = type;
        }

        /**
         * The name of the column.
         */
        private final String name;

        /**
         * The class representing the data type of the column.
         */
        private final Class type;

        public String getName() {
            return name;
        }

        public Class getType() {
            return type;
        }
    }

    public OneToOne(String adtTable, String fdtTable) {
        this.adtTable = adtTable;
        this.fdtTable = fdtTable;
    }

    /**
     * The name of the table in the ADT database.
     */
    private final String adtTable;

    /**
     * The name of the table in the FDT database.
     */
    private final String fdtTable;

    /**
     * The column mappings between the ADT and the FDT table..
     */
    private Map<Column, Column> columnMappings;

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

}
