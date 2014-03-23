/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intellisoftkenya.adt.migrator.data;

/**
 * A reference to a database table to which this column is a foreign key.
 */
public class Reference {
    /**
     * Name of the referenced table
     */
    private final String table;
    /**
     * Name of the column containing the text by which to query for primary
     * keys
     */
    private String column = "name";
    /**
     * Name of the referenced primary key
     */
    private String pk = "id";
    /**
     * Whether or not a record should be created if the text from
     * {@link Reference#column} is missing. Records are created subject to
     * the table allowing insertion to column alone. If for instance there
     * are other non-nullable columns which do not have defaults, creation
     * will fail.
     */
    private boolean creatable = false;

    public Reference(String table) {
        this.table = table;
    }

    public Reference(String table, boolean creatable) {
        this(table);
        this.creatable = creatable;
    }

    public Reference(String table, String column) {
        this(table);
        this.column = column;
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

    public boolean isCreatable() {
        return creatable;
    }

    public void setCreatable(boolean creatable) {
        this.creatable = creatable;
    }
    
}
