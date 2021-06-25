package mude.srl.ssc.service.payload.exception;

import java.util.Collection;

public class OdooException extends RuntimeException
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -5201472605099140593L;

public OdooException( String errorMessage )
  {
    super( errorMessage );
  }

  private OdooException( Collection errorMessages )
  {
    super( String.join( " && ", errorMessages.toString() ) );
  }

  static OdooException fromErrors( Collection errorMessages )
  {
    if( errorMessages.size() == 1 )
    {
      return fromError( errorMessages.iterator().next().toString() );
    }
    else
    {
      return new OdooException( errorMessages );
    }
  }

  private static OdooException fromError( String errorMessage )
  {
    switch( errorMessage )
    {
      case MissingNameException.MESSAGE:
        return new MissingNameException();
      case ApiHandleNotUniqueException.MESSAGE:
        return new ApiHandleNotUniqueException();
      default:
        return new OdooException( errorMessage );
    }
  }
  private String target;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}
