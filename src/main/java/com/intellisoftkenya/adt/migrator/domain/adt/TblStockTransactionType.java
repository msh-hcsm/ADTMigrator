/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gitahi
 */
@Entity
@Table(name = "tblStockTransactionType")
@NamedQueries({
    @NamedQuery(name = "TblStockTransactionType.findAll", query = "SELECT t FROM TblStockTransactionType t")})
public class TblStockTransactionType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "TransactionType")
    private Integer transactionType;
    @Lob
    @Column(name = "TransactionDescription")
    private String transactionDescription;
    @Lob
    @Column(name = "ReportTitle")
    private String reportTitle;
    @Id
    @Column(name = "pk")
    private Integer pk;

    public TblStockTransactionType() {
    }

    public TblStockTransactionType(Integer pk) {
        this.pk = pk;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
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
        if (!(object instanceof TblStockTransactionType)) {
            return false;
        }
        TblStockTransactionType other = (TblStockTransactionType) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblStockTransactionType[ pk=" + pk + " ]";
    }
    
}
