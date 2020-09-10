/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.ariannamondo.mag.sms;






/**
 *
 * @author giacomo
 */
public class SMSResponseAndSMS {
    
   
    private SMSResponseAndCode respAndCode ;

    public SMSResponseAndSMS() {
       
        this.respAndCode = null;
    }

   

    public SMSResponseAndCode getRespAndCode() {
        return respAndCode;
    }

    public void setRespAndCode(SMSResponseAndCode respAndCode) {
        this.respAndCode = respAndCode;
    }


    

}
