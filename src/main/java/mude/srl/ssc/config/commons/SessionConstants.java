/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.config.commons;

/**
 *
 * @author jackarian
 */
public enum SessionConstants {   
    
    USER("user");
    
    private final String value;
        
    private SessionConstants(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
}
