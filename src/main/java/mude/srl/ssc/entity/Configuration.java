package mude.srl.ssc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.Cache;

@Entity
@Cache(alwaysRefresh = true)
@Table(catalog = "ssc", schema = "public",name = "configuration")
@XmlRootElement
public class Configuration  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2776809091937581358L;
	/**
	 * 
	 */
	
	
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Basic(optional = false)
	 @Column(nullable = false,name = "location_gestita")
	 private Long id;
	 
	 
	 @Basic(optional = false)
	 @Column(nullable = false,name = "url_servizio_prenotazione")
	 private String  urlServizioPrenotazione;
	 
	 @Basic(optional = false)
	 @Column(nullable = false,name = "url_apikey")
	 private String  urlApikey;
	 
	 @Basic(optional = false)
	 @Column(nullable = false,name = "endpoint")
	 private String  endpoint;
	 
	 
	 
	 @Basic(optional = false)
	 @Column(nullable = false,name = "valida")
	 private Boolean valida;



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getUrlServizioPrenotazione() {
		return urlServizioPrenotazione;
	}



	public void setUrlServizioPrenotazione(String urlServizioPrenotazione) {
		this.urlServizioPrenotazione = urlServizioPrenotazione;
	}



	public String getUrlApikey() {
		return urlApikey;
	}



	public void setUrlApikey(String urlApikey) {
		this.urlApikey = urlApikey;
	}



	public String getEndpoint() {
		return endpoint;
	}



	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}



	public Boolean getValida() {
		return valida;
	}



	public void setValida(Boolean valida) {
		this.valida = valida;
	}
	 
	 
	 
    
	 
	 
	

}
