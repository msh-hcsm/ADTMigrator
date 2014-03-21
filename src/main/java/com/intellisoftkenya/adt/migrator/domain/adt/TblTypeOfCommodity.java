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
@Table(name = "tblTypeOfCommodity")
@NamedQueries({
    @NamedQuery(name = "TblTypeOfCommodity.findAll", query = "SELECT t FROM TblTypeOfCommodity t")})
public class TblTypeOfCommodity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Lob
    @Column(name = "CommodityTypeCode")
    private String commodityTypeCode;
    @Lob
    @Column(name = "CommdityDescription")
    private String commdityDescription;
    @Id
    @Column(name = "pk")
    private Integer pk;

    public TblTypeOfCommodity() {
    }

    public TblTypeOfCommodity(Integer pk) {
        this.pk = pk;
    }

    public String getCommodityTypeCode() {
        return commodityTypeCode;
    }

    public void setCommodityTypeCode(String commodityTypeCode) {
        this.commodityTypeCode = commodityTypeCode;
    }

    public String getCommdityDescription() {
        return commdityDescription;
    }

    public void setCommdityDescription(String commdityDescription) {
        this.commdityDescription = commdityDescription;
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
        if (!(object instanceof TblTypeOfCommodity)) {
            return false;
        }
        TblTypeOfCommodity other = (TblTypeOfCommodity) object;
        if ((this.pk == null && other.pk != null) || (this.pk != null && !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adtmigrator.domain.adt.withoutpk.TblTypeOfCommodity[ pk=" + pk + " ]";
    }
    
}
