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
@Table(name = "tblClientSupportDetails")
@NamedQueries({
    @NamedQuery(name = "TblClientSupportDetails.findAll", query = "SELECT t FROM TblClientSupportDetails t")})
public class TblClientSupportDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ClientSupportID")
    private Short clientSupportID;
    @Column(name = "ClientSupportDesciption")
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
