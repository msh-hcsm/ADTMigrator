package com.intellisoftkenya.onetooner.data;

/**
 * A named database object such as a table or a column.
 *
 * @author gitahi
 */
public class NamedDatabaseObject {

    /**
     * The name of the database object.
     */
    private final String name;

    public NamedDatabaseObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }   
}
