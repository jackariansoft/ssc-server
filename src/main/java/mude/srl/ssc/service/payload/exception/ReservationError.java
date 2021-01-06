package mude.srl.ssc.service.payload.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ReservationError
{
  private final Object errors;

  @JsonCreator
  public ReservationError( @JsonProperty( "errors" ) Object errors )
  {
    this.errors = errors;
  }

  public OdooException exception()
  {
    if( errors instanceof Collection )
    {
      return OdooException.fromErrors( (Collection) errors );
    }
    else if( errors instanceof Map )
    {
      List<String> errorsList = new ArrayList<>();
      ((Map) errors).forEach( ( key, value ) -> errorsList.add( key + ": " + value ) );
      return OdooException.fromErrors( errorsList );
    }
    else
    {
      return OdooException.fromErrors( Collections.singleton( errors.toString() ) );
    }
  }
}
