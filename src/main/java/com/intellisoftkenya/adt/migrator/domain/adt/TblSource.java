package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblSource implements Serializable {
    private static final long serialVersionUID = 1L;
    private Short sCode;
    private String source;
    private Integer pk;

    public TblSource() {
    }

    public TblSource(Integer pk) {
        this.pk = pk;
    }

    public Short getSCode() {
        return sCode;
    }

    public void setSCode(Short sCode) {
        this.sCode = sCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
        if (!(object instanceof TblSource)) {
            return false;
        }
        TblSource other = (TblSource) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblSource[ pk=" + pk + " ]";
    }
    
}
