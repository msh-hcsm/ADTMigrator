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
@Table(name = "tblSource")
@NamedQueries({
    @NamedQuery(name = "TblSource.findAll", query = "SELECT t FROM TblSource t")})
public class TblSource implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "SCode")
    private Short sCode;
    @Column(name = "Source")
    private String source;
    @Id
    @Column(name = "pk")
    private Integer pk;

    public TblSource() {
    }

    public TblSource(Integer pk) {
        this.pk = pk;
    }

    public Short getSCode() {
        return sCode;
    }

    public void setSCode(Short sCode) {
        this.sCode = sCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
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
        if (!(object instanceof TblSource)) {
            return false;
        }
        TblSource other = (TblSource) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblSource[ pk=" + pk + " ]";
    }
    
}
