/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.accessi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author Jack
 */
@JsonInclude(Include.NON_NULL)
public class RequestTokenMessage extends MIDMessage{
    
    @JsonProperty("FIFO")
    private List<ActivePayload> fifo;

    @JsonProperty("FIFO")
    public List<ActivePayload> getFifo() {
        return fifo;
    }

    public void setFifo(List<ActivePayload> fifo) {
        this.fifo = fifo;
    }
    
    

   
}

