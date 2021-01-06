package mude.srl.ssc.service.payload.exception;

public final class MissingNameException extends OdooException
{
  static final String MESSAGE = "Name: cannot be blank.";

  MissingNameException()
  {
    super( MESSAGE );
  }
}
