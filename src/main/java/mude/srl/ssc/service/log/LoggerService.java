/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.log;

import java.util.logging.Level;

/**
 *
 * @author Jack
 */
public interface LoggerService {
 
    public void logException(Level level, String message,Throwable exception);

    public void logInfo(Level INFO, String message, Object object);
    
    public void logInfo(Level INFO, String message);

    public void logWarning(Level level, String message);
}
