package com.intellisoftkenya.onetooner.business;

/**
 * The {@link ReferenceProcessor} for drug categories.
 *
 * @author gitahi
 */
public class DrugCategoryReferenceProcessor implements ReferenceProcessor {

    @Override
    public String process(String value) {
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
