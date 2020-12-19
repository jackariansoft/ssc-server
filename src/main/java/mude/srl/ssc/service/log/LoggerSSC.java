/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.service.log;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import mude.srl.ssc.service.mail.EmailSenderService;
import mude.srl.ssc.service.mail.EmailService;
import mude.srl.ssc.service.mail.SevereMessageHandler;

/**
 *
 * @author jackarian
 */

public class LoggerSSC {

    
    
    private static final Logger LOGGER = Logger.getLogger("LoggerSmartStation");

    public Logger getLogger() {
        return LOGGER;
    }
    private final SevereMessageHandler smh;
   
    private static LoggerSSC instance;
   

   
    public static final int INSERTED = 1;
    public static final int DOCUMENT_ORDER_NOT_INSERTED = 2;
    public static final int NO_CONNECTION = 3;   
    public static final int ERRORE_INSERIMENTO_PRENOTAZIONE_RISORSA = 21;
    public static final int NO_ORDER_ROW = 9;
    public static final int GENERAL_ERROR = 10;
    public static final int NO_ADDRESS_ROW = 11;
    public static final int ERROR_BUILDING_ROW_ADDRESS = 12;
    public static final int ERROR_BLOCK_QTY_PENDIG_ORDER = 13;
    public static final int ERROR_BUILDING_ROW_ITEM_PENDING = 14;
    public static final int RESOURCE_NOT_FOUND_EXCEPTION = 15;
    public static final int SQL_EXCEPTION = 16;
    public static final int ORDER_NUMEBER_EXCEPTION = 17;
    public static final int NULL_POINTER_EXCEPTION = 18;
    public static final int ERROR_SBLOCK_QTY_PENDIG_RESERVATION = 19;
    public static final int NO_RESULT_FOR_CURRENT_RESOURCE_LIST = 20;
    
    public static final String FILE_FOLDER = "/var/log/spedizioni";
    public static final String FILE_FOLDER_FTP = "/var/log/spedizioni/ftp";

    private  LoggerSSC(EmailSenderService emailSenderService) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        FileHandler fh = new FileHandler(System.getProperty("user.dir") + File.separator + "logger_ssc_" + format.format(Calendar.getInstance(Locale.ITALIAN).getTime()) + ".log");
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

        smh = new SevereMessageHandler(emailSenderService);
        smh.setFormatter(formatter);
        LOGGER.addHandler(fh);
        LOGGER.setUseParentHandlers(true);
        LOGGER.addHandler(smh);
        LOGGER.info("Initializing SSC logger");
    }

    public  static synchronized LoggerSSC getInstance(EmailSenderService emailSenderService) {
        if (instance == null) {
            try {
                instance = new LoggerSSC(emailSenderService);
            } catch (IOException ex) {
                Logger.getLogger(LoggerSSC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    public static void flush() {
        if (LOGGER != null) {
            Handler[] handlers = LOGGER.getHandlers();
            for (Handler h : handlers) {
                h.close();
            }
        }
    }
   

}
