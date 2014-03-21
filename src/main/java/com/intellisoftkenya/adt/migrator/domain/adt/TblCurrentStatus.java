package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblCurrentStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer currentStatusID;
    private String currentStatus;

    public TblCurrentStatus() {
    }

    public TblCurrentStatus(Integer currentStatusID) {
        this.currentStatusID = currentStatusID;
    }

    public Integer getCurrentStatusID() {
        return currentStatusID;
    }

    public void setCurrentStatusID(Integer currentStatusID) {
        this.currentStatusID = currentStatusID;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (currentStatusID != null ? currentStatusID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblCurrentStatus)) {
            return false;
        }
        TblCurrentStatus other = (TblCurrentStatus) object;
        if ((this.currentStatusID == null && other.currentStatusID != null) || (this.currentStatusID != null && !this.currentStatusID.equals(other.currentStatusID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblCurrentStatus[ currentStatusID=" + currentStatusID + " ]";
    }
    
}
