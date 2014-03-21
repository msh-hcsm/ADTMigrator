package com.intellisoftkenya.adt.migrator.domain.adt;

import com.intellisoftkenya.adt.migrator.domain.adt.TblARTPatientMasterInformation;
import com.intellisoftkenya.adt.migrator.domain.adt.TblARVDrugStockMain;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author gitahi
 */
public class TblARTPatientTransactions implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date dateofVisit;
    private String brandName;
    private Integer transactionCode;
    private String unit;
    private Float aRVQty;
    private String dose;
    private Float duration;
    private String regimen;
    private String lastRegimen;
    private String comment;
    private String operator;
    private String indication;
    private Float weight;
    private Short pillCount;
    private Double adherence;
    private Integer daysLate;
    private String reasonsForChange;
    private String refOrderNo;
    private String batchNo;
    private Date expiryDate;
    private Integer patientTranNo;
    private Integer pk;
    private TblARVDrugStockMain tblARVDrugStockMain;
    private TblARTPatientMasterInformation tblARTPatientMasterInformation;

    public TblARTPatientTransactions() {
    }

    public TblARTPatientTransactions(Integer pk) {
        this.pk = pk;
    }

    public Date getDateofVisit() {
        return dateofVisit;
    }

    public void setDateofVisit(Date dateofVisit) {
        this.dateofVisit = dateofVisit;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(Integer transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getARVQty() {
        return aRVQty;
    }

    public void setARVQty(Float aRVQty) {
        this.aRVQty = aRVQty;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getLastRegimen() {
        return lastRegimen;
    }

    public void setLastRegimen(String lastRegimen) {
        this.lastRegimen = lastRegimen;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Short getPillCount() {
        return pillCount;
    }

    public void setPillCount(Short pillCount) {
        this.pillCount = pillCount;
    }

    public Double getAdherence() {
        return adherence;
    }

    public void setAdherence(Double adherence) {
        this.adherence = adherence;
    }

    public Integer getDaysLate() {
        return daysLate;
    }

    public void setDaysLate(Integer daysLate) {
        this.daysLate = daysLate;
    }

    public String getReasonsForChange() {
        return reasonsForChange;
    }

    public void setReasonsForChange(String reasonsForChange) {
        this.reasonsForChange = reasonsForChange;
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getPatientTranNo() {
        return patientTranNo;
    }

    public void setPatientTranNo(Integer patientTranNo) {
        this.patientTranNo = patientTranNo;
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

    public TblARTPatientMasterInformation getTblARTPatientMasterInformation() {
        return tblARTPatientMasterInformation;
    }

    public void setTblARTPatientMasterInformation(TblARTPatientMasterInformation tblARTPatientMasterInformation) {
        this.tblARTPatientMasterInformation = tblARTPatientMasterInformation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pk != null ? pk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblARTPatientTransactions)) {
            return false;
        }
        TblARTPatientTransactions other = (TblARTPatientTransactions) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblARTPatientTransactions[ pk=" + pk + " ]";
    }
    
}
