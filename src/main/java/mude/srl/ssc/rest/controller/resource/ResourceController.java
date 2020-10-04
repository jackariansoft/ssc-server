package mude.srl.ssc.rest.controller.resource;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import org.eclipse.persistence.internal.descriptors.ObjectBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import mude.srl.ssc.config.endpoint.ServiceEndpoint;
import mude.srl.ssc.entity.Resource;
import mude.srl.ssc.entity.beans.Prenotazione;
import mude.srl.ssc.entity.utils.Response;
import mude.srl.ssc.service.dati.PlcService;

@RestController
public class ResourceController {

	@Autowired
    private PlcService plcService;
	
	@RequestMapping(path = ServiceEndpoint.RESOURCE,method =RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Resource>> resourceList(){
		try {
		List<Resource> res = plcService.getResource(null);
		return  ResponseEntity.ok(res);
		}catch (Exception e) {
			return new ResponseEntity<List<Resource>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ResponseBody
	@RequestMapping(path = ServiceEndpoint.RESOURCE_RESERVATIONS,method =RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<Prenotazione>> resourceReservationsList(){
		Response<List<Prenotazione>> resp = new Response<List<Prenotazione>>();
		try {
	
		
		List<Prenotazione> res = plcService.getReservationBeans(null);
		resp.setResult(res);
		resp.setTotalResult(Long.valueOf(res.size()));
		
		}catch (Exception e) {
			resp.setFault(true);
			resp.setErrorMessage(e.getMessage());
			resp.setException(e);
		}
		return resp;
	}
	
	
}
