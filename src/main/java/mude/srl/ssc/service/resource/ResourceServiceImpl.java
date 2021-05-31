package mude.srl.ssc.service.resource;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import mude.srl.ssc.entity.QrcodeTest;
import mude.srl.ssc.entity.Resource;
import mude.srl.ssc.entity.ResourceReservation;
import mude.srl.ssc.entity.utils.Response;
import mude.srl.ssc.messaging.Message;
import mude.srl.ssc.messaging.MessageInfoType;
import mude.srl.ssc.messaging.WebSocketConfig;
import mude.srl.ssc.rest.controller.command.handler.ActivationCommandHandler;
import mude.srl.ssc.rest.controller.command.model.MessageActivationCommand;
import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;
import mude.srl.ssc.rest.controller.command.model.ResponseCommand;
import mude.srl.ssc.service.dati.PlcService;
import mude.srl.ssc.service.log.LoggerService;
import mude.srl.ssc.service.scheduler.SchedulerManager;

@Component
public class ResourceServiceImpl implements ResourceService {

	private final ReentrantLock lock = new ReentrantLock();

	@Autowired
	private PlcService plcService;

	@Autowired
	private LoggerService loggerService;

	@Autowired
	private Scheduler scheduler;
	
	@Autowired
	private ResourcePolicyService resourceService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public void abilitaRisorsa(Resource r) throws Exception {

		if (r == null)
			throw new Exception("Info risorsa  non puo' essere null");
		ActivationCommandHandler h = new ActivationCommandHandler();

		if (r.getPlc() != null) {
			h.setUrl(r.getPlc().getIpAddress());
			h.setPort(r.getPlc().getPortaGestioneServizi().toString());
		}

		MessageActivationCommand cmd = new MessageActivationCommand();
		cmd.setAction(ENABLE_ACTION);
		cmd.setDestination(r.getBusId());

		ResponseCommand resp = new ResponseCommand();
		h.handle(cmd, resp);
		if (resp.getStatus() != 200) {
			throw new Exception("Comando abilitazione risorsa fallito",resp.getEx());
		}

	}

	@Override
	public void abilitaRisorsa(Long id) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void abilitaRisorsa(String tag) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void disabilitaRisorsa(Resource r) throws Exception {

		if (r == null)
			throw new Exception("Info risorsa  non puo' essere null");

		ActivationCommandHandler h = new ActivationCommandHandler();

		if (r.getPlc() != null) {
			h.setUrl(r.getPlc().getIpAddress());
			h.setPort(r.getPlc().getPortaGestioneServizi().toString());
		}

		MessageActivationCommand cmd = new MessageActivationCommand();
		cmd.setAction(DISABLE_ACTION);
		cmd.setDestination(r.getBusId());

		ResponseCommand resp = new ResponseCommand();
		h.handle(cmd, resp);
		if (resp.getStatus() != 200) {
			throw new Exception("Comando abilitazione risorsa fallito",resp.getEx());
		}

	}

	@Override
	public void disabilitaRisorsa(Long id) throws Exception {
		if (id == null)
			throw new Exception("Id risorsa non puo' essere null");

		ActivationCommandHandler h = new ActivationCommandHandler();

		Resource r = plcService.getReourceById(id);

		if (r.getPlc() != null) {
			h.setUrl(r.getPlc().getIpAddress());
			h.setPort(r.getPlc().getPortaGestioneServizi().toString());
			h.setPath(r.getPlc().getPath());
		} else {
			throw new Exception("Info plc non presenti. Impossibile gestire la risorsa");
		}

		MessageActivationCommand cmd = new MessageActivationCommand();
		cmd.setAction(DISABLE_ACTION);
		cmd.setDestination(r.getBusId());

		ResponseCommand resp = new ResponseCommand();
		h.handle(cmd, resp);
		if (resp.getStatus() != 200) {
			throw new Exception("Comando disabilita risorsa fallito",resp.getEx());
		}
	}

