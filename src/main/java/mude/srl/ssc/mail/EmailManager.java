/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.mail.MessagingException;
import mude.srl.ssc.entity.Channel;
import mude.srl.ssc.entity.Email;
import mude.srl.ssc.entity.utils.ResponseRequest;
import mude.srl.ssc.service.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author giacomo
 */
public class EmailManager {

    @Autowired
    private EmailService mu;
    
    private static EmailManager instance;

    public static EmailManager getInstance() {
        if(instance==null){
            instance = new EmailManager();
        }
        return instance;
    }
    
    

    public  void sendEmailLog(String msg, Channel c) {

        try {

            
            if (mu != null) {
                ResponseRequest<Email> accounts = mu.getAccountsByChannerl(c);
                if (accounts.isError()) {
                    Exception xerror = accounts.getEx();
                    LoggerSSC.getInstance().getLogger().log(Level.SEVERE, "Error Retriving email account by channel", xerror);

                } else {
                    EmailSender sender = new EmailSender(accounts.getData());
                    sender.setAuth(true);
                    sender.setSubject("Scc Log Service");
                    sender.setBody("<htm><header></header><body><p>" + msg + "</p> </body></html>");
                    sender.setTextonly(true);
                    sender.setFrom(sender.getLogin());

                    ArrayList<String> emails = new ArrayList<>();
                    emails.add("ariannagiacomo@gmail.com");                  
                    sender.setTo(emails);
                    sender.send();
                }
            }

        } catch (MessagingException | IOException ex) {
            LoggerSSC.getInstance().getLogger().log(Level.SEVERE, "Errore Invio Email", ex);
        }
    }

     void sendEmailLog(String subject, String message, Object object, ArrayList<String> emailRecipient) {
        ResponseRequest<Email> accounts = mu.getDefault();
        if (accounts.isError()) {
            Exception xerror = accounts.getEx();
            LoggerSSC.getInstance().getLogger().log(Level.SEVERE, "Error Retriving Default Account", xerror);

        } else {
            try {
                EmailSender sender = new EmailSender(accounts.getData());
                sender.setAuth(true);
                sender.setSubject(subject);
                sender.setBody("<htm><header><body>" + message + "</body></header></html>");
                sender.setTextonly(true);
                sender.setFrom(sender.getLogin());
                sender.setTo(emailRecipient);
                sender.send();
            } catch (MessagingException | IOException ex) {
                LoggerSSC.getInstance().getLogger().log(Level.SEVERE, "Error Retriving Default Account", ex);
            }
        }
    }

}
