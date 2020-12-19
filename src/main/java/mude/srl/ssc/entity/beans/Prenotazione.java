package mude.srl.ssc.entity.beans;

import java.sql.Timestamp;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;





public class Prenotazione {


	private Long id;

	private String payload;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS",locale = "CEST")
	private Timestamp requestTime;
	private Long resource;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",locale = "CEST")
	private Timestamp starTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",locale = "CEST")
	private Timestamp endTime;
	private Short status;
	private Integer totalMinutes;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",locale = "CEST")
	private Timestamp receivedInterruptAt;
	private String interruptMotivation;
	private Integer schedulerId;
	private String reference;
	private String tag;
	private Long plcRef;
	private String idaddress;
	
	public Prenotazione() {
		super();
		
	}
	public Prenotazione(Long id, String payload, Timestamp requestTime, Long resource, Timestamp starTime, Timestamp endTime,
			Short status, Integer totalMinutes, Timestamp receivedInterruptAt, String interruptMotivation,
			Integer schedulerId, String reference, String tag, Long plcRef, String idaddress) {
		this();
		this.id = id;
		this.payload = payload;
		this.requestTime = requestTime;
		this.resource = resource;
		
		
		this.starTime = starTime;
		this.endTime = endTime;
		this.status = status;
		this.totalMinutes = totalMinutes;
		this.receivedInterruptAt = receivedInterruptAt;
		this.interruptMotivation = interruptMotivation;
		this.schedulerId = schedulerId;
		this.reference = reference;
		this.tag = tag;
		this.plcRef = plcRef;
		this.idaddress = idaddress;
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
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	public Long getResource() {
		return resource;
	}
	public void setResource(Long resource) {
		this.resource = resource;
	}
	public Timestamp getStarTime() {
		return starTime;
	}
	public void setStarTime(Timestamp starTime) {
		this.starTime = starTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Integer getTotalMinutes() {
		return totalMinutes;
	}
	public void setTotalMinutes(Integer totalMinutes) {
		this.totalMinutes = totalMinutes;
	}
	public Timestamp getReceivedInterruptAt() {
		return receivedInterruptAt;
	}
	public void setReceivedInterruptAt(Timestamp receivedInterruptAt) {
		this.receivedInterruptAt = receivedInterruptAt;
	}
	public String getInterruptMotivation() {
		return interruptMotivation;
	}
	public void setInterruptMotivation(String interruptMotivation) {
		this.interruptMotivation = interruptMotivation;
	}
	public Integer getSchedulerId() {
		return schedulerId;
	}
	public void setSchedulerId(Integer schedulerId) {
		this.schedulerId = schedulerId;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Long getPlcRef() {
		return plcRef;
	}
	public void setPlcRef(Long plcRef) {
		this.plcRef = plcRef;
	}
	public String getIdaddress() {
		return idaddress;
	}
	public void setIdaddress(String idaddress) {
		this.idaddress = idaddress;
	}
	
	
}
