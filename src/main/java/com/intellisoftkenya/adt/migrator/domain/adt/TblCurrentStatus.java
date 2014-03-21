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
@Table(name = "tblCurrentStatus")
@NamedQueries({
    @NamedQuery(name = "TblCurrentStatus.findAll", query = "SELECT t FROM TblCurrentStatus t")})
public class TblCurrentStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CurrentStatusID")
    private Integer currentStatusID;
    @Column(name = "CurrentStatus")
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
