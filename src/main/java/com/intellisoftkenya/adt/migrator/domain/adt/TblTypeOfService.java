package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblTypeOfService implements Serializable {
    private static final long serialVersionUID = 1L;
    private Short typeOfServiceID;
    private String typeofService;
    private Integer pk;

    public TblTypeOfService() {
    }

    public TblTypeOfService(Integer pk) {
        this.pk = pk;
    }

    public Short getTypeOfServiceID() {
        return typeOfServiceID;
    }

    public void setTypeOfServiceID(Short typeOfServiceID) {
        this.typeOfServiceID = typeOfServiceID;
    }

    public String getTypeofService() {
        return typeofService;
    }

    public void setTypeofService(String typeofService) {
        this.typeofService = typeofService;
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
        if (!(object instanceof TblTypeOfService)) {
            return false;
        }
        TblTypeOfService other = (TblTypeOfService) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblTypeOfService[ pk=" + pk + " ]";
    }
    
}
