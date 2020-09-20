/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.dati;

/**
 *
 * @author Jack
 */
public interface EnergyService {
    
    /**
     * valori attesi tre
     * @param consum 
     */
    public void saveEnergyConsuption(String[] consum) throws Exception;
    
   
    
    
}
