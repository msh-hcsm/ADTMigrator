package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblUnit implements Serializable {
    private static final long serialVersionUID = 1L;
    private String unit;

    public TblUnit() {
    }

    public TblUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (unit != null ? unit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblUnit)) {
            return false;
        }
        TblUnit other = (TblUnit) object;
        if ((this.unit == null && other.unit != null) || (this.unit != null && !this.unit.equals(other.unit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblUnit[ unit=" + unit + " ]";
    }
    
}
