package com.intellisoftkenya.adt.migrator;

import com.intellisoftkenya.adt.migrator.domain.fdt.DomainObject;
import com.intellisoftkenya.adt.migrator.domain.fdt.User;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author gitahi
 */
public class Util {

    /**
     * Assign common variable to DomainObject
     * 
     * @param domainObject the DomainObject
     */
    public static void fleshFdtDomainObject(DomainObject domainObject) {
        domainObject.setUuid(UUID.randomUUID().toString());
        domainObject.setCreatedBy(new User(1));
        domainObject.setCreatedOn(new Date());
    }
}
