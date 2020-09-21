/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jack
 */
@Entity
@Table(name = "resource_reservation", catalog = "ssc", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResourceReservation.findAll", query = "SELECT r FROM ResourceReservation r"),
    @NamedQuery(name = "ResourceReservation.findById", query = "SELECT r FROM ResourceReservation r WHERE r.id = :id"),
    @NamedQuery(name = "ResourceReservation.findByPayload", query = "SELECT r FROM ResourceReservation r WHERE r.payload = :payload"),
    @NamedQuery(name = "ResourceReservation.findByRequestTime", query = "SELECT r FROM ResourceReservation r WHERE r.requestTime = :requestTime"),
    @NamedQuery(name = "ResourceReservation.findByStartTime", query = "SELECT r FROM ResourceReservation r WHERE r.startTime = :startTime"),
    @NamedQuery(name = "ResourceReservation.findByEndTime", query = "SELECT r FROM ResourceReservation r WHERE r.endTime = :endTime"),
    @NamedQuery(name = "ResourceReservation.findByStatus", query = "SELECT r FROM ResourceReservation r WHERE r.status = :status"),
    @NamedQuery(name = "ResourceReservation.findByTotalMinutes", query = "SELECT r FROM ResourceReservation r WHERE r.totalMinutes = :totalMinutes"),
    @NamedQuery(name = "ResourceReservation.findByReceivedInterrupt", query = "SELECT r FROM ResourceReservation r WHERE r.receivedInterrupt = :receivedInterrupt"),
    @NamedQuery(name = "ResourceReservation.findByRecevedInterruptAt", query = "SELECT r FROM ResourceReservation r WHERE r.recevedInterruptAt = :recevedInterruptAt"),
    @NamedQuery(name = "ResourceReservation.findByInterruptMotivation", query = "SELECT r FROM ResourceReservation r WHERE r.interruptMotivation = :interruptMotivation")})
public class ResourceReservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Size(max = 50)
    @Column(name = "payload", length = 50)
    private String payload;
    @Basic(optional = false)
    @NotNull
    @Column(name = "request_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status", nullable = false)
    private short status;
    @Column(name = "total_minutes")
    private Integer totalMinutes;
    @Column(name = "received_interrupt")
    private Boolean receivedInterrupt;
    @Column(name = "receved_interrupt_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recevedInterruptAt;
    @Size(max = 100)
    @Column(name = "interrupt_motivation", length = 100)
    private String interruptMotivation;
    @JoinColumn(name = "resource", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Resource resource;

    public ResourceReservation() {
    }

    public ResourceReservation(Long id) {
        this.id = id;
    }

    public ResourceReservation(Long id, Date requestTime, Date startTime, short status) {
        this.id = id;
        this.requestTime = requestTime;
        this.startTime = startTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Integer getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public Boolean getReceivedInterrupt() {
        return receivedInterrupt;
    }

    public void setReceivedInterrupt(Boolean receivedInterrupt) {
        this.receivedInterrupt = receivedInterrupt;
    }

    public Date getRecevedInterruptAt() {
        return recevedInterruptAt;
    }

    public void setRecevedInterruptAt(Date recevedInterruptAt) {
        this.recevedInterruptAt = recevedInterruptAt;
    }

    public String getInterruptMotivation() {
        return interruptMotivation;
    }

    public void setInterruptMotivation(String interruptMotivation) {
        this.interruptMotivation = interruptMotivation;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
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
        if (!(object instanceof ResourceReservation)) {
            return false;
        }
        ResourceReservation other = (ResourceReservation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mude.srl.ssc.entity.ResourceReservation[ id=" + id + " ]";
    }
    
}
