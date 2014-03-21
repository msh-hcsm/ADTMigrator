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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gitahi
 */
@Entity
@Table(name = "tblRegimen")
@NamedQueries({
    @NamedQuery(name = "TblRegimen.findAll", query = "SELECT t FROM TblRegimen t")})
public class TblRegimen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "Regimencode")
    private String regimencode;
    @Column(name = "Regimen")
    private String regimen;
    @Column(name = "Line")
    private Short line;
    @Column(name = "Remarks")
    private String remarks;
    @Column(name = "show")
    private Boolean show;
    @Column(name = "Status")
    private String status;
    @JoinColumn(name = "Category", referencedColumnName = "CategoryID")
    @ManyToOne
    private TblRegimenCategory tblRegimenCategory;

    public TblRegimen() {
    }

    public TblRegimen(String regimencode) {
        this.regimencode = regimencode;
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

    public Short getLine() {
        return line;
    }

    public void setLine(Short line) {
        this.line = line;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TblRegimenCategory getTblRegimenCategory() {
        return tblRegimenCategory;
    }

    public void setTblRegimenCategory(TblRegimenCategory tblRegimenCategory) {
        this.tblRegimenCategory = tblRegimenCategory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regimencode != null ? regimencode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRegimen)) {
            return false;
        }
        TblRegimen other = (TblRegimen) object;
        if ((this.regimencode == null && other.regimencode != null) || (this.regimencode != null && !this.regimencode.equals(other.regimencode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblRegimen[ regimencode=" + regimencode + " ]";
    }
    
}
