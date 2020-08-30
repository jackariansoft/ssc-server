/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jackarian
 */
@Entity
@Table(name = "ssc_server", catalog = "mag", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"tag"})})
@XmlRootElement
public class SscServer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Column(name = "tag", length = 20)
    private String tag;
    @Size(max = 20)
    @Column(name = "ip_address_service", length = 20)
    private String ipAddressService;
    @Column(name = "ip_port_service")
    private Integer ipPortService;
    @OneToMany(mappedBy = "ssc")
    private Collection<Resource> resourceCollection;
    @JoinColumn(name = "location", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Location location;

    public SscServer() {
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

    
   


    public String getIpAddressService() {
        return ipAddressService;
    }

    public void setIpAddressService(String ipAddressService) {
        this.ipAddressService = ipAddressService;
    }

    public Integer getIpPortService() {
        return ipPortService;
    }

    public void setIpPortService(Integer ipPortService) {
        this.ipPortService = ipPortService;
    }

    @XmlTransient
    public Collection<Resource> getResourceCollection() {
        return resourceCollection;
    }

    public void setResourceCollection(Collection<Resource> resourceCollection) {
        this.resourceCollection = resourceCollection;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    

    @Override
    public String toString() {
        return "it.ariannamondo.mag.entity.SscServer[ sscServerPK=" + id + " ]";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.tag);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SscServer other = (SscServer) obj;
        if (!Objects.equals(this.tag, other.tag)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }
    
    
    
}
