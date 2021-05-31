/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Jack
 */
@Entity
@Table(catalog = "ssc", schema = "public")
//@XmlRootElement
public class Energymesure implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal voltage;
    @Basic(optional = false)
   
    @Column(name = "current_", nullable = true, precision = 10, scale = 4)
    private BigDecimal current;
    @Basic(optional = false)
    
    @Column(name = "active_power", nullable = true, precision = 10, scale = 4)
    private BigDecimal activePower;
    @Basic(optional = false)
    
    @Column(name = "apparent_power", nullable = true, precision = 10, scale = 4)
    private BigDecimal apparentPower;
    @Basic(optional = false)
    
    @Column(name = "reactive_power", nullable = true, precision = 10, scale = 4)
    private BigDecimal reactivePower;
    @Basic(optional = false)
    
    @Column(name = "power_factor", nullable = true, precision = 10, scale = 4)
    private BigDecimal powerFactor;
    @Basic(optional = false)
    
    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal frequency;
    @Basic(optional = false)
    
    @Column(name = "i_active_en", nullable = true, precision = 10, scale = 4)
    private BigDecimal iActiveEn;
    @Basic(optional = false)
    
    @Column(name = "e_actve_en", nullable = true, precision = 10, scale = 4)
    private BigDecimal eActveEn;
    
    @Basic(optional = false)    
    @Column(name = "t_actvie_en", nullable = true, precision = 10, scale = 4)
    private BigDecimal tActvieEn;
    @JoinColumn(name = "plc", referencedColumnName = "id")
    @ManyToOne
    private Plc plc;
    @JoinColumn(name = "ssc", referencedColumnName = "id")
    @ManyToOne
    private Ssc ssc;

    @Column(name = "d_ins", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dins;
    
    @Column(name = "resource", nullable = true)
    private Long resource;
    
    
    public Energymesure() {
    }

    public Energymesure(Short id) {
        this.id = id;
    }

    public Energymesure(Short id, BigDecimal voltage, BigDecimal current, BigDecimal activePower, BigDecimal apparentPower, BigDecimal reactivePower, BigDecimal powerFactor, BigDecimal frequency, BigDecimal iActiveEn, BigDecimal eActveEn, BigDecimal tActvieEn) {
        this.id = id;
        this.voltage = voltage;
        this.current = current;
        this.activePower = activePower;
        this.apparentPower = apparentPower;
        this.reactivePower = reactivePower;
        this.powerFactor = powerFactor;
        this.frequency = frequency;
        this.iActiveEn = iActiveEn;
        this.eActveEn = eActveEn;
        this.tActvieEn = tActvieEn;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public BigDecimal getVoltage() {
        return voltage;
    }

    public void setVoltage(BigDecimal voltage) {
        this.voltage = voltage;
    }

    public BigDecimal getCurrent() {
        return current;
    }

    public void setCurrent(BigDecimal current) {
        this.current = current;
    }

    public BigDecimal getActivePower() {
        return activePower;
    }

    public void setActivePower(BigDecimal activePower) {
        this.activePower = activePower;
    }

    public BigDecimal getApparentPower() {
        return apparentPower;
    }

    public void setApparentPower(BigDecimal apparentPower) {
        this.apparentPower = apparentPower;
    }

    public BigDecimal getReactivePower() {
        return reactivePower;
    }

    public void setReactivePower(BigDecimal reactivePower) {
        this.reactivePower = reactivePower;
    }

    public BigDecimal getPowerFactor() {
        return powerFactor;
    }

    public void setPowerFactor(BigDecimal powerFactor) {
        this.powerFactor = powerFactor;
    }

    public BigDecimal getFrequency() {
        return frequency;
    }

    public void setFrequency(BigDecimal frequency) {
        this.frequency = frequency;
    }

    public BigDecimal getIActiveEn() {
        return iActiveEn;
    }

    public void setIActiveEn(BigDecimal iActiveEn) {
        this.iActiveEn = iActiveEn;
    }

    public BigDecimal getEActveEn() {
        return eActveEn;
    }

    public void setEActveEn(BigDecimal eActveEn) {
        this.eActveEn = eActveEn;
    }

    public BigDecimal getTActvieEn() {
        return tActvieEn;
    }

    public void setTActvieEn(BigDecimal tActvieEn) {
        this.tActvieEn = tActvieEn;
    }

    public Plc getPlc() {
        return plc;
    }

    public void setPlc(Plc plc) {
        this.plc = plc;
    }

    public Ssc getSsc() {
        return ssc;
    }

    public void setSsc(Ssc ssc) {
        this.ssc = ssc;
    }

    public Date getDins() {
        return dins;
    }

    public void setDins(Date dins) {
        this.dins = dins;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Energymesure)) {
            return false;
        }
        Energymesure other = (Energymesure) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mude.srl.ssc.entity.utils.Energymesure[ id=" + id + " ]";
    }
    
}
