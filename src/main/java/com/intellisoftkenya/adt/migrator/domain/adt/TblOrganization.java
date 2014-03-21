package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblOrganization implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer organizationCode;
    private String organization;
    private Short adultage;
    private Integer maxPatientperday;
    private String district;
    private String region;
    private Boolean gokSupport;
    private Boolean mSFSupport;
    private Boolean pEPFARSupport;
    private Boolean aRTService;
    private Boolean pMTCTService;
    private Boolean pEPService;
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
