package mude.srl.ssc.service.payload;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Component;

import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;

@Component
public class RemoteServiceImpl implements RemoteService {

//	{
		int interval_in_minutes = 15;
		
		String plc_uid ="12321277";
		String resource_tag="CAB1";
//		"start":"2020-10-02 22:00:00",
//		"end":"2020-10-02 22:10:00",
//		"payload":"adm1",
//		"action":1
//	}
	@Override
	public RequestCommandResourceReservation validatePayload(String payload) {
		RequestCommandResourceReservation r = new RequestCommandResourceReservation();
		r.setPayload(payload);
		r.setAction(1);
		r.setPlc_uid(plc_uid);
		r.setResource_tag(resource_tag);
		
		return r;
	}

	Date convertToDateViaInstant(LocalDateTime dateToConvert) {
	    return java.util.Date
	      .from(dateToConvert.atZone(ZoneId.systemDefault())
	      .toInstant());
	}
	
}
