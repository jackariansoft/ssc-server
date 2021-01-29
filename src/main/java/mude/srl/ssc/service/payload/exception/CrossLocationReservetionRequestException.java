package mude.srl.ssc.service.payload.exception;

public class CrossLocationReservetionRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8133851719334447130L;

	static final String MESSAGE = "Cross Location Reservetion not enabled";

	public CrossLocationReservetionRequestException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CrossLocationReservetionRequestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public CrossLocationReservetionRequestException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CrossLocationReservetionRequestException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CrossLocationReservetionRequestException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
	
}
