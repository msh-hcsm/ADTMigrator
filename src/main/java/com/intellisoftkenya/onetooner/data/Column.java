package com.intellisoftkenya.onetooner.data;

/**
 * A database column with it's name and data type.
 */
public class Column {

    /**
     * The name of the column.
     */
    private final String name;
    /**
     * The {@link java.sql.Types} representing the data type of the column in
     * the database.
     */
    private final int type;

    /**
     * The {@link Reference} representing a relationship in which this column
     * participates.
     */
    private Reference reference;
    
    /**
     * An optional default value that may be inserted for this column on the Destination
     * side. To activate this, map this column to a Source column with name ==
     * null.
     */
    private Object value;

    public Column(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public Column(String name, int type, Object value) {
        this(name, type);
        this.value = value;
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
