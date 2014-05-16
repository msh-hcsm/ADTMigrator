package com.intellisoftkenya.onetooner.data;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A database column.
 * 
 * @author gitahi
 */
public class Column extends NamedDatabaseObject {

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
     * An optional default value that may be inserted for this column on the
     * Destination side. It is activated by setting it to anything other than
     * null.
     */
    private Object value;

    public Column(String name, int type) {
        super(name);
        this.type = type;
    }

    public Column(String name, int type, Object value) {
        this(name, type);
        this.value = value;
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
