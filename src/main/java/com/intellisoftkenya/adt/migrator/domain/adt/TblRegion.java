package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblRegion implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer rcode;
    private String region;
    private Integer pk;

    public TblRegion() {
    }

    public TblRegion(Integer pk) {
        this.pk = pk;
    }

    public Integer getRcode() {
        return rcode;
    }

    public void setRcode(Integer rcode) {
        this.rcode = rcode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
        if (!(object instanceof TblRegion)) {
            return false;
        }
        TblRegion other = (TblRegion) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblRegion[ pk=" + pk + " ]";
    }
    
}
