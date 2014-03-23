package com.intellisoftkenya.adt.migrator.data;

import java.util.Map;

/**
 * Represents a one-to-one mapping between an ADT table and an FDT table.
 *
 * @author gitahi
 */
public class OneToOne {

    /**
     * A database column with it's name and data type.
     */
    public static class Column {

        /**
         * The name of the column.
         */
        private final String name;

        /**
         * The class representing the data type of the column.
         */
        private final int type;

        private Reference reference;

        public Column(String name, int type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public int getType() {
            return type;
        }

        public Reference getReference() {
            return reference;
        }

        public void setReference(Reference reference) {
            this.reference = reference;
        }
    }

    /**
     * A reference to a database table to which this column is a foreign key.
     */
    public static class Reference {

        private final String table;
        private String column = "name";
        private String pk = "id";

        public Reference(String table) {
            this.table = table;
        }

        public String getTable() {
            return table;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getPk() {
            return pk;
        }

        public void setPk(String pk) {
            this.pk = pk;
        }
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
}
