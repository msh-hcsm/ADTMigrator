package com.intellisoftkenya.onetooner.business;

/**
 * The {@link ReferenceProcessor} for account types.
 *
 * @author gitahi
 */
public class AccountTypeReferenceProcessor implements ReferenceProcessor {

    @Override
    public String process(String value) {
        if (value != null) {
            if (value.equalsIgnoreCase("KEMSA")
                    || value.equalsIgnoreCase("MEDS")
                    || value.equalsIgnoreCase("PEPFAR")
                    || value.equalsIgnoreCase("KENYA PHARMA")) {
                return "Supplier";
            } else if (value.equalsIgnoreCase("ART PHARMACY")
                    || value.equalsIgnoreCase("BULK STORE")
                    || value.equalsIgnoreCase("MAIN PHARMACY")
                    || value.equalsIgnoreCase("SATELLITE SITES")
                    || value.equalsIgnoreCase("STOCK TAKE")
                    || value.equalsIgnoreCase("DRUG STORE")
                    || value.equalsIgnoreCase("PATIENT RETURNS")) {
                return "Store";
            } else if (value.equalsIgnoreCase("PATIENTS")) {
                return "Patient";
            }
        }
        return null;
    }
}
