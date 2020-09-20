/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.command.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Jack
 */
public class MIDResponse {
    @JsonProperty("MID")
    private long MID; 

    @JsonProperty("MID")    
    public long getMID() {
        return MID;
    }

    public void setMID(long MID) {
        this.MID = MID;
    }
    
    
}
