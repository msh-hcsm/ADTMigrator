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
import javax.persistence.Lob;
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
@Table(name = "tblDrugPhysicalMain")
@NamedQueries({
    @NamedQuery(name = "TblDrugPhysicalMain.findAll", query = "SELECT t FROM TblDrugPhysicalMain t")})
public class TblDrugPhysicalMain implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "PhyID")
    private Integer phyID;
    @Column(name = "PhyYear")
    private Short phyYear;
    @Column(name = "PhyMonth")
    private Short phyMonth;
    @Column(name = "PhyFromDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date phyFromDate;
    @Column(name = "PhyToDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date phyToDate;
    @Column(name = "BFStatus")
    private String bFStatus;
    @Lob
    @Column(name = "Remarks")
    private String remarks;
    @Id
    @Column(name = "TranDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranDate;

    public TblDrugPhysicalMain() {
    }

    public TblDrugPhysicalMain(Date tranDate) {
        this.tranDate = tranDate;
    }

    public Integer getPhyID() {
        return phyID;
    }

    public void setPhyID(Integer phyID) {
        this.phyID = phyID;
    }

    public Short getPhyYear() {
        return phyYear;
    }

    public void setPhyYear(Short phyYear) {
        this.phyYear = phyYear;
    }

    public Short getPhyMonth() {
        return phyMonth;
    }

    public void setPhyMonth(Short phyMonth) {
        this.phyMonth = phyMonth;
    }

    public Date getPhyFromDate() {
        return phyFromDate;
    }

    public void setPhyFromDate(Date phyFromDate) {
        this.phyFromDate = phyFromDate;
    }

    public Date getPhyToDate() {
        return phyToDate;
    }

    public void setPhyToDate(Date phyToDate) {
        this.phyToDate = phyToDate;
    }

    public String getBFStatus() {
        return bFStatus;
    }

    public void setBFStatus(String bFStatus) {
        this.bFStatus = bFStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tranDate != null ? tranDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblDrugPhysicalMain)) {
            return false;
        }
        TblDrugPhysicalMain other = (TblDrugPhysicalMain) object;
        if ((this.tranDate == null && other.tranDate != null) || (this.tranDate != null && !this.tranDate.equals(other.tranDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblDrugPhysicalMain[ tranDate=" + tranDate + " ]";
    }
    
}
