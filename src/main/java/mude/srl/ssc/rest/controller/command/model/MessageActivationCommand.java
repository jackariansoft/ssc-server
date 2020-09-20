/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.command.model;

import mude.srl.ssc.rest.controller.command.interfaces.Command;

/**
 *
 * @author Jack
 */
public class MessageActivationCommand extends MIDResponse implements Command{

     private final String type = CommandMessageType.ACTIVATION_TYPE.getType();
    
     private int action;
     private String message;
     private int destination;
     
    @Override
    public String getType() {
       return type;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "MessageActivationCommand{" + "type=" + type + ", action=" + action + ", message=" + message + ", destination=" + destination + '}';
    }

    
    
    
}
