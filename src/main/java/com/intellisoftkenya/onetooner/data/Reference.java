package com.intellisoftkenya.onetooner.data;

import com.intellisoftkenya.onetooner.business.ReferenceProcessor;

/**
 * A reference to an Destination database table to which this column is a foreign key.
 */
public class Reference {

    /**
     * Name of the referenced table
     */
    private final String table;
    /**
     * Name of the column containing the text by which to query for primary
     * keys. Defaults to "name" if not set.
     */
    private String column = "name";
    /**
     * Name of the referenced primary key. Defaults to "id" if not set.
     */
    private String pk = "id";
    /**
     * Whether or not a record should be created if the text from
     * {@link Reference#column} is missing. Records are created subject to the
     * table allowing insertion to column alone. If for instance there are other
     * non-nullable columns which do not have defaults, creation will fail.
     */
    private boolean creatable = false;

    /**
     * A {@link ReferenceProcessor} to process a reference value to be used as
     * the data for cells in this column. This is useful when the value cannot
     * be easily deduced and must be obtained by some non-trivial means.
     */
    private ReferenceProcessor referenceProcessor;

    public Reference(String table) {
        this.table = table;
    }

    public Reference(String table, boolean creatable) {
        this(table);
        this.creatable = creatable;
    }

    public Reference(String table, boolean creatable, ReferenceProcessor referenceProcessor) {
        this(table, creatable);
        this.referenceProcessor = referenceProcessor;
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

    public ReferenceProcessor getReferenceProcessor() {
        return referenceProcessor;
    }

    public void setReferenceProcessor(ReferenceProcessor referenceProcessor) {
        this.referenceProcessor = referenceProcessor;
    }
}
