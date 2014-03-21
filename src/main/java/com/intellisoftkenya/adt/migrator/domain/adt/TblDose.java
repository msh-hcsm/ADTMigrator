package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblDose implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dose;
    private Double value;
    private Integer frequency;

    public TblDose() {
    }

    public TblDose(String dose) {
        this.dose = dose;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dose != null ? dose.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblDose)) {
            return false;
        }
        TblDose other = (TblDose) object;
        if ((this.dose == null && other.dose != null) || (this.dose != null && !this.dose.equals(other.dose))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblDose[ dose=" + dose + " ]";
    }
    
}
