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
@Table(name = "tblDrugStockBatch")
@NamedQueries({
    @NamedQuery(name = "TblDrugStockBatch.findAll", query = "SELECT t FROM TblDrugStockBatch t")})
public class TblDrugStockBatch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "ARVDrugsID")
    private String aRVDrugsID;
    @Column(name = "BatchNo")
    private String batchNo;
    @Column(name = "Unit")
    private String unit;
    @Column(name = "ExpiryDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;
    @Column(name = "PackSize")
    private Integer packSize;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "UnitCost")
    private Float unitCost;
    @Column(name = "Quantity")
    private Float quantity;
    @Lob
    @Column(name = "upsize_ts")
    private byte[] upsizeTs;
    @Column(name = "TranDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranDate;
    @Id
    @Column(name = "pk")
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
