package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblReasonforChange implements Serializable {
    private static final long serialVersionUID = 1L;
    private Short reasonForChangeID;
    private String reasonforChange;

    public TblReasonforChange() {
    }

    public TblReasonforChange(Short reasonForChangeID) {
        this.reasonForChangeID = reasonForChangeID;
    }

    public Short getReasonForChangeID() {
        return reasonForChangeID;
    }

    public void setReasonForChangeID(Short reasonForChangeID) {
        this.reasonForChangeID = reasonForChangeID;
    }

    public String getReasonforChange() {
        return reasonforChange;
    }

    public void setReasonforChange(String reasonforChange) {
        this.reasonforChange = reasonforChange;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reasonForChangeID != null ? reasonForChangeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblReasonforChange)) {
            return false;
        }
        TblReasonforChange other = (TblReasonforChange) object;
        if ((this.reasonForChangeID == null && other.reasonForChangeID != null) || (this.reasonForChangeID != null && !this.reasonForChangeID.equals(other.reasonForChangeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblReasonforChange[ reasonForChangeID=" + reasonForChangeID + " ]";
    }
    
}
