package com.intellisoftkenya.onetooner.business.imp;

import com.intellisoftkenya.onetooner.business.api.ValueTranslator;

/**
 * The {@link ValueTranslator} for drug categories.
 *
 * @author gitahi
 */
public class DrugCategoryValueTranslator implements ValueTranslator {

    @Override
    public String translate(String value) {
        if (value != null) {
            if (value.equalsIgnoreCase("1")) {
                return "Fixed Dose Combination";
            } else if (value.equalsIgnoreCase("2")) {
                return "Single drugs";
            } else if (value.equalsIgnoreCase("3")) {
                return "PAEDIATRIC Preparations";
            }else if (value.equalsIgnoreCase("4")) {
                return "OI Drugs";
            }
        }
        return null;
    }
}
