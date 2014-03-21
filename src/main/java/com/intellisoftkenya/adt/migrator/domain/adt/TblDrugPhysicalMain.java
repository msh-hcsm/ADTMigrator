package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author gitahi
 */
public class TblDrugPhysicalMain implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer phyID;
    private Short phyYear;
    private Short phyMonth;
    private Date phyFromDate;
    private Date phyToDate;
    private String bFStatus;
    private String remarks;
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
