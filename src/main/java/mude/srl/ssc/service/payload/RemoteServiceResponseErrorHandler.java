package mude.srl.ssc.service.payload;

import com.fasterxml.jackson.databind.ObjectMapper;

import mude.srl.ssc.service.payload.exception.ReservationError;
import mude.srl.ssc.service.log.LoggerService;
import mude.srl.ssc.service.payload.exception.OdooException;
import mude.srl.ssc.service.payload.exception.ResourceNotFoundException;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public final class RemoteServiceResponseErrorHandler extends DefaultResponseErrorHandler {
	private ObjectMapper objectMapper = new ObjectMapper();
	private LoggerService loggerService = null;

	public RemoteServiceResponseErrorHandler(LoggerService loggerService) {
		super();
		this.loggerService = loggerService;

	}

	@Override
	protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
		switch (statusCode.series()) {
		case CLIENT_ERROR:
			if (statusCode == HttpStatus.NOT_FOUND) {
				ResourceNotFoundException ex = new ResourceNotFoundException();
				loggerService.logException(Level.SEVERE, "Response: " + readInputStream(response.getBody()), ex);

				throw ex;
				
			} else if (statusCode == HttpStatus.FORBIDDEN) {
				OdooException ex = new OdooException(readInputStream(response.getBody()));
				loggerService.logException(Level.SEVERE, "Response: " + readInputStream(response.getBody()), ex);
				throw ex;
			} else {
				OdooException ex  = objectMapper.readValue(response.getBody(), ReservationError.class).exception();
				loggerService.logException(Level.SEVERE, "Response: " + readInputStream(response.getBody()), ex);
				throw ex;
			}
		case SERVER_ERROR:
			throw new HttpServerErrorException(statusCode, response.getStatusText(), response.getHeaders(),
					getResponseBody(response), getCharset(response));
		default:
			throw new UnknownHttpStatusCodeException(statusCode.value(), response.getStatusText(),
					response.getHeaders(), getResponseBody(response), getCharset(response));
		}
	}

	private String readInputStream(final InputStream stream) {
		return new java.util.Scanner(stream).useDelimiter("\\A").next();
	}
}
