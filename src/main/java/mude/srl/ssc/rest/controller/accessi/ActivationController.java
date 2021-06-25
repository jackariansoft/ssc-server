/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.accessi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mude.srl.ssc.config.utils.protocol.StringUtils;
import mude.srl.ssc.rest.controller.accessi.model.MIDMessage;
import mude.srl.ssc.rest.controller.accessi.model.RequestTokenMessage;
import mude.srl.ssc.rest.controller.command.model.MessageActivationCommand;
import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;
import mude.srl.ssc.rest.controller.command.model.ResponseCommand;
import mude.srl.ssc.service.dati.EnergyService;
import mude.srl.ssc.service.log.LoggerService;
import mude.srl.ssc.service.payload.RemoteService;
import mude.srl.ssc.service.payload.exception.OdooException;
import mude.srl.ssc.service.resource.ResourceService;

/**
 *
 * @author Jack
 */
@Controller
public class ActivationController {

    private final boolean DEBUG = false;
    private final StringUtils su = StringUtils.getInstance();
    
    
    @Autowired
    EnergyService energyService;
    
    @Autowired
    LoggerService loggerService;
    
    @Autowired
    private RemoteService remoteService;
    
    @Autowired
    private ResourceService resourceService;
    
    
    
    @RequestMapping(path = "/activation", produces = {MediaType.ALL_VALUE}, consumes = MediaType.ALL_VALUE)
    public void actiovationNode(HttpServletRequest request, HttpServletResponse resp) throws Exception {
        //To do register message
        String  test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        test = su.clearString(test);
        if (DEBUG) {
            Logger.getLogger(ActivationController.class.getName()).log(Level.INFO, test);
        }

        if (test.contains("UID") && !test.contains("FIFO")) {
            builHeartBitResponse(test, request, resp);

        } else {

            buildResponse(test, request, resp);

        }
    }

    /**
     *
     * @param test
     * @param request
     * @param resp
     * @throws JsonProcessingException
     * @throws IOException
     */
    public void builHeartBitResponse(String test, HttpServletRequest request, HttpServletResponse resp) throws JsonProcessingException, IOException {

        ObjectMapper mapper = new ObjectMapper();

        MIDMessage mi = mapper.readValue(test, MIDMessage.class);

        mapper.writeValue(resp.getOutputStream(), mi);

    }

    /**
     *
     * @param test
     * @param request
     * @param resp
     * @throws Exception 
     */
    public void buildResponse(String test, HttpServletRequest request, HttpServletResponse resp) throws Exception {

        //ActivationCommmandHandler h = new ActivationCommmandHandler();

        ObjectMapper mapper = new ObjectMapper();

        RequestTokenMessage readValue = mapper.readValue(test, RequestTokenMessage.class);
        Long mid = readValue.getMID();
        /**
         * 
         * Questo valore serve in tutti i casi si voglia inviare un messaggio a video.
         * Esso rapprenta il riferimento alla provenienza della richiesta,
         * indentificativo del plc che riceve la richiesta.
         *  
         * 
         */
        Long uid = readValue.getUID();
        
        MessageActivationCommand res = new MessageActivationCommand();
        res.setMID(mid);        
        res.setAction(3);
        res.setMessage("NO_VALID");
        res.setDestination(10);
        if (DEBUG) {
            Logger.getLogger(ActivationController.class.getName()).log(Level.INFO, res.toString());
        }
        mapper.writeValue(resp.getOutputStream(), res);
        /**
         * Estrazione del payload
         * 
         */
        String payload = readValue.getFifo().get(0).getValue().getToken();
        
        try {
        	
			RequestCommandResourceReservation r = remoteService.validatePayload(payload.replaceAll("(\\r|\\n)", ""),uid.toString());
			
			if(r!=null) {
				resourceService.gestionePrenotazioneRisorsa(r);											
			}
			
			
		} catch (Exception e) {
			if(e instanceof OdooException) {
				((OdooException)e).setTarget(uid.toString());
			}
			loggerService.logException(Level.SEVERE, "Error on validation payload: "+payload, e);
			throw e;
			
			
		}
        
    }
    
    /**
     * @param request
     * @param resp
     * @throws IOException
     * @throws JsonProcessingException
     * @throws InterruptedException 
     */
    @RequestMapping(path = "/consumo", method = RequestMethod.POST,produces = {MediaType.ALL_VALUE}, consumes = MediaType.ALL_VALUE)
    public void gestioneConsumi(HttpServletRequest request, HttpServletResponse resp) throws IOException, JsonProcessingException, InterruptedException {
         String test = null;
        try {
            test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            String[] valoreConsumo = test.split(":");
            energyService.saveEnergyConsuption(valoreConsumo);
        } catch (Exception ex) {
            loggerService.logException(Level.SEVERE,"/consumo "+test ,ex);
        }
                    
    }

}
