package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblHealthFacilities implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer mFLCode;
    private String facilityName;
    private String district;

    public TblHealthFacilities() {
    }

    public TblHealthFacilities(Integer mFLCode) {
        this.mFLCode = mFLCode;
    }

    public Integer getMFLCode() {
        return mFLCode;
    }

    public void setMFLCode(Integer mFLCode) {
        this.mFLCode = mFLCode;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mFLCode != null ? mFLCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblHealthFacilities)) {
            return false;
        }
        TblHealthFacilities other = (TblHealthFacilities) object;
        if ((this.mFLCode == null && other.mFLCode != null) || (this.mFLCode != null && !this.mFLCode.equals(other.mFLCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblHealthFacilities[ mFLCode=" + mFLCode + " ]";
    }
    
}
