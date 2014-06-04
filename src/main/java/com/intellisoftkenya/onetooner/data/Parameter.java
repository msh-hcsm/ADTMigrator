package com.intellisoftkenya.onetooner.data;

import java.sql.Types;

/**
 * An SQL parameter
 * 
 * @author gitahi
 */
public class Parameter {
  
    /**
     * The value for this parameter.
     */
    private final Object value;
    
    /**
     * The type ({@link Types}) of this parameter.
     */
    private final int type;

    public Parameter(Object value, int type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        if (value != null) {
            return value.toString();
        }
        return super.toString();
    }
}
