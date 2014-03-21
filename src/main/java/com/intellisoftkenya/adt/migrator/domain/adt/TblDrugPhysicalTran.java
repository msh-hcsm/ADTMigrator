package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author gitahi
 */
public class TblDrugPhysicalTran implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer phyID;
    private Short phyYear;
    private Short phyMonth;
    private String aRVDrugsID;
    private String unit;
    private String batchNo;
    private Date expiryDate;
    private Float bookQty;
    private Integer phyQty;
    private String remarks;
    private byte[] upsizeTs;
    private Date tranDate;
    private Integer code;
    private Integer pk;

    public TblDrugPhysicalTran() {
    }

    public TblDrugPhysicalTran(Integer pk) {
        this.pk = pk;
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

    public String getARVDrugsID() {
        return aRVDrugsID;
    }

    public void setARVDrugsID(String aRVDrugsID) {
        this.aRVDrugsID = aRVDrugsID;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Float getBookQty() {
        return bookQty;
    }

    public void setBookQty(Float bookQty) {
        this.bookQty = bookQty;
    }

    public Integer getPhyQty() {
        return phyQty;
    }

    public void setPhyQty(Integer phyQty) {
        this.phyQty = phyQty;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public byte[] getUpsizeTs() {
        return upsizeTs;
    }

    public void setUpsizeTs(byte[] upsizeTs) {
        this.upsizeTs = upsizeTs;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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
        if (!(object instanceof TblDrugPhysicalTran)) {
            return false;
        }
        TblDrugPhysicalTran other = (TblDrugPhysicalTran) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblDrugPhysicalTran[ pk=" + pk + " ]";
    }
    
}
