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
@Table(name = "tblRegion")
@NamedQueries({
    @NamedQuery(name = "TblRegion.findAll", query = "SELECT t FROM TblRegion t")})
public class TblRegion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "Rcode")
    private Integer rcode;
    @Column(name = "Region")
    private String region;
    @Id
    @Column(name = "pk")
    private Integer pk;

    public TblRegion() {
    }

    public TblRegion(Integer pk) {
        this.pk = pk;
    }

    public Integer getRcode() {
        return rcode;
    }

    public void setRcode(Integer rcode) {
        this.rcode = rcode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
        if (!(object instanceof TblRegion)) {
            return false;
        }
        TblRegion other = (TblRegion) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblRegion[ pk=" + pk + " ]";
    }
    
}
