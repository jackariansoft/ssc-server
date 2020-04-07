/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.entity;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "installazione")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Installazione.findAll", query = "SELECT i FROM Installazione i"),
    @NamedQuery(name = "Installazione.findByDescrizione", query = "SELECT i FROM Installazione i WHERE i.descrizione = :descrizione"),
    @NamedQuery(name = "Installazione.findByDescrizioneEstesa", query = "SELECT i FROM Installazione i WHERE i.descrizioneEstesa = :descrizioneEstesa"),
    @NamedQuery(name = "Installazione.findByDataInizioLavori", query = "SELECT i FROM Installazione i WHERE i.dataInizioLavori = :dataInizioLavori"),
    @NamedQuery(name = "Installazione.findByDataFineLavori", query = "SELECT i FROM Installazione i WHERE i.dataFineLavori = :dataFineLavori"),
    @NamedQuery(name = "Installazione.findByScaduta", query = "SELECT i FROM Installazione i WHERE i.scaduta = :scaduta"),
    @NamedQuery(name = "Installazione.findByCreationDate", query = "SELECT i FROM Installazione i WHERE i.creationDate = :creationDate"),
    @NamedQuery(name = "Installazione.findByLastupdate", query = "SELECT i FROM Installazione i WHERE i.lastupdate = :lastupdate"),
    @NamedQuery(name = "Installazione.findByNote", query = "SELECT i FROM Installazione i WHERE i.note = :note"),
    @NamedQuery(name = "Installazione.findById", query = "SELECT i FROM Installazione i WHERE i.id = :id"),
    @NamedQuery(name = "Installazione.findByIndirizzoIp", query = "SELECT i FROM Installazione i WHERE i.indirizzoIp = :indirizzoIp"),
    @NamedQuery(name = "Installazione.findByNazione", query = "SELECT i FROM Installazione i WHERE i.nazione = :nazione"),
    @NamedQuery(name = "Installazione.findByRegione", query = "SELECT i FROM Installazione i WHERE i.regione = :regione"),
    @NamedQuery(name = "Installazione.findByProv", query = "SELECT i FROM Installazione i WHERE i.prov = :prov"),
    @NamedQuery(name = "Installazione.findByIndirizzo", query = "SELECT i FROM Installazione i WHERE i.indirizzo = :indirizzo"),
    @NamedQuery(name = "Installazione.findByCap", query = "SELECT i FROM Installazione i WHERE i.cap = :cap"),
    @NamedQuery(name = "Installazione.findByLatitudine", query = "SELECT i FROM Installazione i WHERE i.latitudine = :latitudine"),
    @NamedQuery(name = "Installazione.findByLongitude", query = "SELECT i FROM Installazione i WHERE i.longitude = :longitude")})
public class Installazione implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descrizione")
    private String descrizione;
    @Size(max = 250)
    @Column(name = "descrizione_estesa")
    private String descrizioneEstesa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_inizio_lavori")
    @Temporal(TemporalType.DATE)
    private Date dataInizioLavori;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "data_fine_lavori")
    private String dataFineLavori;
    @Column(name = "scaduta")
    private Boolean scaduta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lastupdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdate;
    @Size(max = 500)
    @Column(name = "note")
    private String note;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 32)
    @Column(name = "indirizzo_ip")
    private String indirizzoIp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nazione")
    private String nazione;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "regione")
    private String regione;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "prov")
    private String prov;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "indirizzo")
    private String indirizzo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "cap")
    private String cap;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitudine")
    private BigDecimal latitudine;
    @Column(name = "longitude")
    private BigDecimal longitude;
    @JoinColumn(name = "lastupdate_by", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User lastupdateBy;
    @JoinColumn(name = "tecnico", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User tecnico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "installazione")
    private Collection<Location> locationCollection;

    public Installazione() {
    }

    public Installazione(Long id) {
        this.id = id;
    }

    public Installazione(Long id, String descrizione, Date dataInizioLavori, String dataFineLavori, Date creationDate, Date lastupdate, String nazione, String regione, String prov, String indirizzo, String cap) {
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizioneEstesa() {
        return descrizioneEstesa;
    }

    public void setDescrizioneEstesa(String descrizioneEstesa) {
        this.descrizioneEstesa = descrizioneEstesa;
    }

    public Date getDataInizioLavori() {
        return dataInizioLavori;
    }

    public void setDataInizioLavori(Date dataInizioLavori) {
        this.dataInizioLavori = dataInizioLavori;
    }

    public String getDataFineLavori() {
        return dataFineLavori;
    }

    public void setDataFineLavori(String dataFineLavori) {
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

    @XmlTransient
    public Collection<Location> getLocationCollection() {
        return locationCollection;
    }

    public void setLocationCollection(Collection<Location> locationCollection) {
        this.locationCollection = locationCollection;
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
    
}
