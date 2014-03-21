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
@Table(name = "tblDrugsInRegimen")
@NamedQueries({
    @NamedQuery(name = "TblDrugsInRegimen.findAll", query = "SELECT t FROM TblDrugsInRegimen t")})
public class TblDrugsInRegimen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "Regimencode")
    private String regimencode;
    @Column(name = "Regimen")
    private String regimen;
    @Column(name = "Combinations")
    private String combinations;
    @Id
    @Column(name = "pk")
    private Integer pk;

    public TblDrugsInRegimen() {
    }

    public TblDrugsInRegimen(Integer pk) {
        this.pk = pk;
    }

    public String getRegimencode() {
        return regimencode;
    }

    public void setRegimencode(String regimencode) {
        this.regimencode = regimencode;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getCombinations() {
        return combinations;
    }

    public void setCombinations(String combinations) {
        this.combinations = combinations;
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
        if (!(object instanceof TblDrugsInRegimen)) {
            return false;
        }
        TblDrugsInRegimen other = (TblDrugsInRegimen) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblDrugsInRegimen[ pk=" + pk + " ]";
    }
    
}
