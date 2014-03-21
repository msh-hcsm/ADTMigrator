package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblVisitTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer transactionCode;
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
