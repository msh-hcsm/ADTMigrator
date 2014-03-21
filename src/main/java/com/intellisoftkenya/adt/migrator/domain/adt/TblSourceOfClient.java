package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author gitahi
 */
public class TblSourceOfClient implements Serializable {
    private static final long serialVersionUID = 1L;
    private Short sourceID;
    private String sourceOfClient;
    private List<TblARTPatientMasterInformation> tblARTPatientMasterInformationList;

    public TblSourceOfClient() {
    }

    public TblSourceOfClient(Short sourceID) {
        this.sourceID = sourceID;
    }

    public Short getSourceID() {
        return sourceID;
    }

    public void setSourceID(Short sourceID) {
        this.sourceID = sourceID;
    }

    public String getSourceOfClient() {
        return sourceOfClient;
    }

    public void setSourceOfClient(String sourceOfClient) {
        this.sourceOfClient = sourceOfClient;
    }

    public List<TblARTPatientMasterInformation> getTblARTPatientMasterInformationList() {
        return tblARTPatientMasterInformationList;
    }

    public void setTblARTPatientMasterInformationList(List<TblARTPatientMasterInformation> tblARTPatientMasterInformationList) {
        this.tblARTPatientMasterInformationList = tblARTPatientMasterInformationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sourceID != null ? sourceID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblSourceOfClient)) {
            return false;
        }
        TblSourceOfClient other = (TblSourceOfClient) object;
        if ((this.sourceID == null && other.sourceID != null) || (this.sourceID != null && !this.sourceID.equals(other.sourceID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblSourceOfClient[ sourceID=" + sourceID + " ]";
    }
    
}
