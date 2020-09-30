package mude.srl.ssc.rest.controller.command.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ReservationaDatDeserilizer extends StdDeserializer<Date> {

	private SimpleDateFormat  df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	protected ReservationaDatDeserilizer(Class<?> vc) {
		super(vc);

	}
	public ReservationaDatDeserilizer() {
		this(null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3209973275113006732L;
    
	

	

	/**
	 * 
	 */
	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		String s  = p.getText();
		Date d = null;
		try {	
			d = df.parse(s);

		} catch (ParseException e) {
			
		}
		return d;
	}


}
