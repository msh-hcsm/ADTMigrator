/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author gitahi
 */
@Entity
@Table(name = "tblARVDrugStockMain")
@NamedQueries({
    @NamedQuery(name = "TblARVDrugStockMain.findAll", query = "SELECT t FROM TblARVDrugStockMain t")})
public class TblARVDrugStockMain implements Serializable {
    @OneToMany(mappedBy = "tblARVDrugStockMain")
    private List<TblARVDrugStockTransactions> tblARVDrugStockTransactionsList;
    @OneToMany(mappedBy = "tblARVDrugStockMain")
    private List<TblARTPatientTransactions> tblARTPatientTransactionsList;
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ARVDrugsID")
    private String aRVDrugsID;
    @Column(name = "DrugCategory")
    private Integer drugCategory;
    @Column(name = "ToolOrder")
    private Integer toolOrder;
    @Column(name = "Packsizes")
    private Integer packsizes;
    @Column(name = "Unit")
    private String unit;
    @Column(name = "GenericName")
    private String genericName;
    @Column(name = "MiximumLevel")
    private Integer miximumLevel;
    @Column(name = "MinimumLevel")
    private Integer minimumLevel;
    @Column(name = "ReorderLevel")
    private Integer reorderLevel;
    @Column(name = "AMC")
    private Integer amc;
    @Column(name = "SaftyStock")
    private Integer saftyStock;
    @Lob
    @Column(name = "Comment")
    private String comment;
    @Column(name = "StockonHand")
    private Integer stockonHand;
    @Column(name = "IsProphyDrug")
    private Boolean isProphyDrug;
    @Column(name = "SupportedBy")
    private Integer supportedBy;
    @Column(name = "SupportedBy1")
    private Integer supportedBy1;
    @Column(name = "SupportedBy2")
    private Integer supportedBy2;
    @Column(name = "SupportedBy3")
    private Integer supportedBy3;
    @Column(name = "SupportedBy4")
    private Integer supportedBy4;
    @Column(name = "StdDose")
    private String stdDose;
    @Column(name = "StdDuration")
    private Short stdDuration;
    @Column(name = "StdQty")
    private Integer stdQty;
    @Column(name = "InRegimen")
    private String inRegimen;
    @Column(name = "InUse")
    private String inUse;
    @Column(name = "TBDrug")
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