	@Override
	public void disabilitaRisorsa(String tag) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void disabilitaRisorsaByReservationId(String r) throws Exception {

		ActivationCommandHandler h = new ActivationCommandHandler();
		Long id_reservetion = Long.valueOf(r);
		
		Resource res = plcService.getResourceByReservetionId(id_reservetion);

		if (res.getPlc() != null) {
			
			h.setUrl(res.getPlc().getIpAddress());
			h.setPort(res.getPlc().getPortaGestioneServizi().toString());
			
		} else {
			throw new Exception("Info plc non presenti. Impossibile gestire la risorsa");
		}

		MessageActivationCommand cmd = new MessageActivationCommand();
		cmd.setAction(DISABLE_ACTION);
		cmd.setDestination(res.getBusId());

		ResponseCommand resp = new ResponseCommand();
		resp.setStatus(200);
		h.handle(cmd, resp);
		if (resp.getStatus() != 200) {

			if (resp.getEx() == null) {
				throw new Exception(resp.getErrorMessage());
			}

			else
				throw resp.getEx();
		}

	}

	/**
	 * 
	 * TODO Impostare messaggi utente in db per tipologia
	 * TODO Inserire gestore di eccezioni puntuale.Verificare tutte le eccezioni create ad hoc per ogni singolo evento.
	 * 
	 */
	@Override
	public ResponseCommand gestionePrenotazioneRisorsa(RequestCommandResourceReservation request) throws Exception{
		
		ResponseCommand response = new ResponseCommand();
		
		try {

			Resource resource = plcService.getReourceByPlcAndTag(request.getPlc_uid(), request.getResource_tag());
			if (resource == null) {
				response.setErrorMessage("Resource not found");
				response.setStatus(HttpStatus.BAD_REQUEST.value());

			} else {
				
				resourceService.checkPolicy(resource, request);
				
				Response<ResourceReservation> controllaPerAvvio = plcService.controllaPerAvvio(resource, request);
				
				if (!controllaPerAvvio.isFault()) {
				
					gestisciSchedulazionePrenotazione(controllaPerAvvio.getResult(),request);
					
				} else {

					
					
					simpMessagingTemplate.convertAndSend(WebSocketConfig.INFO_WEBSOCKET_ENDPOINT, 
							Message.buildFromRequest(MessageInfoType.ERROR,
							"Errore inaspettato", "Contattare il servizio clienti", request));
					
					loggerService.logException(Level.WARNING, "Nessuna prenotazione creata:" + request,
							new Exception("nessuna prenotazione creata"));
					
					response.setStatus(HttpStatus.BAD_REQUEST.value());
					response.setFault(true);
					response.setErrorMessage("Intervallo prenotazione gia' utilizzato per questa risorsa: "+resource.getTag());
					response.setEx(new Exception("Intervallo prenotazione gia' utilizzato per questa risorsa: "+resource.getTag()));

				}

			}

		} catch (Exception ex) {
			
				
			
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setFault(true);
			response.setErrorMessage(ex.getMessage());
			response.setEx(ex);
			
			loggerService.logException(Level.SEVERE,"Eccezione nella gestione della prenotrazione.", ex);
			throw ex;
			

		}
		return response;
	}

	private void gestisciSchedulazionePrenotazione(ResourceReservation r, RequestCommandResourceReservation request) throws Exception {
		
		SchedulerManager.getInstance().avviaGestionePrenotazione(r, scheduler);
		
		simpMessagingTemplate.convertAndSend(WebSocketConfig.AGGIORNAMENTO_WEBSOCKET_ENDPOINT,r);
		
		simpMessagingTemplate.convertAndSend(WebSocketConfig.INFO_WEBSOCKET_ENDPOINT, 
				Message.buildFromRequest(MessageInfoType.INFO,
						"Ottimo!", "La tua prenotazione e' stata schedulata.",
						request));
		
	}

	/**
	 * 
	 */
	// @Override
	public Response<QrcodeTest> getTestBy(String id) {
		Response<QrcodeTest> response = new Response<QrcodeTest>();
		try {
			response.setResult(plcService.getQrcodeTestById(id));
		} catch (Exception e) {
			response.setFault(true);
			response.setException(e);
			loggerService.logException(Level.SEVERE, null, e);
		}
		return response;
	}

}
