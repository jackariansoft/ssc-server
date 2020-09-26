/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.log;

import java.util.logging.Level;
import mude.srl.ssc.service.mail.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jack
 */
@Service
public class LoggerServiceImpl implements LoggerService{

    @Autowired
    private EmailSenderService emailSenderService;
            
    @Override
    public void logException(Level level, String message, Throwable exception) {
        LoggerSSC.getInstance(emailSenderService).getLogger().log(level, message, exception);
    }

    @Override
    public void logInfo(Level levl, String message, Object object) {
        LoggerSSC.getInstance(emailSenderService).getLogger().log(levl, message, object);
    }

    @Override
    public void logWarning(Level level, String message) {
       LoggerSSC.getInstance(emailSenderService).getLogger().log(level, message);
    }
    
}
