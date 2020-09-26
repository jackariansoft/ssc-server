/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.mail;

import java.util.List;

/**
 *
 * @author Jack
 */
public interface EmailSenderService {
    
    /**
     *
     * @param subject
     * @param message
     * @param object
     * @param emailRecipient
     * @throws Exception
     */
    public void sendEmailLog(String subject, String message, Object object, List<String> emailRecipient) throws Exception;
}
