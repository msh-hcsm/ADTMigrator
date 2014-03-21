package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author gitahi
 */
public class TblDrugStockBatch implements Serializable {
    private static final long serialVersionUID = 1L;
    private String aRVDrugsID;
    private String batchNo;
    private String unit;
    private Date expiryDate;
    private Integer packSize;
    private Float unitCost;
    private Float quantity;
    private byte[] upsizeTs;
    private Date tranDate;
    private Integer pk;

    public TblDrugStockBatch() {
    }

    public TblDrugStockBatch(Integer pk) {
        this.pk = pk;
    }

    public String getARVDrugsID() {
        return aRVDrugsID;
    }

    public void setARVDrugsID(String aRVDrugsID) {
        this.aRVDrugsID = aRVDrugsID;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getPackSize() {
        return packSize;
    }

    public void setPackSize(Integer packSize) {
        this.packSize = packSize;
    }

    public Float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Float unitCost) {
        this.unitCost = unitCost;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
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
        if (!(object instanceof TblDrugStockBatch)) {
            return false;
        }
        TblDrugStockBatch other = (TblDrugStockBatch) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblDrugStockBatch[ pk=" + pk + " ]";
    }
    
}
