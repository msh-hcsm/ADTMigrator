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
@Table(name = "tblRegimenCategory")
@NamedQueries({
    @NamedQuery(name = "TblRegimenCategory.findAll", query = "SELECT t FROM TblRegimenCategory t")})
public class TblRegimenCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CategoryID")
    private Integer categoryID;
    @Column(name = "CategoryName")
    private String categoryName;
    @OneToMany(mappedBy = "tblRegimenCategory")
    private List<TblRegimen> tblRegimenList;

    public TblRegimenCategory() {
    }

    public TblRegimenCategory(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<TblRegimen> getTblRegimenList() {
        return tblRegimenList;
    }

    public void setTblRegimenList(List<TblRegimen> tblRegimenList) {
        this.tblRegimenList = tblRegimenList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryID != null ? categoryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRegimenCategory)) {
            return false;
        }
        TblRegimenCategory other = (TblRegimenCategory) object;
        if ((this.categoryID == null && other.categoryID != null) || (this.categoryID != null && !this.categoryID.equals(other.categoryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblRegimenCategory[ categoryID=" + categoryID + " ]";
    }
    
}
