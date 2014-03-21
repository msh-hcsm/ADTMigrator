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
@Table(name = "tblUnit")
@NamedQueries({
    @NamedQuery(name = "TblUnit.findAll", query = "SELECT t FROM TblUnit t")})
public class TblUnit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "Unit")
    private String unit;

    public TblUnit() {
    }

    public TblUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (unit != null ? unit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblUnit)) {
            return false;
        }
        TblUnit other = (TblUnit) object;
        if ((this.unit == null && other.unit != null) || (this.unit != null && !this.unit.equals(other.unit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblUnit[ unit=" + unit + " ]";
    }
    
}
