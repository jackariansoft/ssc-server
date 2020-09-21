/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jack
 */
@Entity
@Table(catalog = "ssc", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plc.findAll", query = "SELECT p FROM Plc p"),
    @NamedQuery(name = "Plc.findById", query = "SELECT p FROM Plc p WHERE p.id = :id"),
    @NamedQuery(name = "Plc.findByIpAddress", query = "SELECT p FROM Plc p WHERE p.ipAddress = :ipAddress"),
    @NamedQuery(name = "Plc.findByPortaGestioneServizi", query = "SELECT p FROM Plc p WHERE p.portaGestioneServizi = :portaGestioneServizi")})
public class Plc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "ip_address", nullable = false, length = 15)
    private String ipAddress;
    @Basic(optional = false)
    @NotNull
    @Column(name = "porta_gestione_servizi", nullable = false)
    private Long portaGestioneServizi;
    @Column(name = "uid",nullable = false)
    String uid;
    @OneToMany(mappedBy = "plc")
    private Collection<ExUserToNotifyFault> exUserToNotifyFaultCollection;
    @OneToMany(mappedBy = "plc")
    private Collection<Resource> resourceCollection;
    @JoinColumn(name = "ssc", referencedColumnName = "id")
    @ManyToOne
    private Ssc ssc;
    @OneToMany(mappedBy = "plc",cascade = {CascadeType.ALL })
    private Collection<Energymesure> energymesureCollection;

    public Plc() {
    }

    public Plc(Short id) {
        this.id = id;
    }

    public Plc(Short id, String ipAddress, Long portaGestioneServizi) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.portaGestioneServizi = portaGestioneServizi;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getPortaGestioneServizi() {
        return portaGestioneServizi;
    }

    public void setPortaGestioneServizi(Long portaGestioneServizi) {
        this.portaGestioneServizi = portaGestioneServizi;
    }

    @XmlTransient
    public Collection<ExUserToNotifyFault> getExUserToNotifyFaultCollection() {
        return exUserToNotifyFaultCollection;
    }

    public void setExUserToNotifyFaultCollection(Collection<ExUserToNotifyFault> exUserToNotifyFaultCollection) {
        this.exUserToNotifyFaultCollection = exUserToNotifyFaultCollection;
    }

    @XmlTransient
    public Collection<Resource> getResourceCollection() {
        return resourceCollection;
    }

    public void setResourceCollection(Collection<Resource> resourceCollection) {
        this.resourceCollection = resourceCollection;
    }

    public Ssc getSsc() {
        return ssc;
    }

    public void setSsc(Ssc ssc) {
        this.ssc = ssc;
    }

    @XmlTransient
    public Collection<Energymesure> getEnergymesureCollection() {
        return energymesureCollection;
    }

    public void setEnergymesureCollection(Collection<Energymesure> energymesureCollection) {
        this.energymesureCollection = energymesureCollection;
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
        if (!(object instanceof Plc)) {
            return false;
        }
        Plc other = (Plc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    
    
    @Override
    public String toString() {
        return "mude.srl.ssc.entity.utils.Plc[ id=" + id + " ]";
    }
    
}
