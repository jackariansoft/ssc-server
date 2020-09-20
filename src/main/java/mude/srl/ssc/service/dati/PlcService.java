/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.dati;

import mude.srl.ssc.entity.Plc;

/**
 *
 * @author Jack
 */
public interface PlcService {
    
     public Plc getPlcByUID(String uid);
}
