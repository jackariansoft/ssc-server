/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.ariannamondo.mag.sms;

/**
 *
 * @author giacomo
 */
public class SMSResponseAndCode {
    
    private String response = null;
    private int codeResponse = -7;

    public SMSResponseAndCode() {
        super();
    }
    public SMSResponseAndCode(String response, int code) {
      this.codeResponse = code;
      this.response = response;
    }


    public int getCodeResponse() {
        return codeResponse;
    }

    public void setCodeResponse(int codeResponse) {
        this.codeResponse = codeResponse;
    }

    public String getRespose() {
        return response;
    }

    public void setRespose(String respose) {
        this.response = respose;
    }

    
    
}
