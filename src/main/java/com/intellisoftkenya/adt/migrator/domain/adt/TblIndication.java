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
@Table(name = "tblIndication")
@NamedQueries({
    @NamedQuery(name = "TblIndication.findAll", query = "SELECT t FROM TblIndication t")})
public class TblIndication implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "indicationCode")
    private String indicationCode;
    @Column(name = "IndicationName")
    private String indicationName;

    public TblIndication() {
    }

    public TblIndication(String indicationCode) {
        this.indicationCode = indicationCode;
    }

    public String getIndicationCode() {
        return indicationCode;
    }

    public void setIndicationCode(String indicationCode) {
        this.indicationCode = indicationCode;
    }

    public String getIndicationName() {
        return indicationName;
    }

    public void setIndicationName(String indicationName) {
        this.indicationName = indicationName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indicationCode != null ? indicationCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblIndication)) {
            return false;
        }
        TblIndication other = (TblIndication) object;
        if ((this.indicationCode == null && other.indicationCode != null) || (this.indicationCode != null && !this.indicationCode.equals(other.indicationCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withpk.TblIndication[ indicationCode=" + indicationCode + " ]";
    }
    
}
