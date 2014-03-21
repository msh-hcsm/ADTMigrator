package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblGenericName implements Serializable {
    private static final long serialVersionUID = 1L;
    private Short genID;
    private String genericName;

    public TblGenericName() {
    }

    public TblGenericName(Short genID) {
        this.genID = genID;
    }

    public Short getGenID() {
        return genID;
    }

    public void setGenID(Short genID) {
        this.genID = genID;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (genID != null ? genID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblGenericName)) {
            return false;
        }
        TblGenericName other = (TblGenericName) object;
        if ((this.genID == null && other.genID != null) || (this.genID != null && !this.genID.equals(other.genID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblGenericName[ genID=" + genID + " ]";
    }
    
}
