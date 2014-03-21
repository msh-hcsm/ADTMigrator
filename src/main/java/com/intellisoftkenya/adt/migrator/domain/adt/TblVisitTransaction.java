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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gitahi
 */
@Entity
@Table(name = "tblVisitTransaction")
@NamedQueries({
    @NamedQuery(name = "TblVisitTransaction.findAll", query = "SELECT t FROM TblVisitTransaction t")})
public class TblVisitTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "TransactionCode")
    private Integer transactionCode;
    @Column(name = "VisitTranName")
    private String visitTranName;

    public TblVisitTransaction() {
    }

    public TblVisitTransaction(Integer transactionCode) {
        this.transactionCode = transactionCode;
    }

    public Integer getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(Integer transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getVisitTranName() {
        return visitTranName;
    }

    public void setVisitTranName(String visitTranName) {
        this.visitTranName = visitTranName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionCode != null ? transactionCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblVisitTransaction)) {
            return false;
        }
        TblVisitTransaction other = (TblVisitTransaction) object;
        if ((this.transactionCode == null && other.transactionCode != null) || (this.transactionCode != null && !this.transactionCode.equals(other.transactionCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblVisitTransaction[ transactionCode=" + transactionCode + " ]";
    }
    
}
