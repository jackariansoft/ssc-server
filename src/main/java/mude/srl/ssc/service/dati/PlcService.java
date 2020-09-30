/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.dati;

import mude.srl.ssc.entity.Plc;
import mude.srl.ssc.entity.Resource;
import mude.srl.ssc.entity.ResourceReservation;
import mude.srl.ssc.entity.utils.Response;
import mude.srl.ssc.rest.controller.command.model.RequestCommandResourceReservation;

/**
 *
 * @author Jack
 */
public interface PlcService {
    
     public Plc getPlcByUID(String uid) throws Exception;
     
     public Resource getReourceByPlcAndTag(Plc plc,String tag) throws Exception;
     
     public Resource getReourceByPlcAndTag(String plc_uid,String tag) throws Exception;
     
     public ResourceReservation controllaPerAvvio(Resource r,RequestCommandResourceReservation request) throws Exception;
     
     public Response<ResourceReservation> aggiornaStatoPrenotazione(ResourceReservation r,Short status) throws Exception;
}
