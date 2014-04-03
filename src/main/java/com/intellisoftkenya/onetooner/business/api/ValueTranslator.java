package com.intellisoftkenya.onetooner.business.api;

/**
 * Defines a means by which a source value is translated to a single destination
 * value.
 * 
 * @author gitahi
 */
public interface ValueTranslator {
    
    /**
     * Translates a given source value to a destination value.
     * column.
     *
     * @param value the source value to be translated.
     * 
     * @return the result of the translation.
     */
    public String translate(String value);
}