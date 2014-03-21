package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblClientSupportDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    private Short clientSupportID;
    private String clientSupportDesciption;

    public TblClientSupportDetails() {
    }

    public TblClientSupportDetails(Short clientSupportID) {
        this.clientSupportID = clientSupportID;
    }

    public Short getClientSupportID() {
        return clientSupportID;
    }

    public void setClientSupportID(Short clientSupportID) {
        this.clientSupportID = clientSupportID;
    }

    public String getClientSupportDesciption() {
        return clientSupportDesciption;
    }

    public void setClientSupportDesciption(String clientSupportDesciption) {
        this.clientSupportDesciption = clientSupportDesciption;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientSupportID != null ? clientSupportID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblClientSupportDetails)) {
            return false;
        }
        TblClientSupportDetails other = (TblClientSupportDetails) object;
        if ((this.clientSupportID == null && other.clientSupportID != null) || (this.clientSupportID != null && !this.clientSupportID.equals(other.clientSupportID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblClientSupportDetails[ clientSupportID=" + clientSupportID + " ]";
    }
    
}
