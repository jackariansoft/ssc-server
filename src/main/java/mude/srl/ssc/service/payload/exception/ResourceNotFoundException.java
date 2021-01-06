package mude.srl.ssc.service.payload.exception;

public class ResourceNotFoundException extends OdooException
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -4361168861073271760L;
static final String MESSAGE = "Requested resource not found";

  public ResourceNotFoundException()
  {
    super( MESSAGE );
  }
}
