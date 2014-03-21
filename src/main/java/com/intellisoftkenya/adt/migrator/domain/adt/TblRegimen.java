package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblRegimen implements Serializable {
    private static final long serialVersionUID = 1L;
    private String regimencode;
    private String regimen;
    private Short line;
    private String remarks;
    private Boolean show;
    private String status;
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
