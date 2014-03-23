package com.intellisoftkenya.adt.migrator.data;

import java.util.Map;

/**
 *
 * @author gitahi
 */
public class OneToOne {

    public static class Column {

        public Column(String name, Class type) {
            this.name = name;
            this.type = type;
        }

        private final String name;
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

    private final String adtTable;
    private final String fdtTable;
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
