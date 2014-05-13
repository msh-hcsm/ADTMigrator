package com.intellisoftkenya.onetooner.api.translator;

/**
 * Defines a means by which a destination value is inferred from a source
 * value.
 * 
 * @author gitahi
 */
public interface ValueInferrer {
    
    /**
     * Infers a given destination value from a source value.
     * column.
     *
     * @param value the source value by which to make the inference.
     * 
     * @return the inferred value.
     */
    public Integer infer(String value);
}
