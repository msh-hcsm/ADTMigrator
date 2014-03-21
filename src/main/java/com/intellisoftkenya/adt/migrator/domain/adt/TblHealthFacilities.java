/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gitahi
 */
@Entity
@Table(name = "tblHealthFacilities")
@NamedQueries({
    @NamedQuery(name = "TblHealthFacilities.findAll", query = "SELECT t FROM TblHealthFacilities t")})
public class TblHealthFacilities implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "MFLCode")
    private Integer mFLCode;
    @Column(name = "FacilityName")
    private String facilityName;
    @Column(name = "District")
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
