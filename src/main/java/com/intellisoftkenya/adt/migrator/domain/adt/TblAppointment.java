/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gitahi
 */
@Entity
@Table(name = "tblAppointment")
@NamedQueries({
    @NamedQuery(name = "TblAppointment.findAll", query = "SELECT t FROM TblAppointment t")})
public class TblAppointment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "AppDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appDate;
    @Column(name = "NumAppointed")
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
