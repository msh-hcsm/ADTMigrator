/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intellisoftkenya.adt.migrator.data;

/**
 * A database column with it's name and data type.
 */
public class Column {
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
