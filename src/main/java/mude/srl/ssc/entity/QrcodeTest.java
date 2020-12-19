package mude.srl.ssc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.Cache;

@Entity
@Cache(alwaysRefresh = true)
@Table(catalog = "ssc", schema = "public",name = "qrcoderequest")
@XmlRootElement
public class QrcodeTest  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8306874479321419218L;
	
	
	 @Id	    
     @Basic(optional = false)
	 @Column(nullable = false,name = "id")
	 private String id;
	 
	  
     @Basic(optional = false)
	 @Column(nullable = false,name = "plc_uid")
	 private String plcUid;
	 
	     
     @Basic(optional = false)
	 @Column(nullable = false,name = "resource_tag")
	 private String resourceTag;
     
     @Basic(optional = false)
     @NotNull
     @Column(name = "start_val", nullable = false)
     @Temporal(TemporalType.TIMESTAMP)
     private Date startTime;
     
     @Column(name = "end_val")
     @Temporal(TemporalType.TIMESTAMP)
     private Date endTime;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPlcUid() {
		return plcUid;
	}


	public void setPlcUid(String plcUid) {
		this.plcUid = plcUid;
	}


	public String getResourceTag() {
		return resourceTag;
	}


	public void setResourceTag(String resourceTag) {
		this.resourceTag = resourceTag;
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
	 
	 
	

}
