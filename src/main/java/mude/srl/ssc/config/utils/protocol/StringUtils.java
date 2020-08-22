/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.config.utils.protocol;

/**
 *
 * @author Jack
 */
public class StringUtils {
    
    private static  StringUtils instance;

    public static StringUtils getInstance() {
        if(instance==null){
            instance  = new StringUtils();
        }
        return instance;
    }
    
    public String clearString(String str){
        
        str = str.replaceAll("\n","").replaceAll("\r","");
        return str;
    }
    
    
    
}
