/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author gitahi
 */
@Entity
@Table(name = "tblSourceOfClient")
@NamedQueries({
    @NamedQuery(name = "TblSourceOfClient.findAll", query = "SELECT t FROM TblSourceOfClient t")})
public class TblSourceOfClient implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "SourceID")
    private Short sourceID;
    @Column(name = "SourceOfClient")
    private String sourceOfClient;
    @OneToMany(mappedBy = "tblSourceOfClient")
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
