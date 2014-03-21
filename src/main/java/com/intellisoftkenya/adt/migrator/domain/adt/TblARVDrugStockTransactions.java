package com.intellisoftkenya.adt.migrator.domain.adt;

import com.intellisoftkenya.adt.migrator.domain.adt.TblARVDrugStockMain;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author gitahi
 */
public class TblARVDrugStockTransactions implements Serializable {
    private static final long serialVersionUID = 1L;
    private String unit;
    private Date tranDate;
    private String refOrderNo;
    private String batchNo;
    private Integer transactionType;
    private String sourceorDestination;
    private String collectedBy;
    private Date expirydate;
    private Integer packSize;
    private Integer npacks;
    private BigInteger unitCost;
    private Integer qty;
    private BigInteger amount;
    private String remarks;
    private String operator;
    private Integer runningStock;
    private Integer stockTranNo;
    private Integer pk;
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
