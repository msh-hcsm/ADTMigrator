package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblDrugsInRegimen implements Serializable {
    private static final long serialVersionUID = 1L;
    private String regimencode;
    private String regimen;
    private String combinations;
    private Integer pk;

    public TblDrugsInRegimen() {
    }

    public TblDrugsInRegimen(Integer pk) {
        this.pk = pk;
    }

    public String getRegimencode() {
        return regimencode;
    }

    public void setRegimencode(String regimencode) {
        this.regimencode = regimencode;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getCombinations() {
        return combinations;
    }

    public void setCombinations(String combinations) {
        this.combinations = combinations;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pk != null ? pk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblDrugsInRegimen)) {
            return false;
        }
        TblDrugsInRegimen other = (TblDrugsInRegimen) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblDrugsInRegimen[ pk=" + pk + " ]";
    }
    
}
