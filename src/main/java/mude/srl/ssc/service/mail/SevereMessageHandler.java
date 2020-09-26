/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.mail;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import mude.srl.ssc.service.log.LoggerSSC;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author upload
 */

public class SevereMessageHandler extends Handler {

    
    
    ArrayList<String> emailRecipient = new ArrayList<>();
    ArrayList<LogRecord> errors = new ArrayList<>();
    private final String applicationTitle;
    private static final String NEW_LINE = "<br/>";
    private String pattern;
    //private final EmailSenderService emailService;
  
  
    private EmailSenderService emailSenderService;

    public SevereMessageHandler(EmailSenderService emailSenderService) {
        super();
        init();
        applicationTitle = "";
        this.emailSenderService = emailSenderService;
        
    }

   

    @Override
    public void publish(LogRecord record) {

        if (record.getLevel() == getLevel()) {
            errors.add(record);
            flush();
        }

    }

    private String getCustomStackTrace(Throwable e) {
        final StringBuilder result = new StringBuilder("StackTraceInfo: ");
        result.append(e.toString());
        result.append(NEW_LINE);

        //add each element of the stack trace
        for (StackTraceElement element : e.getStackTrace()) {
            result.append(element);
            result.append(NEW_LINE);
        }
        return result.toString();
    }

    @Override
    public void flush() {
        publish();
    }

    @Override
    public void close() throws SecurityException {
               
        publish();
               
    }

    private void init() {
        configureEmailRecipient();
        setLevel(Level.SEVERE);
    }

    /**
     *
     */
    private void configureEmailRecipient() {
        emailRecipient.add("ariannagiacomo@gmail.com");
        
    }

    /**
     *
     * @param email
     */
    public void addEmailRecipient(String email) {
        emailRecipient.add(email);
    }

    /**
     *
     */
    public void publish() {
        if (!errors.isEmpty()) {
            String subject = "Error Log Service - " + applicationTitle;
            String message = "  </br>";
            try {

                for (LogRecord record : errors) {
                    message += NEW_LINE;

                    if (record.getMessage() != null) {

                        Formatter formatter = getFormatter();
                        if (formatter != null) {

                            String format = getFormatter().format(record);
                            message += format;
                        }
                    }
                    Throwable thrown = record.getThrown();

                    if (thrown != null) {
                        if (thrown instanceof SQLException) {

                            SQLException se = (SQLException) thrown;
                            message += NEW_LINE + "Errorno:" + se.getErrorCode();
                            message += NEW_LINE + "SQL State:" + se.getSQLState();

                        }
                        message += NEW_LINE + thrown.getMessage();

                        message += NEW_LINE + getCustomStackTrace(thrown);

                    }

                }
                emailSenderService.sendEmailLog(subject, message, subject, emailRecipient);

            } catch (Exception ex) {

                LoggerSSC.getInstance(this.emailSenderService).getLogger().log(Level.WARNING, ex.getMessage());
                
            }
            errors.clear();
        }
    }

}
