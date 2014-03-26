package com.intellisoftkenya.onetooner.business;

/**
 * Defines a means by which a group values from the Source table may be processed 
 * (one at a time) into a single referenced value in the Destination database representing 
 * a common meaning for members in that group.
 * 
 * @author gitahi
 */
public interface ReferenceProcessor {
    
    /**
     * Translates a given value to an integer reference to be used as an Destination
     * table foreign key.
     * column.
     *
     * @param value the individual value to process from a group of values.
     * 
     * @return the group value to which value belongs.
     */
    public String process(String value);
}
