package com.intellisoftkenya.onetooner.data;

import com.intellisoftkenya.onetooner.api.imp.processor.LookupValuePkProcessor;

/**
 * An object that may be set as a derivable destination {@link Column#value}.
 * Such a value is then dynamically retrieved as the primary key of a lookup
 * table by a {@link LookupValuePkProcessor} pre-processor.
 *
 * @author gitahi
 */
public class LookupValue {

    /**
     * The lookup table.
     */
    private String table;
    
    /**
     * The primary key column name of the lookup table.
     */
    private String pk;
    
    /**
     * The value column name of the lookup table that is used to query for
     * the primary key value.
     */
    private String valueColumn;
    
    /**
     * The actual value for querying the lookup table to retrieve the primary
     * key.
     */
    private String value;

    public LookupValue(String table, String value) {
        this.table = table;
        this.value = value;
    }

    public LookupValue(String table, String valueColumn, String value) {
        this.table = table;
        this.valueColumn = valueColumn;
        this.value = value;
    }

    public LookupValue(String table, String pk, String valueColumn, String value) {
        this.table = table;
        this.pk = pk;
        this.valueColumn = valueColumn;
        this.value = value;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
