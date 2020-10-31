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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import mude.srl.ssc.messaging.Message;
import mude.srl.ssc.messaging.MessageInfoType;
import mude.srl.ssc.messaging.WebSocketConfig;
import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;

@Component
public class RemoteServiceImpl implements RemoteService {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	int interval_in_minutes = 10;

	private static final String plc_uid = "12321277";
	private static final String resource_tag1 = "CAB1";
	private static final String resource_tag2 = "CAB2";
	private static final String resource_tag3 = "CAB3";
	private static final String resource_tag4 = "CAB4";

	private TreeMap<String, RequestCommandResourceReservation> mokData;
	private ArrayList<String> payloadTest = new ArrayList<String>();
	private ArrayList<String> resource = new ArrayList<String>();

	/**
	 * Validazione payload TO DO inserimento codice per richiesta validazione
	 * payload da server remoto
	 * 
	 */
	@Override
	public RequestCommandResourceReservation validatePayload(String payload) {
		RequestCommandResourceReservation r = mokData.get(payload);
		if (r != null) {
			if (!validateTimeInterval(r)) {
				r = null;
			}
		}else {
			
		}
		return r;
	}

	private boolean validateTimeInterval(RequestCommandResourceReservation r) {
		boolean check = true;
		Date adesso = Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("+01:00")));
		Message m = null;
		if (r.getStart().before(adesso)) {
			check = false;
			
			m = new Message(MessageInfoType.ERROR, "Payload Non valido", "Intervallo prenotazione non valido",
					r.getPlc_uid());
			simpMessagingTemplate.convertAndSend(WebSocketConfig.INFO_WEBSOCKET_ENDPOINT, m);
		}else if(r.getEnd().before(r.getStart())) {
			check = false;
			
			m = new Message(MessageInfoType.ERROR, "Payload Non valido", "Intervallo prenotazione non valido",
					r.getPlc_uid());
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
