package com.intellisoftkenya.adt.migrator.exceptions;

/**
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
