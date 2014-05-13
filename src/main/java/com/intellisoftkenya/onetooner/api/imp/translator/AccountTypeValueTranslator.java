package com.intellisoftkenya.onetooner.api.imp.translator;

import com.intellisoftkenya.onetooner.api.translator.ValueTranslator;

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
                    ) {
                return "Supplier";
            } else if (value.equalsIgnoreCase("STOCK TAKE")
                    || value.equalsIgnoreCase("99")) {
                return "Adjustment";
            } else if (value.equalsIgnoreCase("PATIENTS")) {
                return "Patient";
            } else {
                return "Store";
            }
        }
        return null;
    }
}
