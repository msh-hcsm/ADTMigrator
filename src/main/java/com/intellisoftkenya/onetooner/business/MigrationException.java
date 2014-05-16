package com.intellisoftkenya.onetooner.business;

import com.intellisoftkenya.onetooner.data.OneToOne;

/**
 * Thrown whenever migration for a given {@link OneToOne} object fails for any
 * reason.
 *
 * @author gitahi
 */
public class MigrationException extends Exception {

    /**
     * The offending {@link OneToOne} object.
     */
    private final OneToOne oto;

    /**
     * Creates a new instance of <code>MigrationException</code> with the
     * offending {@link OneToOne} object
     *
     * @param oto the offending {@link OneToOne} object
     */
    public MigrationException(OneToOne oto) {
        this.oto = oto;
    }

    /**
     * Constructs an instance of <code>MigrationException</code> with the
     * specified detail message and the offending {@link OneToOne} object.
     *
     * @param msg the detail message.
     * @param oto the offending {@link OneToOne} object
     */
    public MigrationException(String msg, OneToOne oto) {
        super(msg);
        this.oto = oto;
    }

    /**
     * Constructs an instance of <code>MigrationException</code> with the
     * specified detail message and the offending {@link OneToOne} object.
     *
     * @param msg the detail message.
     * @param oto the offending {@link OneToOne} object
     * @param cause the underlying Exception
     */
    public MigrationException(String msg, OneToOne oto, Throwable cause) {
        super(msg, cause);
        this.oto = oto;
    }

    public OneToOne getOneToOne() {
        return oto;
    }
}
