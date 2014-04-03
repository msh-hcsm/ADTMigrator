package com.intellisoftkenya.onetooner.business.imp;

import com.intellisoftkenya.onetooner.business.api.ValueTranslator;

/**
 * The {@link ValueTranslator} for account types.
 *
 * @author gitahi
 */
public class AccountTypeValueTranslator implements ValueTranslator {

    @Override
    public String translate(String value) {
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
