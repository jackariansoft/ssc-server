package mude.srl.ssc.service.payload.exception;

public class ReservationIntervalException extends HourOutOfLimitException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 272952029392591293L;

	private static final String MESSAGE = "Interval exception";
	
	public ReservationIntervalException() {
		super(MESSAGE);
		
	}

	public ReservationIntervalException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public ReservationIntervalException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ReservationIntervalException(String message) {
		super(message);
		
	}

	public ReservationIntervalException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * 
	 */

}
