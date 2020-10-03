package mude.srl.ssc.service.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mude.srl.ssc.entity.Resource;
import mude.srl.ssc.rest.controller.command.handler.ActivationCommandHandler;
import mude.srl.ssc.rest.controller.command.model.MessageActivationCommand;
import mude.srl.ssc.rest.controller.command.model.ResponseCommand;
import mude.srl.ssc.service.dati.PlcService;

@Component
public class ResourceServiceImpl implements ResourceService{

	@Autowired
	private PlcService plcService;

	@Override
	public void abilitaRisorsa(Resource r) throws Exception {

		if(r==null)
			throw new Exception("Info risorsa  non puo' essere null");
		ActivationCommandHandler h  = new ActivationCommandHandler();

		if(r.getPlc()!=null) {
			h.setUrl(r.getPlc().getIpAddress());
			h.setPort(r.getPlc().getPortaGestioneServizi().toString());			
		}

		MessageActivationCommand cmd  = new MessageActivationCommand();
		cmd.setAction(ENABLE_ACTION);
		cmd.setDestination(r.getBusId());

		ResponseCommand resp = new ResponseCommand();
		h.handle(cmd, resp);



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
		
		if(r==null)
			throw new Exception("Info risorsa  non puo' essere null");

		ActivationCommandHandler h  = new ActivationCommandHandler();

		if(r.getPlc()!=null) {
			h.setUrl(r.getPlc().getIpAddress());
			h.setPort(r.getPlc().getPortaGestioneServizi().toString());			
		}

		MessageActivationCommand cmd  = new MessageActivationCommand();
		cmd.setAction(DISABLE_ACTION);
		cmd.setDestination(r.getBusId());

		ResponseCommand resp = new ResponseCommand();
		h.handle(cmd, resp);

	}

	@Override
	public void disabilitaRisorsa(Long id) throws Exception {
		if(id==null)
			throw new Exception("Id risorsa non puo' essere null");
		
		ActivationCommandHandler h  = new ActivationCommandHandler();
		
		Resource r = plcService.getReourceById(id);
		
		

		if(r.getPlc()!=null) {
			h.setUrl(r.getPlc().getIpAddress());
			h.setPort(r.getPlc().getPortaGestioneServizi().toString());			
		}else {
			throw new Exception("Info plc non presenti. Impossibile gestire la risorsa");
		}

		MessageActivationCommand cmd  = new MessageActivationCommand();
		cmd.setAction(DISABLE_ACTION);
		cmd.setDestination(r.getBusId());

		ResponseCommand resp = new ResponseCommand();
		h.handle(cmd, resp);



	}

	@Override
	public void disabilitaRisorsa(String tag) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void disabilitaRisorsaByReservationId(String r) throws Exception {
		  
		   ActivationCommandHandler h  = new ActivationCommandHandler();
		   Long id_reservetion = Long.valueOf(r);
		   Resource res =  plcService.getResourceByReservetionId(id_reservetion);
		   
		   if(res.getPlc()!=null) {
				h.setUrl(res.getPlc().getIpAddress());
				h.setPort(res.getPlc().getPortaGestioneServizi().toString());			
			}else {
				throw new Exception("Info plc non presenti. Impossibile gestire la risorsa");
			}

			MessageActivationCommand cmd  = new MessageActivationCommand();
			cmd.setAction(DISABLE_ACTION);
			cmd.setDestination(res.getBusId());

			ResponseCommand resp = new ResponseCommand();
			resp.setStatus(200);
			h.handle(cmd, resp);
			if(resp.getStatus()!=200) {
				throw new Exception(resp.getErrorMessage());
			}
		
	}

}
