/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.entity;

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
 * @author jackarian
 */
@Entity
@Table(name="",catalog = "mag", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t"),
    @NamedQuery(name = "Task.findById", query = "SELECT t FROM Task t WHERE t.id = :id"),
    @NamedQuery(name = "Task.findByDataInizio", query = "SELECT t FROM Task t WHERE t.dataInizio = :dataInizio"),
    @NamedQuery(name = "Task.findByDataFine", query = "SELECT t FROM Task t WHERE t.dataFine = :dataFine"),
    @NamedQuery(name = "Task.findByTempoPrevisto", query = "SELECT t FROM Task t WHERE t.tempoPrevisto = :tempoPrevisto"),
    @NamedQuery(name = "Task.findByUnitaTempoPrevisto", query = "SELECT t FROM Task t WHERE t.unitaTempoPrevisto = :unitaTempoPrevisto"),
    @NamedQuery(name = "Task.findByDescrizione", query = "SELECT t FROM Task t WHERE t.descrizione = :descrizione"),
    @NamedQuery(name = "Task.findByPriorita", query = "SELECT t FROM Task t WHERE t.priorita = :priorita"),
    @NamedQuery(name = "Task.findByOrdineEsecuzione", query = "SELECT t FROM Task t WHERE t.ordineEsecuzione = :ordineEsecuzione"),
    @NamedQuery(name = "Task.findByTaskBloccante", query = "SELECT t FROM Task t WHERE t.taskBloccante = :taskBloccante"),
    @NamedQuery(name = "Task.findByTecnico", query = "SELECT t FROM Task t WHERE t.tecnico = :tecnico"),
    @NamedQuery(name = "Task.findByStato", query = "SELECT t FROM Task t WHERE t.stato = :stato")})
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_inizio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInizio;
    @Column(name = "data_fine")
    @Temporal(TemporalType.DATE)
    private Date dataFine;
    @Column(name = "tempo_previsto")
    private Integer tempoPrevisto;
    @Size(max = 3)
    @Column(name = "unita_tempo_previsto", length = 3)
    private String unitaTempoPrevisto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1500)
    @Column(nullable = false, length = 1500)
    private String descrizione;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short priorita;
    @Size(max = 2147483647)
    @Column(name = "ordine_esecuzione", length = 2147483647)
    private String ordineEsecuzione;
    @Size(max = 2147483647)
    @Column(name = "task_bloccante", length = 2147483647)
    private String taskBloccante;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String tecnico;
    private Short stato;
    @JoinColumn(name = "installazione", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Installazione installazione;

    public Task() {
    }

    public Task(Long id) {
        this.id = id;
    }

    public Task(Long id, Date dataInizio, String descrizione, short priorita) {
        this.id = id;
        this.dataInizio = dataInizio;
        this.descrizione = descrizione;
        this.priorita = priorita;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public Integer getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(Integer tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }

    public String getUnitaTempoPrevisto() {
        return unitaTempoPrevisto;
    }

    public void setUnitaTempoPrevisto(String unitaTempoPrevisto) {
        this.unitaTempoPrevisto = unitaTempoPrevisto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public short getPriorita() {
        return priorita;
    }

    public void setPriorita(short priorita) {
        this.priorita = priorita;
    }

    public String getOrdineEsecuzione() {
        return ordineEsecuzione;
    }

    public void setOrdineEsecuzione(String ordineEsecuzione) {
        this.ordineEsecuzione = ordineEsecuzione;
    }

    public String getTaskBloccante() {
        return taskBloccante;
    }

    public void setTaskBloccante(String taskBloccante) {
        this.taskBloccante = taskBloccante;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public Short getStato() {
        return stato;
    }

    public void setStato(Short stato) {
        this.stato = stato;
    }

    public Installazione getInstallazione() {
        return installazione;
    }

    public void setInstallazione(Installazione installazione) {
        this.installazione = installazione;
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
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.ariannamondo.mag.entity.Task[ id=" + id + " ]";
    }
    
}
