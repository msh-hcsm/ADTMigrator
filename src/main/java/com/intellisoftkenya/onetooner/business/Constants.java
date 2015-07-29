package com.intellisoftkenya.onetooner.business;

/**
 * Specifies "system wide" constants. Actually these aren't really generic so 
 * later on they should be pulled out of the core system.
 * 
 * TODO: Move these non-generic constants out of the core system.
 *
 * @author gitahi
 */
public class Constants {

    /**
     * The primary key value of the ART ID identifier type.
     */
    public static final Integer ART_IDENTIFIER_TYPE_ID = 1;

    /**
     * The primary key value of the OPDIPD ID identifier type.
     */
    public static final Integer OPIP_IDENTIFIER_TYPE_ID = 2;

    /**
     * The id of the "Dispensed to Patients" transaction type in the ADT
     * database.
     */
    public static final Integer DISPENSED_TO_PATIENTS_TX_TYPE = 6;

    /**
     * The pre-fix used in ADT in front of ART ID of a patient who received
     * drugs
     */
    public static final String DISPENSED_TO_ART_ID_PREFIX = "Dispensed to Patient No: ";
}
