package mude.srl.ssc.service.payload.exception;

import mude.srl.ssc.service.payload.model.Reservation;

public class HourOutOfLimitException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8528474900815750814L;

	public static final String MESSAGE = "Hour out of limit Exception";
	
	
	
	public HourOutOfLimitException() {
		super(MESSAGE);
	
	}

	public HourOutOfLimitException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public HourOutOfLimitException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public HourOutOfLimitException(String message) {
		super(message);
		
	}

	public HourOutOfLimitException(Throwable cause) {
		super(cause);
		
	}
	
	/**
	 * Informazione relativa alla prenotazione generata dal payload.
	 * Utile per informare il cliente che si trova ad utilizzare un 
	 * token che fa riferimento una risorsa che si trova su altra posizione.
	 *  
	 * @param r
	 */
	private Reservation reservation;
	
	public void setReservation(Reservation r) {
		this.reservation = r;	
	}

	public Reservation getReservation() {
		return reservation;
	}
	/**
	 * Il targer rappresenta su quale canale inviare eventuale messaggio di errore.
	 * Nell'attuale framawork questo rappresenta il canale indicato nel websocket per notificare i messaggi,
	 * sia quelli diretti all'utente, sia quelli diretti all back-office.
	 * 
	 */
	private String target;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	

}
