package mude.srl.ssc.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(catalog = "ssc",name = "resource_policy", schema = "public")
@XmlRootElement
public class ResourcePolicy  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7134690790911399441L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
	
	@Basic(optional = false)
    @NotNull
     
    @Column(name = "policy_description",nullable = false, length = 150)
    @JsonProperty(value = "text")
    private String description;
	
	//weekend_available
	@Basic(optional = false)
    @NotNull
    @Column(name = "weekend_available",nullable = false,updatable = true)
	Boolean weekendAvailable;
	
	@Basic(optional = false)
    @NotNull
    @Column(name = "daily_hour_limited",nullable = false,updatable = true)
	Boolean hourLimited;
	

	@Basic(optional = false)
    @NotNull
     
    @Column(name = "day_of_week",nullable = false, length = 150)    
    private String dayOfWeek;
	
	
	@Basic(optional = false)
    @NotNull
     
    @Column(name = "hour_of_day",nullable = false, length = 150)   
    private String  hourOfDay;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "policy",cascade = {CascadeType.ALL },fetch = FetchType.LAZY)
    private Collection<Resource> reservation;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Boolean getWeekendAvailable() {
		return weekendAvailable;
	}


	public void setWeekendAvailable(Boolean weekendAvailable) {
		this.weekendAvailable = weekendAvailable;
	}


	public Boolean getHourLimited() {
		return hourLimited;
	}


	public void setHourLimited(Boolean hourLimited) {
		this.hourLimited = hourLimited;
	}


	public String getDayOfWeek() {
		return dayOfWeek;
	}


	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}


	public String getHourOfDay() {
		return hourOfDay;
	}


	public void setHourOfDay(String hourOfDay) {
		this.hourOfDay = hourOfDay;
	}

	@XmlTransient
	public Collection<Resource> getReservation() {
		return reservation;
	}


	public void setReservation(Collection<Resource> reservation) {
		this.reservation = reservation;
	}
	
	
	

}
