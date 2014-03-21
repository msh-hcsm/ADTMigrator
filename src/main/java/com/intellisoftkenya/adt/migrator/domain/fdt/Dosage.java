package com.intellisoftkenya.adt.migrator.domain.fdt;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 * @author gitahi
 */
public class Dosage extends DomainObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private BigDecimal value;
    private int frequency;
    private String description;

    public Dosage() {
    }

    public Dosage(Integer id) {
        this.id = id;
    }

    public Dosage(Integer id, String name, BigDecimal value, int frequency) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.frequency = frequency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Dosage)) {
            return false;
        }
        Dosage other = (Dosage) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "com.intellisoftkenya.adt.migrator.domain.fdt.Dosage[ id=" + id + " ]";
    }
    
}
