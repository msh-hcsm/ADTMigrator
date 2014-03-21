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
@Table(name = "tblOrganization")
@NamedQueries({
    @NamedQuery(name = "TblOrganization.findAll", query = "SELECT t FROM TblOrganization t")})
public class TblOrganization implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "OrganizationCode")
    private Integer organizationCode;
    @Column(name = "Organization")
    private String organization;
    @Column(name = "Adultage")
    private Short adultage;
    @Column(name = "MaxPatientperday")
    private Integer maxPatientperday;
    @Column(name = "District")
    private String district;
    @Column(name = "Region")
    private String region;
    @Column(name = "GokSupport")
    private Boolean gokSupport;
    @Column(name = "MSFSupport")
    private Boolean mSFSupport;
    @Column(name = "PEPFARSupport")
    private Boolean pEPFARSupport;
    @Column(name = "ARTService")
    private Boolean aRTService;
    @Column(name = "PMTCTService")
    private Boolean pMTCTService;
    @Column(name = "PEPService")
    private Boolean pEPService;
    @Id
    @Column(name = "pk")
    private Integer pk;

    public TblOrganization() {
    }

    public TblOrganization(Integer pk) {
        this.pk = pk;
    }

    public Integer getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(Integer organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Short getAdultage() {
        return adultage;
    }

    public void setAdultage(Short adultage) {
        this.adultage = adultage;
    }

    public Integer getMaxPatientperday() {
        return maxPatientperday;
    }

    public void setMaxPatientperday(Integer maxPatientperday) {
        this.maxPatientperday = maxPatientperday;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getGokSupport() {
        return gokSupport;
    }

    public void setGokSupport(Boolean gokSupport) {
        this.gokSupport = gokSupport;
    }

    public Boolean getMSFSupport() {
        return mSFSupport;
    }

    public void setMSFSupport(Boolean mSFSupport) {
        this.mSFSupport = mSFSupport;
    }

    public Boolean getPEPFARSupport() {
        return pEPFARSupport;
    }

    public void setPEPFARSupport(Boolean pEPFARSupport) {
        this.pEPFARSupport = pEPFARSupport;
    }

    public Boolean getARTService() {
        return aRTService;
    }

    public void setARTService(Boolean aRTService) {
        this.aRTService = aRTService;
    }

    public Boolean getPMTCTService() {
        return pMTCTService;
    }

    public void setPMTCTService(Boolean pMTCTService) {
        this.pMTCTService = pMTCTService;
    }

    public Boolean getPEPService() {
        return pEPService;
    }

    public void setPEPService(Boolean pEPService) {
        this.pEPService = pEPService;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblOrganization)) {
            return false;
        }
        TblOrganization other = (TblOrganization) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblOrganization[ pk=" + pk + " ]";
    }
    
}
