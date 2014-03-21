package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author gitahi
 */
public class TblAppointment implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date appDate;
    private Integer numAppointed;

    public TblAppointment() {
    }

    public TblAppointment(Date appDate) {
        this.appDate = appDate;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public Integer getNumAppointed() {
        return numAppointed;
    }

    public void setNumAppointed(Integer numAppointed) {
        this.numAppointed = numAppointed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appDate != null ? appDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblAppointment)) {
            return false;
        }
        TblAppointment other = (TblAppointment) object;
        if ((this.appDate == null && other.appDate != null) || (this.appDate != null && !this.appDate.equals(other.appDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblAppointment[ appDate=" + appDate + " ]";
    }
    
}
