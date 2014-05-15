package com.intellisoftkenya.onetooner.api.translator;

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
     * 
     * @throws java.lang.Exception if anything goes wrong during translation.
     */
    public String translate(String value) throws Exception;
}
