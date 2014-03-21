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
@Table(name = "tblGenericName")
@NamedQueries({
    @NamedQuery(name = "TblGenericName.findAll", query = "SELECT t FROM TblGenericName t")})
public class TblGenericName implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "GenID")
    private Short genID;
    @Column(name = "GenericName")
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
