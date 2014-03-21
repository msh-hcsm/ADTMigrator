package com.intellisoftkenya.adt.migrator.domain.fdt;

/**
 *
 * @author gitahi
 */
public class Nameable extends DomainObject {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
