package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author gitahi
 */
public class TblRegimenCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer categoryID;
    private String categoryName;
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
