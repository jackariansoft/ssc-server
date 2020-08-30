/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jackarian
 */
@Entity
@Table(name = "networklocation")
@XmlRootElement
public class Location implements Serializable {

    @Size(max = 50)
    @Column(length = 50)
    private String reference;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ip_address", nullable = false, length = 50)
    private String ipAddress;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_update", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "ip_port_service")
    private Integer ipPortService;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short stato;
    @Size(max = 100)
    @Column(length = 100)
    private String title;
    private Boolean active;
    @Size(max = 100)
    @Column(length = 100)
    private String login;
    @Size(max = 100)
    @Column(length = 100)
    private String password;
    @Size(max = 30)
    @Column(name = "hotspot_tag", length = 30)
    private String hotspotTag;
    @JoinColumn(name = "installazione", referencedColumnName = "id")
    @ManyToOne
    private Installazione installazione;
    @JoinColumn(name = "lastupdate_by", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private User lastupdateBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "location")
    private Collection<SscServer> sscServerCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "location")
    private Collection<Installazione> installazioni;
 
    public Collection<Installazione> getInstallazioni() {
        return installazioni;
    }

    public void setInstallazioni(Collection<Installazione> installazioni) {
        this.installazioni = installazioni;
    }
    
    
   
    public Location() {
    }

    public Location(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.ariannamondo.mag.entity.Location[ id=" + id + " ]";
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getIpPortService() {
        return ipPortService;
    }

    public void setIpPortService(Integer ipPortService) {
        this.ipPortService = ipPortService;
    }

    public short getStato() {
        return stato;
    }

    public void setStato(short stato) {
        this.stato = stato;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHotspotTag() {
        return hotspotTag;
    }

    public void setHotspotTag(String hotspotTag) {
        this.hotspotTag = hotspotTag;
    }

    public Installazione getInstallazione() {
        return installazione;
    }

    public void setInstallazione(Installazione installazione) {
        this.installazione = installazione;
    }

    public User getLastupdateBy() {
        return lastupdateBy;
    }

    public void setLastupdateBy(User lastupdateBy) {
        this.lastupdateBy = lastupdateBy;
    }

    @XmlTransient
    public Collection<SscServer> getSscServerCollection() {
        return sscServerCollection;
    }

    public void setSscServerCollection(Collection<SscServer> sscServerCollection) {
        this.sscServerCollection = sscServerCollection;
    }
    
}
