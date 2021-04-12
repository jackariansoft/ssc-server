package mude.srl.ssc.service.payload;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.SerializationFeature;

import mude.srl.ssc.entity.Configuration;
import mude.srl.ssc.entity.Plc;
import mude.srl.ssc.entity.QrcodeTest;
import mude.srl.ssc.entity.Resource;
import mude.srl.ssc.messaging.Message;
import mude.srl.ssc.messaging.MessageInfoType;
import mude.srl.ssc.messaging.WebSocketConfig;
import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;
import mude.srl.ssc.service.configuration.ConfigurationService;
import mude.srl.ssc.service.dati.PlcService;
import mude.srl.ssc.service.log.LoggerService;
import mude.srl.ssc.service.payload.exception.CrossLocationReservetionRequestException;
import mude.srl.ssc.service.payload.model.Reservation;
import mude.srl.ssc.service.payload.model.ReservationResponse;
import mude.srl.ssc.service.payload.model.UnlockRequest;

@Component
@PropertySource(value = "file:/opt/config/location.properties", ignoreResourceNotFound = true)
public class RemoteServiceImpl implements RemoteService {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private PlcService plcService;

	@Autowired
	private LoggerService loggerService;

	@Value("${location.id}")
	private String location;

	int interval_in_minutes = 10;
	// private String domain = "https://camajora-staging.donodoo.it";

	//	private static final String plc_uid = "12321277";
	//	private static final String resource_tag1 = "CAB1";
	//	private static final String resource_tag2 = "CAB2";
	//	private static final String resource_tag3 = "CAB3";
	//	private static final String resource_tag4 = "CAB4";
	//
	//	private TreeMap<String, RequestCommandResourceReservation> mokData;
	//	private ArrayList<String> payloadTest = new ArrayList<String>();
	//	private ArrayList<String> resource = new ArrayList<String>();

	/**
	 * Validazione payload TO DO inserimento codice per richiesta validazione
	 * payload da server remoto
	 * 
	 * @throws Exception
	 * 
	 */
	@Override
	public RequestCommandResourceReservation validatePayload(String payload, String target) throws Exception {

		Configuration conf = configurationService.getCurrentValidConfig();
		// Response<QrcodeTest> resp = resourceService.getTestBy(payload);
		RequestCommandResourceReservation rcr = null;
		/**
		 * 
		 * Chimata al servizio remoto per validazione token
		 * 
		 */
		RestTemplate httpClient = new RestTemplateBuilder()
				.uriTemplateHandler(new RootUriTemplateHandler("https://" + conf.getUrlServizioPrenotazione()))
				.defaultHeader("Authorization", "Token " + conf.getUrlApikey())
				.errorHandler(new RemoteServiceResponseErrorHandler(loggerService)).build();
		/**
		 * 
		 * Configurazione particolare della gestione degli stream
		 * 
		 */
		httpClient.getMessageConverters().stream().filter(AbstractJackson2HttpMessageConverter.class::isInstance)
				.map(AbstractJackson2HttpMessageConverter.class::cast)
				.map(AbstractJackson2HttpMessageConverter::getObjectMapper)
				.forEach(mapper -> mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));

		// httpClient.postForObject("", request, responseType, uriVariables);
		ReservationResponse reservationResponse = httpClient.postForObject(conf.getEndpoint(),new UnlockRequest(payload), ReservationResponse.class);

		if (reservationResponse != null&& reservationResponse.getResults()!=null&&!reservationResponse.getResults().isEmpty()) {
			 Reservation reservation = reservationResponse.getResults().get(0);
			if (reservation.getLocation().compareTo(Long.valueOf(location)) != 0) {

				CrossLocationReservetionRequestException e = new CrossLocationReservetionRequestException(
						reservation.toString());
				e.setReservation(reservation);
				e.setTarget(target);

				throw e;

			}

			Resource resource = plcService.getReourceByTag(reservation.getResourceSku());

			if (resource != null) {
				
				Plc plc = resource.getPlc();
				rcr = createFrom(reservation, plc, resource, payload);

				if (!validateTimeInterval(rcr)) {
					rcr = null;
				}
			}else {
				/**
				 * TODO
				 * 
				 */
			}

		}

