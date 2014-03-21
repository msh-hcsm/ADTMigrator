package com.intellisoftkenya.adt.migrator;

/**
 *
 * @author gitahi
 */
public class NameableMeta {

    private final String adtTableName;
    private final String adtColumnName;
    private final String fdtTableName;
    private final String fdtColumnName;

    public NameableMeta(String adtTableName, String adtColumnName, String fdtTableName, String fdtColumnName) {
        this.adtTableName = adtTableName;
        this.adtColumnName = adtColumnName;
        this.fdtTableName = fdtTableName;
        this.fdtColumnName = fdtColumnName;
    }

    public String getAdtTableName() {
        return adtTableName;
    }

    public String getAdtColumnName() {
        return adtColumnName;
    }

    public String getFdtTableName() {
        return fdtTableName;
    }

    public String getFdtColumnName() {
        return fdtColumnName;
    }
}
