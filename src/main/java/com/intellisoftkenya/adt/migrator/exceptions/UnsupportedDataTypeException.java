package com.intellisoftkenya.adt.migrator.exceptions;

import com.intellisoftkenya.adt.migrator.data.OneToOne;

/**
 * Thrown whenever there is an attempt to process a {@link OneToOne.Column}
 * with an unsupported data type.
 * 
 * @author gitahi
 */
public class UnsupportedDataTypeException extends Exception {

    public UnsupportedDataTypeException() {
        super("Unsupported data type.");
    }

    public UnsupportedDataTypeException(String message) {
        super(message);
    }
}
