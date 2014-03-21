package com.intellisoftkenya.adt.migrator.domain.adt;

import java.io.Serializable;

/**
 *
 * @author gitahi
 */
public class TblTypeOfCommodity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String commodityTypeCode;
    private String commdityDescription;
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