		return rcr;
	}

	/**
	 * Creazione richiesta di attivazione risorsa.
	 * 
	 * @param result
	 * @param plc
	 * @param r
	 * @param payload
	 * @return
	 */
	protected RequestCommandResourceReservation createFrom(Reservation result, Plc plc, Resource r, String payload) {
		RequestCommandResourceReservation command = new RequestCommandResourceReservation();
		command.setAction(1);
		command.setEnd(result.getDateEnd());
		command.setStart(result.getDateStart());
		command.setPlc_uid(plc.getUid());
		command.setResource_tag(result.getResourceSku());
		command.setPayload(payload);
		command.setReservation(result);
		return command;
	}

	/**
	 * Metodo utilizzato con dati generati automaticamente
	 * 
	 * @param result
	 * @return
	 */
	protected RequestCommandResourceReservation createFromTest(QrcodeTest result) {
		RequestCommandResourceReservation r = new RequestCommandResourceReservation();
		r.setAction(1);
		r.setEnd(result.getEndTime());
		r.setStart(result.getStartTime());
		r.setPlc_uid(result.getPlcUid());
		r.setResource_tag(result.getResourceTag());
		r.setPayload(result.getId());

		return r;
	}

	/**
	 * Validazione intervallo temporale della prenotazione. TO DO aggiungere
	 * gestione della data di inizio prenotazione in base ad una politica variabile.
	 * 
	 * @param r
	 * @return
	 */
	private boolean validateTimeInterval(RequestCommandResourceReservation r) {
		boolean check = true;
		Date adesso = Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("+01:00")));
		Message m = null;

		if (r.getEnd().before(adesso)) {
			check = false;
			m = new Message(MessageInfoType.ERROR, "Prenotazione scaduta.",
					"Intervallo prenotazione non valido.<br>" + "La data fine prenotazione e' passata. ",
					r.getPlc_uid());
			simpMessagingTemplate.convertAndSend(WebSocketConfig.INFO_WEBSOCKET_ENDPOINT, m);
		}
		if (r.getEnd().before(r.getStart())) {
			check = false;

			m = new Message(MessageInfoType.ERROR, "Payload Non valido", "Intervallo prenotazione non valido.<br>"
					+ "La data fine prenotazione non puo' essere minore della data di inizio ", r.getPlc_uid());
			simpMessagingTemplate.convertAndSend(WebSocketConfig.INFO_WEBSOCKET_ENDPOINT, m);
		}

		return check;
	}

	/**
	 * Setting of mock data
	 */
//	public RemoteServiceImpl() {
//		super();
//		payloadTest.add("0001");
//		payloadTest.add("0010");
//		payloadTest.add("0011");
//		payloadTest.add("0100");
//		payloadTest.add("0101");
//		payloadTest.add("0110");
//		payloadTest.add("0111");
//		payloadTest.add("1000");
//		payloadTest.add("1001");
//		payloadTest.add("1010");
//		payloadTest.add("1011");
//		payloadTest.add("1100");
//
//		resource.add(resource_tag1);
//		resource.add(resource_tag2);
//		resource.add(resource_tag3);
//		resource.add(resource_tag4);
//		mokData = new TreeMap<String, RequestCommandResourceReservation>();
//
//		for (String payload : payloadTest) {
//			mokData.put(payload, createMokedReservation(payload, 0, 1));
//		}
//		NavigableMap<String, RequestCommandResourceReservation> dm = mokData.descendingMap();
//		Set<Entry<String, RequestCommandResourceReservation>> es = dm.entrySet();
//		Iterator<Entry<String, RequestCommandResourceReservation>> i = es.iterator();
//		while (i.hasNext()) {
//			System.out.println(i.next().getValue());
//
//		}
//
//	}
//
//	/**
//	 * 
//	 * @param dateToConvert
//	 * @return
//	 */
	Date convertToDateViaInstant(LocalDateTime dateToConvert) {
		return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
	}
//
//	/**
//	 * 
//	 * @param payload
//	 * @param index
//	 * @param interval
//	 * @return
//	 */
//	private RequestCommandResourceReservation createMokedReservation(String payload, int index, int interval) {
//		RequestCommandResourceReservation r = new RequestCommandResourceReservation();
//		r.setPayload(payload);
//		r.setAction(1);
//		r.setPlc_uid(plc_uid);
//		r.setResource_tag(resource.get(ThreadLocalRandom.current().nextInt(0, 3)));
//		/**
//		 * Random interval
//		 */
//		Calendar c = Calendar.getInstance(Locale.ITALIAN);
//		c.set(Calendar.SECOND, 0);
//		c.add(Calendar.MINUTE, interval);
//
//		Date star_time = new Date(c.getTimeInMillis());
//		c.add(Calendar.MINUTE, interval * 3);
//
//		Date endExclusive = c.getTime();
//		c.add(Calendar.MINUTE, interval * 4);
//
//		r.setStart(between(star_time, endExclusive));
//		r.setEnd(between(endExclusive, c.getTime()));
//
//		return r;
//	}

	/**
	 * 
	 * @param startInclusive
	 * @param endExclusive
	 * @return
	 */
	public static Date between(Date startInclusive, Date endExclusive) {
		long startMillis = startInclusive.getTime();
		long endMillis = endExclusive.getTime();
		long randomMillisSinceEpoch = ThreadLocalRandom.current().nextLong(startMillis, endMillis);

		return new Date(randomMillisSinceEpoch);
	}

}
