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
@Table(name = "tblDistricts")
@NamedQueries({
    @NamedQuery(name = "TblDistricts.findAll", query = "SELECT t FROM TblDistricts t")})
public class TblDistricts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "DCode")
    private Integer dCode;
    @Column(name = "DistrictName")
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
