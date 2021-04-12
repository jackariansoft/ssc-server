/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.command.model;


import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import mude.srl.ssc.rest.controller.command.utils.ReservationaDatDeserilizer;
import mude.srl.ssc.service.payload.model.Reservation;



/**
 *
 * @author Jack
 */
public class RequestCommandResourceReservation  extends RequestCommand{
    
	@NotNull
	@NotEmpty
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",locale = "it_iT",timezone = "CEST")
	@JsonDeserialize(using = ReservationaDatDeserilizer.class)
    private Date start;
	
	@NotNull
	@NotEmpty
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",locale = "it_IT",timezone = "CEST")
	@JsonDeserialize(using = ReservationaDatDeserilizer.class)
    private Date end;
	
	@NotNull
	@NotEmpty	
    private String payload;
	
	@JsonIgnore
	private Reservation reservation;
	
	

    public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
    
    
    

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

	@Override
	public String toString() {
		return "RequestCommandResourceReservation [start=" + start + ", end=" + end + ", payload=" + payload + "]";
	}
    
    
    
}
