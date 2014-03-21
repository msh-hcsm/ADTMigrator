package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author gitahi
 */
public class TblARVDrugStockMain implements Serializable {
    private List<TblARVDrugStockTransactions> tblARVDrugStockTransactionsList;
    private List<TblARTPatientTransactions> tblARTPatientTransactionsList;
    private static final long serialVersionUID = 1L;
    private String aRVDrugsID;
    private Integer drugCategory;
    private Integer toolOrder;
    private Integer packsizes;
    private String unit;
    private String genericName;
    private Integer miximumLevel;
    private Integer minimumLevel;
    private Integer reorderLevel;
    private Integer amc;
    private Integer saftyStock;
    private String comment;
    private Integer stockonHand;
    private Boolean isProphyDrug;
    private Integer supportedBy;
    private Integer supportedBy1;
    private Integer supportedBy2;
    private Integer supportedBy3;
    private Integer supportedBy4;
    private String stdDose;
    private Short stdDuration;
    private Integer stdQty;
    private String inRegimen;
    private String inUse;
    private Boolean tBDrug;

    public TblARVDrugStockMain() {
    }

    public TblARVDrugStockMain(String aRVDrugsID) {
        this.aRVDrugsID = aRVDrugsID;
    }

    public String getARVDrugsID() {
        return aRVDrugsID;
    }

    public void setARVDrugsID(String aRVDrugsID) {
        this.aRVDrugsID = aRVDrugsID;
    }

    public Integer getDrugCategory() {
        return drugCategory;
    }

    public void setDrugCategory(Integer drugCategory) {
        this.drugCategory = drugCategory;
    }

    public Integer getToolOrder() {
        return toolOrder;
    }

    public void setToolOrder(Integer toolOrder) {
        this.toolOrder = toolOrder;
    }

    public Integer getPacksizes() {
        return packsizes;
    }

    public void setPacksizes(Integer packsizes) {
        this.packsizes = packsizes;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public Integer getMiximumLevel() {
        return miximumLevel;
    }

    public void setMiximumLevel(Integer miximumLevel) {
        this.miximumLevel = miximumLevel;
    }

    public Integer getMinimumLevel() {
        return minimumLevel;
    }

    public void setMinimumLevel(Integer minimumLevel) {
        this.minimumLevel = minimumLevel;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Integer getAmc() {
        return amc;
    }

    public void setAmc(Integer amc) {
        this.amc = amc;
    }

    public Integer getSaftyStock() {
        return saftyStock;
    }

    public void setSaftyStock(Integer saftyStock) {
        this.saftyStock = saftyStock;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStockonHand() {
        return stockonHand;
    }

    public void setStockonHand(Integer stockonHand) {
        this.stockonHand = stockonHand;
    }

    public Boolean getIsProphyDrug() {
        return isProphyDrug;
    }

    public void setIsProphyDrug(Boolean isProphyDrug) {
        this.isProphyDrug = isProphyDrug;
    }

    public Integer getSupportedBy() {
        return supportedBy;
    }

    public void setSupportedBy(Integer supportedBy) {
        this.supportedBy = supportedBy;
    }

    public Integer getSupportedBy1() {
        return supportedBy1;
    }

    public void setSupportedBy1(Integer supportedBy1) {
        this.supportedBy1 = supportedBy1;
    }

    public Integer getSupportedBy2() {
        return supportedBy2;
    }

    public void setSupportedBy2(Integer supportedBy2) {
        this.supportedBy2 = supportedBy2;
    }

    public Integer getSupportedBy3() {
        return supportedBy3;
    }

    public void setSupportedBy3(Integer supportedBy3) {
        this.supportedBy3 = supportedBy3;
    }

    public Integer getSupportedBy4() {
        return supportedBy4;
    }

    public void setSupportedBy4(Integer supportedBy4) {
        this.supportedBy4 = supportedBy4;
    }

    public String getStdDose() {
        return stdDose;
    }

    public void setStdDose(String stdDose) {
        this.stdDose = stdDose;
    }

    public Short getStdDuration() {
        return stdDuration;
    }

    public void setStdDuration(Short stdDuration) {
        this.stdDuration = stdDuration;
    }

    public Integer getStdQty() {
        return stdQty;
    }

    public void setStdQty(Integer stdQty) {
        this.stdQty = stdQty;
    }

    public String getInRegimen() {
        return inRegimen;
    }

    public void setInRegimen(String inRegimen) {
        this.inRegimen = inRegimen;
    }

    public String getInUse() {
        return inUse;
    }

    public void setInUse(String inUse) {
        this.inUse = inUse;
    }

    public Boolean getTBDrug() {
        return tBDrug;
    }

    public void setTBDrug(Boolean tBDrug) {
        this.tBDrug = tBDrug;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aRVDrugsID != null ? aRVDrugsID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblARVDrugStockMain)) {
            return false;
        }
        TblARVDrugStockMain other = (TblARVDrugStockMain) object;
        if ((this.aRVDrugsID == null && other.aRVDrugsID != null) || (this.aRVDrugsID != null && !this.aRVDrugsID.equals(other.aRVDrugsID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblARVDrugStockMain[ aRVDrugsID=" + aRVDrugsID + " ]";
    }

    public List<TblARVDrugStockTransactions> getTblARVDrugStockTransactionsList() {
        return tblARVDrugStockTransactionsList;
    }

    public void setTblARVDrugStockTransactionsList(List<TblARVDrugStockTransactions> tblARVDrugStockTransactionsList) {
        this.tblARVDrugStockTransactionsList = tblARVDrugStockTransactionsList;
    }

    public List<TblARTPatientTransactions> getTblARTPatientTransactionsList() {
        return tblARTPatientTransactionsList;
    }

    public void setTblARTPatientTransactionsList(List<TblARTPatientTransactions> tblARTPatientTransactionsList) {
        this.tblARTPatientTransactionsList = tblARTPatientTransactionsList;
    }
    
}
