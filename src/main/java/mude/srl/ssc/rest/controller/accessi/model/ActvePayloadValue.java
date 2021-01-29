/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.accessi.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author Jack
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActvePayloadValue {
    
    private String ip_address;
    private String timeStamp;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    
}
