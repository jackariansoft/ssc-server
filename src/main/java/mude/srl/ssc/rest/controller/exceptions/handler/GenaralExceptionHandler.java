package mude.srl.ssc.rest.controller.exceptions.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import mude.srl.ssc.messaging.Message;
import mude.srl.ssc.messaging.MessageInfoType;
import mude.srl.ssc.messaging.WebSocketConfig;
import mude.srl.ssc.service.payload.exception.CrossLocationReservetionRequestException;
import mude.srl.ssc.service.payload.exception.HourOutOfLimitException;
import mude.srl.ssc.service.payload.exception.OdooException;
import mude.srl.ssc.service.payload.exception.ReservationIntervalException;
import mude.srl.ssc.service.payload.model.Reservation;

@ControllerAdvice
public class GenaralExceptionHandler {
	
		
		@Autowired
		private SimpMessagingTemplate simpMessagingTemplate;
		
//		@Autowired
//		private LoggerService loggerService;
		
	    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 409
	    @ExceptionHandler(CrossLocationReservetionRequestException.class)
	    public void gestisciConflitto(CrossLocationReservetionRequestException exception) {
	    	
	    	String target = exception.getTarget();
	    	Reservation r = exception.getReservation();
	    	if(target!=null&&r!=null) {
	    		simpMessagingTemplate.convertAndSend(WebSocketConfig.INFO_WEBSOCKET_ENDPOINT,
	    				Message.buildFromRequest(MessageInfoType.ERROR, "Attenzione","Questa prenotazione non puo' essere evasa."
	    						+ "La prenotazione si riferisce ad una risorsa installata in localita' diversa.",target));
	    	    
	    	}
	    	
	               
	    }
	    
	    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 409
	    @ExceptionHandler(HourOutOfLimitException.class)
	    public void gestisciErroreLimitePrenotazione(HourOutOfLimitException exception) {
	    	
	    	String target = exception.getTarget();
	    	Reservation r = exception.getReservation();
	    	if(target!=null&&r!=null) {
	    		simpMessagingTemplate.convertAndSend(WebSocketConfig.INFO_WEBSOCKET_ENDPOINT,
	    				Message.buildFromRequest(MessageInfoType.ERROR, "Attenzione","Questa prenotazione non puo' essere evasa."
	    						+ "La risorsa non puo' essere attivata nell'intervallo :"+r.getDateStart()+" "+r.getDateEnd(),target));
	    	    
	    	}
	    	
	               
	    }
	    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 409
	    @ExceptionHandler(ReservationIntervalException.class)
	    public void gestisciErroreLimitePrenotazione(ReservationIntervalException exception) {
	    	
	    	String target = exception.getTarget();
	    	Reservation r = exception.getReservation();
	    	if(target!=null&&r!=null) {
	    		simpMessagingTemplate.convertAndSend(WebSocketConfig.INFO_WEBSOCKET_ENDPOINT,
	    				Message.buildFromRequest(MessageInfoType.ERROR, "Attenzione","Questa prenotazione non puo' essere evasa."
	    						+ "La risorsa non puo' essere attivata nell'intervallo :"+r.getDateStart()+" "+r.getDateEnd(),target));
	    	    
	    	}
	    	
	               
	    }
	    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 409
	    @ExceptionHandler(OdooException.class)
	    public void gestisciErroreApiKey(OdooException exception) {
	    	
	    	String target = exception.getTarget();
	    	
	    	if(target!=null) {
	    		simpMessagingTemplate.convertAndSend(WebSocketConfig.INFO_WEBSOCKET_ENDPOINT,
	    				Message.buildFromRequest(MessageInfoType.ERROR, "Attenzione",
	    						"Questa prenotazione non puo' essere evasa."
	    						+ "Servizio Prenotazione Sospeso. Contattare l'amministratore",target));
	    	    
	    	}
	    	
	               
	    }
}
