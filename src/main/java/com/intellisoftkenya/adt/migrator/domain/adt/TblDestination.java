package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblDestination implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer dCode;
    private String destination;
    private String rCode;
    private String sortingCode;
    private Integer source;
    private Integer pk;

    public TblDestination() {
    }

    public TblDestination(Integer pk) {
        this.pk = pk;
    }

    public Integer getDCode() {
        return dCode;
    }

    public void setDCode(Integer dCode) {
        this.dCode = dCode;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRCode() {
        return rCode;
    }

    public void setRCode(String rCode) {
        this.rCode = rCode;
    }

    public String getSortingCode() {
        return sortingCode;
    }

    public void setSortingCode(String sortingCode) {
        this.sortingCode = sortingCode;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
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
        if (!(object instanceof TblDestination)) {
            return false;
        }
        TblDestination other = (TblDestination) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblDestination[ pk=" + pk + " ]";
    }
    
}
