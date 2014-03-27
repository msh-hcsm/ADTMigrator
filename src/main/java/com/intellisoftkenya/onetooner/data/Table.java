package com.intellisoftkenya.onetooner.data;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A database table
 *
 * @author gitahi
 */
public class Table extends NamedDatabaseObject {

    /**
     * A set of columns by which to order rows selected from this tables.
     */
    private Set<String> orderBy;

    public Table(String name) {
        super(name);
    }

    public Table(String name, Set<String> orderBy) {
        super(name);
        this.orderBy = orderBy;
    }

    public Set<String> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Set<String> orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * A convenience method creating a {@link Column#orderBy} set with only one
     * column.
     *
     * @param column the column to order by
     *
     * @return a set containing one column
     */
    public static Set<String> orderBy(String column) {
        Set<String> orderBy = new LinkedHashSet<>();
        orderBy.add(column);
        return orderBy;
    }
}
