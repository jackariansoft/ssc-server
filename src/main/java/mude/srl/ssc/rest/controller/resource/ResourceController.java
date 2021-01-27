package mude.srl.ssc.rest.controller.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mude.srl.ssc.config.endpoint.ServiceEndpoint;
import mude.srl.ssc.entity.Plc;
import mude.srl.ssc.entity.Resource;
import mude.srl.ssc.entity.beans.Prenotazione;
import mude.srl.ssc.entity.beans.ResourceWithPlc;
import mude.srl.ssc.entity.utils.Response;
import mude.srl.ssc.service.dati.PlcService;

@RestController
@CrossOrigin(origins = { "http://localhost:8000", "http://127.0.0.1:8000"})
public class ResourceController {

	@Autowired
	private PlcService plcService;

	@RequestMapping(path = ServiceEndpoint.RESOURCE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResourceWithPlc>> resourceList() {
		List<ResourceWithPlc> _res = new ArrayList<>();
		try {
			List<Resource> res = plcService.getResource(null);

			if (res != null && !res.isEmpty()) {
				for (Resource r : res) {
					_res.add(new ResourceWithPlc(r));
				}

			}

		} catch (Exception e) {
			return new ResponseEntity<List<ResourceWithPlc>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(_res);
	}

	@ResponseBody
	@RequestMapping(path = ServiceEndpoint.RESOURCE_RESERVATIONS, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<Prenotazione>> resourceReservationsList() {
		Response<List<Prenotazione>> resp = new Response<List<Prenotazione>>();
		try {

			List<Prenotazione> res = plcService.getReservationBeans(null);
			resp.setResult(res);
			resp.setTotalResult(Long.valueOf(res.size()));

		} catch (Exception e) {
			resp.setFault(true);
			resp.setErrorMessage(e.getMessage());
			resp.setException(e);
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(path = ServiceEndpoint.LIST_PLC, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<Plc>> plcList() {
		Response<List<Plc>> resp = new Response<List<Plc>>();
		try {

			List<Plc> res = plcService.getPlcList(null);
			resp.setResult(res);
			resp.setTotalResult(Long.valueOf(res.size()));

		} catch (Exception e) {
			resp.setFault(true);
			resp.setErrorMessage(e.getMessage());
			resp.setException(e);
		}
		return resp;
	}

}
