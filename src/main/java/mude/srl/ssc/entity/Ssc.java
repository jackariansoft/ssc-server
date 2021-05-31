/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Jack
 */
@Entity
@Table(catalog = "ssc", schema = "public", uniqueConstraints = { @UniqueConstraint(columnNames = { "mapped_by" }) })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Ssc.findAll", query = "SELECT s FROM Ssc s"),
		@NamedQuery(name = "Ssc.findById", query = "SELECT s FROM Ssc s WHERE s.id = :id"),
		@NamedQuery(name = "Ssc.findByMappedBy", query = "SELECT s FROM Ssc s WHERE s.mappedBy = :mappedBy"),
		@NamedQuery(name = "Ssc.findByLocation", query = "SELECT s FROM Ssc s WHERE s.location = :location"),
		@NamedQuery(name = "Ssc.findByName", query = "SELECT s FROM Ssc s WHERE s.name = :name") })
public class Ssc implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Short id;
	@Basic(optional = false)
	@NotNull
	 
	@Column(name = "mapped_by", nullable = false, length = 20)
	private String mappedBy;

	@Basic(optional = false)
	@NotNull
	 
	@Column(nullable = false, length = 2147483647)
	private String location;

	@Basic(optional = false)
	@NotNull
	 
	@Column(nullable = false, length = 50)
	private String name;

//  @JoinColumn(name = "email_config", referencedColumnName = "ref", nullable = false)
	@Column(name = "email_config", nullable = false)
	Short emailConfig;

//   @ManyToOne(optional = false)
	// private Email emailConfig;
	@OneToMany(mappedBy = "ssc")
	@JsonIgnore
	private Collection<Resource> resourceCollection;

	@OneToMany(mappedBy = "ssc")
	@JsonIgnore
	private Collection<Plc> plcCollection;

	@OneToMany(mappedBy = "ssc")
	@JsonIgnore
	private Collection<Energymesure> energymesureCollection;

	@JoinColumn(name = "location_id", referencedColumnName = "id")
	@ManyToOne
	private Location position;

	public Ssc() {
	}

	public Ssc(Short id) {
		this.id = id;
	}

	public Ssc(Short id, String mappedBy, String location, String name) {
		this.id = id;
		this.mappedBy = mappedBy;
		this.location = location;
		this.name = name;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getMappedBy() {
		return mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getEmailConfig() {
		return emailConfig;
	}

	public void setEmailConfig(Short emailConfig) {
		this.emailConfig = emailConfig;
	}

	public Location getPosition() {
		return position;
	}

	public void setPosition(Location position) {
		this.position = position;
	}

	// public Email getEmailConfig() {
//        return emailConfig;
//    }
//
//    public void setEmailConfig(Email emailConfig) {
//        this.emailConfig = emailConfig;
//    }
	@XmlTransient
	public Collection<Resource> getResourceCollection() {
		return resourceCollection;
	}

	public void setResourceCollection(Collection<Resource> resourceCollection) {
		this.resourceCollection = resourceCollection;
	}

	@XmlTransient
	public Collection<Plc> getPlcCollection() {
		return plcCollection;
	}

	public void setPlcCollection(Collection<Plc> plcCollection) {
		this.plcCollection = plcCollection;
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
		if (!(object instanceof Ssc)) {
			return false;
		}
		Ssc other = (Ssc) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "mude.srl.ssc.entity.utils.Ssc[ id=" + id + " ]";
	}

}
