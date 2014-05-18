package com.intellisoftkenya.onetooner.data;

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
     * Destination side. It is activated by the name of the source column mapped
     * to this destination column to null.
     */
    private Object value;

    /**
     * A String value that may optionally be appended in front of the values for
     * this column in the destination. This is typically necessary if data from
     * more than one source table is going to a single destination table and key
     * values from the different source tables belong to the same set and hence
     * have the potential to collide. Prefixing is activated by setting this
     * value to anything other than null.
     */
    private String prefix;

    public Column(String name, int type) {
        super(name);
        this.type = type;
    }

    public Column(String name, int type, Object value) {
        this(name, type);
        this.value = value;
    }

    public Column(String name, int type, String prefix) {
        this(name, type);
        this.prefix = prefix;
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
