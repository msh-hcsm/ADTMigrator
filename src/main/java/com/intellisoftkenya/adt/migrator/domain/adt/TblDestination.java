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
@Table(name = "tblDestination")
@NamedQueries({
    @NamedQuery(name = "TblDestination.findAll", query = "SELECT t FROM TblDestination t")})
public class TblDestination implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "DCode")
    private Integer dCode;
    @Column(name = "Destination")
    private String destination;
    @Column(name = "RCode")
    private String rCode;
    @Column(name = "SortingCode")
    private String sortingCode;
    @Column(name = "source")
    private Integer source;
    @Id
    @Column(name = "pk")
    private Integer pk;

    public TblDestination() {
    }

    public TblDestination(Integer pk) {
        this.pk = pk;
    }

    public Integer getDCode() {
        return dCode;
    }

    public void setDCode(Integer dCode) {
        this.dCode = dCode;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRCode() {
        return rCode;
    }

    public void setRCode(String rCode) {
        this.rCode = rCode;
    }

    public String getSortingCode() {
        return sortingCode;
    }

    public void setSortingCode(String sortingCode) {
        this.sortingCode = sortingCode;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
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
        if (!(object instanceof TblDestination)) {
            return false;
        }
        TblDestination other = (TblDestination) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblDestination[ pk=" + pk + " ]";
    }
    
}
