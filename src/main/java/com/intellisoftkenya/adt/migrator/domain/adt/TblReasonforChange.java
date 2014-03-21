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
@Table(name = "tblReasonforChange")
@NamedQueries({
    @NamedQuery(name = "TblReasonforChange.findAll", query = "SELECT t FROM TblReasonforChange t")})
public class TblReasonforChange implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ReasonForChangeID")
    private Short reasonForChangeID;
    @Column(name = "ReasonforChange")
    private String reasonforChange;

    public TblReasonforChange() {
    }

    public TblReasonforChange(Short reasonForChangeID) {
        this.reasonForChangeID = reasonForChangeID;
    }

    public Short getReasonForChangeID() {
        return reasonForChangeID;
    }

    public void setReasonForChangeID(Short reasonForChangeID) {
        this.reasonForChangeID = reasonForChangeID;
    }

    public String getReasonforChange() {
        return reasonforChange;
    }

    public void setReasonforChange(String reasonforChange) {
        this.reasonforChange = reasonforChange;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reasonForChangeID != null ? reasonForChangeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblReasonforChange)) {
            return false;
        }
        TblReasonforChange other = (TblReasonforChange) object;
        if ((this.reasonForChangeID == null && other.reasonForChangeID != null) || (this.reasonForChangeID != null && !this.reasonForChangeID.equals(other.reasonForChangeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblReasonforChange[ reasonForChangeID=" + reasonForChangeID + " ]";
    }
    
}
