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
@Table(name = "tblTypeOfService")
@NamedQueries({
    @NamedQuery(name = "TblTypeOfService.findAll", query = "SELECT t FROM TblTypeOfService t")})
public class TblTypeOfService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "TypeOfServiceID")
    private Short typeOfServiceID;
    @Column(name = "TypeofService")
    private String typeofService;
    @Id
    @Column(name = "pk")
    private Integer pk;

    public TblTypeOfService() {
    }

    public TblTypeOfService(Integer pk) {
        this.pk = pk;
    }

    public Short getTypeOfServiceID() {
        return typeOfServiceID;
    }

    public void setTypeOfServiceID(Short typeOfServiceID) {
        this.typeOfServiceID = typeOfServiceID;
    }

    public String getTypeofService() {
        return typeofService;
    }

    public void setTypeofService(String typeofService) {
        this.typeofService = typeofService;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblTypeOfService)) {
            return false;
        }
        TblTypeOfService other = (TblTypeOfService) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblTypeOfService[ pk=" + pk + " ]";
    }
    
}
