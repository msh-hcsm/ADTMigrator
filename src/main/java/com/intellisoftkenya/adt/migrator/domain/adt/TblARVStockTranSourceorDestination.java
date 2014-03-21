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
@Table(name = "tblARVStockTranSourceorDestination")
@NamedQueries({
    @NamedQuery(name = "TblARVStockTranSourceorDestination.findAll", query = "SELECT t FROM TblARVStockTranSourceorDestination t")})
public class TblARVStockTranSourceorDestination implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "SDNo")
    private Integer sDNo;
    @Column(name = "SourceorDestination")
    private String sourceorDestination;
    @Id
    @Column(name = "pk")
    private Integer pk;

    public TblARVStockTranSourceorDestination() {
    }

    public TblARVStockTranSourceorDestination(Integer pk) {
        this.pk = pk;
    }

    public Integer getSDNo() {
        return sDNo;
    }

    public void setSDNo(Integer sDNo) {
        this.sDNo = sDNo;
    }

    public String getSourceorDestination() {
        return sourceorDestination;
    }

    public void setSourceorDestination(String sourceorDestination) {
        this.sourceorDestination = sourceorDestination;
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
        if (!(object instanceof TblARVStockTranSourceorDestination)) {
            return false;
        }
        TblARVStockTranSourceorDestination other = (TblARVStockTranSourceorDestination) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblARVStockTranSourceorDestination[ pk=" + pk + " ]";
    }
    
}
