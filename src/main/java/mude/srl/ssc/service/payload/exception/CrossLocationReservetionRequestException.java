package mude.srl.ssc.service.payload.exception;

import mude.srl.ssc.service.payload.model.Reservation;

public class CrossLocationReservetionRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8133851719334447130L;
	

	static final String MESSAGE = "Cross Location Reservetion not enabled";
	
	

	public CrossLocationReservetionRequestException() {
		super(MESSAGE);
	
	}

	public CrossLocationReservetionRequestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public CrossLocationReservetionRequestException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public CrossLocationReservetionRequestException(String message) {
		super(message);
		
	}

	public CrossLocationReservetionRequestException(Throwable cause) {
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
	 * Nell'attuale framawork questo rappresenta il canale indicato nel websocke per notificare i messaggi,
	 * sia quelli diretti all;utente, sia quelli diretti all back-office.
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
