package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblIndication implements Serializable {
    private static final long serialVersionUID = 1L;
    private String indicationCode;
    private String indicationName;

    public TblIndication() {
    }

    public TblIndication(String indicationCode) {
        this.indicationCode = indicationCode;
    }

    public String getIndicationCode() {
        return indicationCode;
    }

    public void setIndicationCode(String indicationCode) {
        this.indicationCode = indicationCode;
    }

    public String getIndicationName() {
        return indicationName;
    }

    public void setIndicationName(String indicationName) {
        this.indicationName = indicationName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indicationCode != null ? indicationCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblIndication)) {
            return false;
        }
        TblIndication other = (TblIndication) object;
        if ((this.indicationCode == null && other.indicationCode != null) || (this.indicationCode != null && !this.indicationCode.equals(other.indicationCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblIndication[ indicationCode=" + indicationCode + " ]";
    }
    
}
