/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intellisoftkenya.adt.migrator.domain.adt;

import com.intellisoftkenya.adt.migrator.domain.adt.TblARVDrugStockMain;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tblARVDrugStockTransactions")
@NamedQueries({
    @NamedQuery(name = "TblARVDrugStockTransactions.findAll", query = "SELECT t FROM TblARVDrugStockTransactions t")})
public class TblARVDrugStockTransactions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "Unit")
    private String unit;
    @Column(name = "TranDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranDate;
    @Column(name = "RefOrderNo")
    private String refOrderNo;
    @Column(name = "BatchNo")
    private String batchNo;
    @Column(name = "TransactionType")
    private Integer transactionType;
    @Column(name = "SourceorDestination")
    private String sourceorDestination;
    @Column(name = "CollectedBy")
    private String collectedBy;
    @Column(name = "Expirydate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirydate;
    @Column(name = "PackSize")
    private Integer packSize;
    @Column(name = "Npacks")
    private Integer npacks;
    @Column(name = "UnitCost")
    private BigInteger unitCost;
    @Column(name = "Qty")
    private Integer qty;
    @Column(name = "Amount")
    private BigInteger amount;
    @Column(name = "Remarks")
    private String remarks;
    @Column(name = "operator")
    private String operator;
    @Column(name = "RunningStock")
    private Integer runningStock;
    @Column(name = "StockTranNo")
    private Integer stockTranNo;
    @Id
    @Column(name = "pk")
    private Integer pk;
    @JoinColumn(name = "ARVDrugsID", referencedColumnName = "ARVDrugsID")
    @ManyToOne
    private TblARVDrugStockMain tblARVDrugStockMain;

    public TblARVDrugStockTransactions() {
    }

    public TblARVDrugStockTransactions(Integer pk) {
        this.pk = pk;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public String getRefOrderNo() {
        return refOrderNo;
    }

    public void setRefOrderNo(String refOrderNo) {
        this.refOrderNo = refOrderNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public String getSourceorDestination() {
        return sourceorDestination;
    }

    public void setSourceorDestination(String sourceorDestination) {
        this.sourceorDestination = sourceorDestination;
    }

    public String getCollectedBy() {
        return collectedBy;
    }

    public void setCollectedBy(String collectedBy) {
        this.collectedBy = collectedBy;
    }

    public Date getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }

    public Integer getPackSize() {
        return packSize;
    }

    public void setPackSize(Integer packSize) {
        this.packSize = packSize;
    }

    public Integer getNpacks() {
        return npacks;
    }

    public void setNpacks(Integer npacks) {
        this.npacks = npacks;
    }

    public BigInteger getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigInteger unitCost) {
        this.unitCost = unitCost;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getRunningStock() {
        return runningStock;
    }

    public void setRunningStock(Integer runningStock) {
        this.runningStock = runningStock;
    }

    public Integer getStockTranNo() {
        return stockTranNo;
    }

    public void setStockTranNo(Integer stockTranNo) {
        this.stockTranNo = stockTranNo;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public TblARVDrugStockMain getTblARVDrugStockMain() {
        return tblARVDrugStockMain;
    }

    public void setTblARVDrugStockMain(TblARVDrugStockMain tblARVDrugStockMain) {
        this.tblARVDrugStockMain = tblARVDrugStockMain;
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
        if (!(object instanceof TblARVDrugStockTransactions)) {
            return false;
        }
        TblARVDrugStockTransactions other = (TblARVDrugStockTransactions) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblARVDrugStockTransactions[ pk=" + pk + " ]";
    }
    
}
