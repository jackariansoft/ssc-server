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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Jack
 */
@Entity
@Table(catalog = "ssc", schema = "public")
@XmlRootElement
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
    private String tag;
    @Size(max = 20)
    @Column(length = 20)
    private String reference;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
    private String type;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean manage;
    @Column(name = "bus_id")
    private Short busId;
    @JoinColumn(name = "plc", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private Plc plc;
    @JsonIgnore
    @JoinColumn(name = "ssc", referencedColumnName = "id")
    @ManyToOne
    private Ssc ssc;
    @JsonIgnore
    @OneToMany(mappedBy = "resource",cascade = {CascadeType.ALL })
    private Collection<ResourceReservation> reservation;
    
    public Resource() {
    }

    public Resource(Long id) {
        this.id = id;
    }

    public Resource(Long id, String tag, String type, boolean manage) {
        this.id = id;
        this.tag = tag;
        this.type = type;
        this.manage = manage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getManage() {
        return manage;
    }

    public void setManage(boolean manage) {
        this.manage = manage;
    }

    public Short getBusId() {
        return busId;
    }

    public void setBusId(Short busId) {
        this.busId = busId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resource)) {
            return false;
        }
        Resource other = (Resource) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mude.srl.ssc.entity.utils.Resource[ id=" + id + " ]";
    }
    
}
