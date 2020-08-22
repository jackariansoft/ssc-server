/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.command.model;

/**
 *
 * @author Jack
 */
public enum CommandMessageType {
    MESSAGE_TYPE("MESSAGE"),
    ACTIVATION_TYPE("ACTIVATION"),
    WARNING("WARNING");

    private final String type;
    private CommandMessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
    
}
