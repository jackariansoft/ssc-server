/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jackarian
 */
@Entity
@Table(name = "installazione", catalog = "mag", schema = "public")
@XmlRootElement
public class Installazione implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String descrizione;
    @Basic(optional = false)
    @NotNull   
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "it_IT")
    @Column(name = "data_inizio_lavori", nullable = false)
    @Temporal(TemporalType.DATE)    
    private Date dataInizioLavori;
    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "it_IT")
    @Column(name = "data_fine_lavori", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataFineLavori;
    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "it_IT")
    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "it_IT")
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdate;
    @Size(max = 500)
    @Column(length = 500)
    private String note;
    @Size(max = 32)
    @Column(name = "indirizzo_ip", length = 32)
    private String indirizzoIp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String nazione;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String regione;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String prov;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 150)
    @Column(nullable = false, length = 150)
    private String indirizzo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
    private String cap;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short stato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "installazione")
    private Collection<Task> taskCollection;
    private static final long serialVersionUID = 1L;
    @Column(name = "descrizione_estesa")
    private String descEstesa;
    @Column(name = "scaduta")
    private Boolean scaduta;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitudine")
    private BigDecimal latitudine;
    @Column(name = "longitude")
    private BigDecimal longitude;
    @JoinColumn(name = "lastupdate_by", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    @JsonIgnore
    private User lastupdateBy;
    @JoinColumn(name = "tecnico", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    @JsonIgnore
    private User tecnico;
    @JsonIgnore
    @JoinColumn(name = "location", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Transient
    private String text;

    public Installazione() {
    }

    public Installazione(Long id) {
        this.id = id;
    }

    public Installazione(Long id, String descrizione, Date dataInizioLavori, Date dataFineLavori, Date creationDate, Date lastupdate, String nazione, String regione, String prov, String indirizzo, String cap) {
        this.id = id;
        this.descrizione = descrizione;
        this.dataInizioLavori = dataInizioLavori;
        this.dataFineLavori = dataFineLavori;
        this.creationDate = creationDate;
        this.lastupdate = lastupdate;
        this.nazione = nazione;
        this.regione = regione;
        this.prov = prov;
        this.indirizzo = indirizzo;
        this.cap = cap;
    }

    public String getDescEstesa() {
        return descEstesa;
    }

    public void setDescEstesa(String descEstesa) {
        this.descEstesa = descEstesa;
    }

    public Date getDataInizioLavori() {
        return dataInizioLavori;
    }

    public void setDataInizioLavori(Date dataInizioLavori) {
        this.dataInizioLavori = dataInizioLavori;
    }

    public Date getDataFineLavori() {
        return dataFineLavori;
    }

    public void setDataFineLavori(Date dataFineLavori) {
        this.dataFineLavori = dataFineLavori;
    }

    public Boolean getScaduta() {
        return scaduta;
    }

    public void setScaduta(Boolean scaduta) {
        this.scaduta = scaduta;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndirizzoIp() {
        return indirizzoIp;
    }

    public void setIndirizzoIp(String indirizzoIp) {
        this.indirizzoIp = indirizzoIp;
    }

    public BigDecimal getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(BigDecimal latitudine) {
        this.latitudine = latitudine;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public User getLastupdateBy() {
        return lastupdateBy;
    }

    public void setLastupdateBy(User lastupdateBy) {
        this.lastupdateBy = lastupdateBy;
    }

    public User getTecnico() {
        return tecnico;
    }

    public void setTecnico(User tecnico) {
        this.tecnico = tecnico;
    }

//    @XmlTransient
//    public Collection<Location> getLocationCollection() {
//        return locationCollection;
//    }
//
//    public void setLocationCollection(Collection<Location> locationCollection) {
//        this.locationCollection = locationCollection;
//    }
    public String getText() {
        if (text == null) {
            text = this.descrizione;
        }
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        if (!(object instanceof Installazione)) {
            return false;
        }
        Installazione other = (Installazione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.ariannamondo.mag.entity.Installazione[ id=" + id + " ]";
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public short getStato() {
        return stato;
    }

    public void setStato(short stato) {
        this.stato = stato;
    }

    @XmlTransient
    public Collection<Task> getTaskCollection() {
        return taskCollection;
    }

    public void setTaskCollection(Collection<Task> taskCollection) {
        this.taskCollection = taskCollection;
    }

}
