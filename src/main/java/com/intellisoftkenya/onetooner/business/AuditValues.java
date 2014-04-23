package com.intellisoftkenya.onetooner.business;

import java.util.Date;
import java.util.UUID;

/**
 * Supplies values for audit columns.
 */
public class AuditValues {

    /**
     * @return a random UUID.
     */
    public String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * @return an imaginary user id of 1.
     */
    public int createdBy() {
        return 1;
    }

    /**
     * @return the time now.
     */
    public java.sql.Date createdOn() {
        return new java.sql.Date(new Date().getTime());
    }
    
}
