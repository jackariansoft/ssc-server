/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.common.client;

import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Jack
 */
public class CommonRestClient {
    
    protected String url  ="192.168.2.187";
    protected String proto = "https";
    protected String port  = "10000";
    private RestTemplate rest;

    public CommonRestClient() {
        rest  = new RestTemplate();
    }
    
   

    public RestTemplate getRest() {
        return rest;
    }

    public void setRest(RestTemplate rest) {
        this.rest = rest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProto() {
        return proto;
    }

    public void setProto(String proto) {
        this.proto = proto;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    
    
    
    
}
