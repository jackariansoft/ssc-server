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
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.SerializationFeature;


import mude.srl.ssc.entity.QrcodeTest;
import mude.srl.ssc.messaging.Message;
import mude.srl.ssc.messaging.MessageInfoType;
import mude.srl.ssc.messaging.WebSocketConfig;
import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;
import mude.srl.ssc.service.payload.model.Reservation;
import mude.srl.ssc.service.resource.ResourceService;

@Component
public class RemoteServiceImpl implements RemoteService {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private ResourceService resourceService;

	int interval_in_minutes = 10;
	//private String domain = "https://camajora-staging.donodoo.it";

	private static final String plc_uid = "12321277";
	private static final String resource_tag1 = "CAB1";
	private static final String resource_tag2 = "CAB2";
	private static final String resource_tag3 = "CAB3";
	private static final String resource_tag4 = "CAB4";

	private TreeMap<String, RequestCommandResourceReservation> mokData;
	private ArrayList<String> payloadTest = new ArrayList<String>();
	private ArrayList<String> resource = new ArrayList<String>();
	public static final String domain  = "camajora-staging.donodoo.it";
	public static final String apiKey  = "2a3851a9a3955fb7525564e3e4306b368c32b8131b572361009cba884e945ad7";
	/**
	 * Validazione payload TO DO inserimento codice per richiesta validazione
	 * payload da server remoto
	 * 
	 */
	@Override
	public RequestCommandResourceReservation validatePayload(String payload) {

		// Response<QrcodeTest> resp = resourceService.getTestBy(payload);
		RestTemplate httpClient = new RestTemplateBuilder()
				.uriTemplateHandler(new RootUriTemplateHandler("https://" + domain))
				.defaultHeader("Authorization", "Token " + apiKey)
				.errorHandler(new RemoteServiceResponseErrorHandler()).build();

		httpClient.getMessageConverters().stream().filter(AbstractJackson2HttpMessageConverter.class::isInstance)
				.map(AbstractJackson2HttpMessageConverter.class::cast)
				.map(AbstractJackson2HttpMessageConverter::getObjectMapper)
				.forEach(mapper -> mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));
		
		//httpClient.postForObject("", request, responseType, uriVariables);
		Reservation rs = httpClient.postForObject("/api/v1/unlock",
				new mude.srl.ssc.service.payload.model.UnlockRequest(payload), Reservation.class);
		
		
		RequestCommandResourceReservation r = null;

		

		return r;
	}

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

	private boolean validateTimeInterval(RequestCommandResourceReservation r) {
		boolean check = true;
		Date adesso = Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("+01:00")));
		Message m = null;
		if (r.getStart().before(adesso)) {
			check = false;

			m = new Message(MessageInfoType.ERROR, "Payload Non valido",
					"Intervallo prenotazione non valido. Ora di avvio passata<br>", r.getPlc_uid());
			simpMessagingTemplate.convertAndSend(WebSocketConfig.INFO_WEBSOCKET_ENDPOINT, m);
		} else if (r.getEnd().before(r.getStart())) {
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
	public RemoteServiceImpl() {
		super();
		payloadTest.add("0001");
		payloadTest.add("0010");
		payloadTest.add("0011");
		payloadTest.add("0100");
		payloadTest.add("0101");
		payloadTest.add("0110");
		payloadTest.add("0111");
		payloadTest.add("1000");
		payloadTest.add("1001");
		payloadTest.add("1010");
		payloadTest.add("1011");
		payloadTest.add("1100");

		resource.add(resource_tag1);
		resource.add(resource_tag2);
		resource.add(resource_tag3);
		resource.add(resource_tag4);
		mokData = new TreeMap<String, RequestCommandResourceReservation>();

		for (String payload : payloadTest) {
			mokData.put(payload, createMokedReservation(payload, 0, 1));
		}
		NavigableMap<String, RequestCommandResourceReservation> dm = mokData.descendingMap();
		Set<Entry<String, RequestCommandResourceReservation>> es = dm.entrySet();
		Iterator<Entry<String, RequestCommandResourceReservation>> i = es.iterator();
		while (i.hasNext()) {
			System.out.println(i.next().getValue());

		}

	}

	/**
	 * 
	 * @param dateToConvert
	 * @return
	 */
	Date convertToDateViaInstant(LocalDateTime dateToConvert) {
		return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 
	 * @param payload
	 * @param index
	 * @param interval
	 * @return
	 */
	private RequestCommandResourceReservation createMokedReservation(String payload, int index, int interval) {
		RequestCommandResourceReservation r = new RequestCommandResourceReservation();
		r.setPayload(payload);
		r.setAction(1);
		r.setPlc_uid(plc_uid);
		r.setResource_tag(resource.get(ThreadLocalRandom.current().nextInt(0, 3)));
		/**
		 * Random interval
		 */
		Calendar c = Calendar.getInstance(Locale.ITALIAN);
		c.set(Calendar.SECOND, 0);
		c.add(Calendar.MINUTE, interval);

		Date star_time = new Date(c.getTimeInMillis());
		c.add(Calendar.MINUTE, interval * 3);

		Date endExclusive = c.getTime();
		c.add(Calendar.MINUTE, interval * 4);

		r.setStart(between(star_time, endExclusive));
		r.setEnd(between(endExclusive, c.getTime()));

		return r;
	}

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
