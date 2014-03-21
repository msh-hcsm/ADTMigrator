package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblARVStockTranSourceorDestination implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer sDNo;
    private String sourceorDestination;
    private Integer pk;

    public TblARVStockTranSourceorDestination() {
    }

    public TblARVStockTranSourceorDestination(Integer pk) {
        this.pk = pk;
    }

    public Integer getSDNo() {
        return sDNo;
    }

    public void setSDNo(Integer sDNo) {
        this.sDNo = sDNo;
    }

    public String getSourceorDestination() {
        return sourceorDestination;
    }

    public void setSourceorDestination(String sourceorDestination) {
        this.sourceorDestination = sourceorDestination;
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
        if (!(object instanceof TblARVStockTranSourceorDestination)) {
            return false;
        }
        TblARVStockTranSourceorDestination other = (TblARVStockTranSourceorDestination) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblARVStockTranSourceorDestination[ pk=" + pk + " ]";
    }
    
}
