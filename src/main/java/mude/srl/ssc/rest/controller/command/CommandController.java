/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.command;

import java.util.logging.Level;
import java.util.logging.Logger;
import mude.srl.ssc.config.endpoint.ServiceEndpoint;
import mude.srl.ssc.entity.Plc;
import mude.srl.ssc.entity.Resource;
import mude.srl.ssc.entity.ResourceReservation;
import mude.srl.ssc.entity.utils.Response;
import mude.srl.ssc.service.log.LoggerSSC;
import mude.srl.ssc.rest.controller.command.handler.ActivationCommandHandler;
import mude.srl.ssc.rest.controller.command.model.MessageActivationCommand;
import mude.srl.ssc.rest.controller.command.model.RequestCommand;
import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;
import mude.srl.ssc.rest.controller.command.model.ResponseCommand;
import mude.srl.ssc.service.dati.PlcService;
import mude.srl.ssc.service.log.LoggerService;
import mude.srl.ssc.service.resource.ResourceService;
import mude.srl.ssc.service.scheduler.SchedulerManager;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Gestione automazione prenotazione risorse. La tipologia attuamente gestita in quest versione e' 
 * quella delle cabine.
 *  
 * @author Jack
 */
@Controller
public class CommandController {

	@Autowired
	private PlcService plcService;

	@Autowired
	private LoggerService loggerService;

	@Autowired
	private ResourceService resourceService;

//     @Autowired
//     private SimpMessagingTemplate simpMessagingTemplate;
	/**
	 * Questo metodo servizio non fa altro che inviare le informazione pervenute 
	 * alla classe servizio che si occupa di validare le informazione per avviare 
	 * l'attivazione della risorsa identificata attraverso le informazione contenute 
	 * nel payload di input.
	 * 
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@CrossOrigin(origins = { "http://localhost:8000", "http://127.0.0.1:8000"})
	@RequestMapping(value = ServiceEndpoint.RESOURCE_ATTIVA, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseCommand> gestionePrenotazioneRisorsa(
			@RequestBody RequestCommandResourceReservation request) throws Exception {

		ResponseCommand response = resourceService.gestionePrenotazioneRisorsa(request);
		return ResponseEntity.ok(response);

	}

	/**
	 * Gestione comandi attivazione/disattivazione cabina
	 * Questo servizio serve ad inviare comandi di accensione e spegnimento di una risora che sia valida nel contesto
	 * della locazione gestita. 
	 * In teoria con questo strumento si posso gestire tutte le risorse che solo collegate alla rete.
	 * Da usare con molta cautela. Solo che sa bene cosa sta facendo dovrebbe utilizzarle.
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@CrossOrigin(origins = { "http://localhost:8000", "http://127.0.0.1:8000"})
	@RequestMapping(value = ServiceEndpoint.RESOURCE_COMMAND, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseCommand> gestioneAttivazioneRisorse(@RequestBody RequestCommand request) {
		ResponseCommand response = new ResponseCommand();
		try {
			Plc plc = plcService.getPlcByUID(request.getPlc_uid());
			if (plc == null) {
				plc = plcService.getPlcById(Long.valueOf(request.getPlc_uid()));

				if (plc == null) {
					response.setErrorMessage("Plc not found");
					response.setStatus(403);
				}
			}

			if (plc != null) {
				Resource resource = plcService.getReourceByPlcAndTag(plc, request.getResource_tag());

				if (resource == null) {
					response.setErrorMessage("Resource not found");
					response.setStatus(HttpStatus.BAD_REQUEST.value());
				} else {
					/*
					 * Controllo se ci sono prenotazione attive per quella risorsa in modo da
					 * evitare azioni manuali e controllare puntualmente quale sia la situazione reale.
					 * 
					 */
					Response<Long> check = plcService.controllaPrenotazioniAttive(resource);
					//if (check.getResult().compareTo(0L) == 1) {
					//	response.setErrorMessage("Trovate prenotazione attive. Impossibile effettuare operazione richiesta");
					//	response.setStatus(HttpStatus.BAD_REQUEST.value());
					//} else {
						ActivationCommandHandler handler = new ActivationCommandHandler();
						handler.setPort(plc.getPortaGestioneServizi().toString());
						handler.setUrl(plc.getIpAddress());
						MessageActivationCommand command = new MessageActivationCommand();
						command.setAction(request.getAction());
						command.setDestination(resource.getBusId());
						command.setMessage("COM");
						handler.handle(command, response);
						
						if(response.isFault()) {
							loggerService.logException(Level.SEVERE,"Endpoint:"+ServiceEndpoint.RESOURCE_COMMAND, response.getEx());
						}
					//}
				}
			}

		} catch (Exception ex) {
			loggerService.logException(Level.SEVERE, null, ex);
		}
		return ResponseEntity.ok(response);
	}

}
