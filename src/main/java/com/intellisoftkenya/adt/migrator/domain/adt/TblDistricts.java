package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblDistricts implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer dCode;
    private String districtName;

    public TblDistricts() {
    }

    public TblDistricts(Integer dCode) {
        this.dCode = dCode;
    }

    public Integer getDCode() {
        return dCode;
    }

    public void setDCode(Integer dCode) {
        this.dCode = dCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dCode != null ? dCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblDistricts)) {
            return false;
        }
        TblDistricts other = (TblDistricts) object;
        if ((this.dCode == null && other.dCode != null) || (this.dCode != null && !this.dCode.equals(other.dCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblDistricts[ dCode=" + dCode + " ]";
    }
    
}
