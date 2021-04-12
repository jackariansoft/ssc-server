package mude.srl.ssc.rest.controller.scheduler;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mude.srl.ssc.config.endpoint.ServiceEndpoint;
import mude.srl.ssc.entity.ResourceReservation;
import mude.srl.ssc.entity.beans.Prenotazione;
import mude.srl.ssc.entity.utils.Response;
import mude.srl.ssc.service.dati.PlcService;
import mude.srl.ssc.service.payload.model.Reservation;
import mude.srl.ssc.service.scheduler.SchedulerManager;

@Controller
public class ControllerScheduler {

	@Autowired
	Scheduler scheduler;

	@Autowired
	PlcService plcService;

	/**
	 * 
	 * @param prenotazione
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(origins = { "http://localhost:8000", "http://127.0.0.1:8000" })
	@RequestMapping(value = ServiceEndpoint.RESOURCE_JOB_SUSPEND, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Boolean>> sospendiJobCollegatoARisorsa(@RequestBody Prenotazione prenotazione)
			throws Exception {

		Response<Boolean> resp = new Response<Boolean>();
		resp.setResult(true);

		if (prenotazione.getId() == null) {
			prepareErrorForBadRequest(resp);
			resp.setResult(false);
		} else {
			Response<ResourceReservation> r = plcService.cercaPerIdPrenotazione(prenotazione.getId());
			if (r.getResult() == null) {
				resp.setResult(false);
				prepareErrorForBadRequest(r);
			} else {
				SchedulerManager.getInstance().sospendiJobPrenotazione(r.getResult(), scheduler);
			}

		}

		return ResponseEntity.ok(resp);
	}

	/**
	 * 
	 * @param prenotazione
	 * @return
	 */
	@CrossOrigin(origins = { "http://localhost:8000", "http://127.0.0.1:8000" })
	@RequestMapping(value = ServiceEndpoint.RESOURCE_JOB_TERM, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Boolean>> terminaJobCollegatoARisorsa(@RequestBody Prenotazione prenotazione) {

		Response<Boolean> resp = new Response<Boolean>();
		resp.setResult(true);
		resp.setFault(false);

		return ResponseEntity.ok(resp);
	}

	/**
	 * 
	 * @param prenotazione
	 * @return
	 */
	@CrossOrigin(origins = { "http://localhost:8000", "http://127.0.0.1:8000" })
	@RequestMapping(value = ServiceEndpoint.RESOURCE_JOB_RESUME, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Boolean>> resumeiJobCollegatoARisorsa(@RequestBody Prenotazione prenotazione) {

		Response<Boolean> resp = new Response<Boolean>();
		resp.setResult(true);
		resp.setFault(false);

		return ResponseEntity.ok(resp);
	}
	
	protected void prepareErrorForBadRequest(Response<?> r) {
		r.setFault(true);
		r.setStatus(HttpStatus.BAD_REQUEST.value());
	}
	protected void prepareErrorForInternalServerErrorRequest(Response<?> r) {
		r.setFault(true);
		r.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	

}
