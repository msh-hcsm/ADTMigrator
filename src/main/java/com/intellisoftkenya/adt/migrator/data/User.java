package com.intellisoftkenya.adt.migrator.data;

/**
 * Place holder for the default user with whom all migrated data is
 * associated.
 * 
 * @author gitahi
 */
public class User {

    private int id;

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
