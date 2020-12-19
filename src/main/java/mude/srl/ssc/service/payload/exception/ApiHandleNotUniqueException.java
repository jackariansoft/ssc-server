package mude.srl.ssc.service.payload.exception;

public final class ApiHandleNotUniqueException extends OdooException
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -2489985407504215791L;
static final String MESSAGE = "API Handle: must be unique - that value has been taken.";

  ApiHandleNotUniqueException()
  {
    super( MESSAGE );
  }
}
