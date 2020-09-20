/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.command.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mude.srl.ssc.rest.controller.command.interfaces.Command;

/**
 *
 * @author Jack
 */
@JsonPropertyOrder({"Command"})
public class ActivationCommand  implements Command,Serializable{

    private final String type = CommandMessageType.ACTIVATION_TYPE.getType();
    private final ArrayList<String> command;

    public ActivationCommand() {
        command = new ArrayList<>(4);
        command.add(0, type);
        command.add(1, "");
        command.add(2, "");
        command.add(3, "");
    }

    
    @JsonProperty("Command")
    public List<String> getCommand() {
        return command;
    }

    @JsonIgnore
    public void setAction(String action){
         command.set(1, action);
    }
    @JsonIgnore
    public void setMessage(String message){
         command.set(2, message);
    }
    
    @JsonIgnore
    public void setDestination(String destination){
         command.set(3, destination);
    }

    @Override
    @JsonIgnore
    public String getType() {
        return type;
    }
    
    
   
   

}
