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
public class RequestCommand {
    String plc_uid;
    String resource_tag;
    Integer action;

    public String getPlc_uid() {
        return plc_uid;
    }

    public void setPlc_uid(String plc_uid) {
        this.plc_uid = plc_uid;
    }

    public String getResource_tag() {
        return resource_tag;
    }

    public void setResource_tag(String resource_tag) {
        this.resource_tag = resource_tag;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }
    
    
}
