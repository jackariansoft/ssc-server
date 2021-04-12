package mude.srl.ssc.service.payload.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Reservation implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 6594213931524956016L;

//	{
//	    "dt_end": "2020-12-19T17:59:00",
//	    "dt_start": "2020-12-19T13:00:00",
//	    "location_id": 1,
//	    "resource_sku": "SKU00001"
//	}

	@NotNull
	@NotEmpty
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss",locale = "it_iT",timezone = "CEST")
	@JsonProperty("dt_end")
	private Date dateEnd;

	@NotNull
	@NotEmpty
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-d'T'HH:mm:ss",locale = "it_iT",timezone = "CEST")
	@JsonProperty("dt_start")
	private Date dateStart;
	

	@JsonProperty("location_id")
	private Long location;

	@JsonProperty("resource_sku")
	private String resourceSku;
	
	private String email;
	
	@JsonProperty("user_id")
	private Long userId;
	
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Long getLocation() {
		return location;
	}

	public void setLocation(Long location) {
		this.location = location;
	}

	public String getResourceSku() {
		return resourceSku;
	}

	public void setResourceSku(String resourceSku) {
		this.resourceSku = resourceSku;
	}

	@Override
	public String toString() {
		return "Reservation [dateEnd=" + dateEnd + ", dateStart=" + dateStart + ", location=" + location
				+ ", resourceSku=" + resourceSku + "]";
	}

	
}
