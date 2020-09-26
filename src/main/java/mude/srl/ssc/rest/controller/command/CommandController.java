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
import mude.srl.ssc.service.log.LoggerSSC;
import mude.srl.ssc.rest.controller.command.handler.ActivationCommandHandler;
import mude.srl.ssc.rest.controller.command.model.MessageActivationCommand;
import mude.srl.ssc.rest.controller.command.model.RequestCommand;
import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;
import mude.srl.ssc.rest.controller.command.model.ResponseCommand;
import mude.srl.ssc.service.dati.PlcService;
import mude.srl.ssc.service.log.LoggerService;
import mude.srl.ssc.service.scheduler.SchedulerManager;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
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
    private Scheduler scheduler;
    /**
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = ServiceEndpoint.RESOURCE_ATTIVA, method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCommand> gestionePrenotazioneRisorsa(@RequestBody RequestCommandResourceReservation request){
        
        ResponseCommand response = new ResponseCommand();
        try {
            Plc plc = plcService.getPlcByUID(request.getPlc_uid());
            if (plc == null) {
                response.setErrorMessage("Plc not found");
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            } else {
                Resource resource = plcService.getReourceByPlcAndTag(plc, request.getResource_tag());
                if(resource==null){
                    response.setErrorMessage("Resource not found");
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                }else{
                    ResourceReservation controllaPerAvvio = plcService.controllaPerAvvio(resource, request);
                    if(controllaPerAvvio!=null){
                        SchedulerManager.getInstance().avviaGestionePrenotazione(controllaPerAvvio,scheduler);
                    }else{
                        loggerService.logException(Level.SEVERE, "Nessuna prenotazione creata",new Exception("nessuna prenotazione creata"));
                        
                    }
                    
                }
            }

        } catch (Exception ex) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
           loggerService.logException(Level.SEVERE, null, ex);
            
        }
        return ResponseEntity.ok(response);
                
    }
    /**
     * Gestione comendi attivazione/disattivazione cabina
     * @param request
     * @return 
     */
    @RequestMapping(value = ServiceEndpoint.RESOURCE_COMMAND, method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCommand> gestioneAttivazioneRisorse(@RequestBody RequestCommand request) {
        ResponseCommand response = new ResponseCommand();
        try {
            Plc plc = plcService.getPlcByUID(request.getPlc_uid());
            if (plc == null) {
                response.setErrorMessage("Plc not found");
                response.setStatus(403);
            } else {
                Resource resource = plcService.getReourceByPlcAndTag(plc, request.getResource_tag());
                if(resource==null){
                    response.setErrorMessage("Resource not found");
                    response.setStatus(403);
                }else{
                    ActivationCommandHandler handler = new ActivationCommandHandler();
                    handler.setPort(plc.getPortaGestioneServizi().toString());
                    handler.setUrl(plc.getIpAddress());
                    MessageActivationCommand command  = new MessageActivationCommand();
                    command.setAction(request.getAction());
                    command.setDestination(resource.getBusId());
                    command.setMessage("COM");
                    handler.handle(command, response);
                }
            }

        } catch (Exception ex) {
            loggerService.logException(Level.SEVERE, null, ex);
        }
        return ResponseEntity.ok(response);
    }
}
