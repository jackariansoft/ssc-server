/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.sms;

import java.util.TreeMap;

/**
 *
 * @author giacomo
 */
public final class SMSResponse {

public  static final int LOGIN_MISSING = 100; // La login non è stata impostata.
public  static final int PASSWORD_MISSING = 101; // La password non è stata impostata.
public  static final int DESTINATION_MISSING = 102; // I destinatari non sono stati impostati.
public  static final int TYPE_OPERATION_MISSING = 103; // Il tipo di operazione non è stato impostato.
public  static final int SANDER_MISSING = 104; // Il mittente non è stato impostato.
public  static final int TEXT_MSG_MISSING = 105;// Il testo del messaggio non è stato impostato.
public  static final int LOGIN_EMPTY = 106;// La login non può essere vuota.
public  static final int PASSWORD_EMPTY = 107;// La password non può essere vuota.
public  static final int SANDER_EMPTY = 108;// Il mittente non può essere vuoto.
public  static final int OPERATION_NOTCORRETC = 109;// Il tipo di operazione impostata non è corretta.
public  static final int OVERFLOW_SANDER_LENGHT = 110;// Il mittente non può essere più lungo di 11 caratteri.
public  static final int AUTHENTICATION_FAILED = 111;// Errore di autenticazione.
public  static final int PARAMETERS_ERROR = 112;// I parametri impostati non sono corretti.
public  static final int NO_CREDIT = 113;// Crediti SMS esauriti.
public  static final int GENERIC_ERROR = 114;// Errore generico.
// Aggiunta per compatibilita' da parte del programmatore
public  static final int MESSAGE_SENT_OK =10; //Messaggio inviato con successo.

    
    private static final TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    public static String getMsgResponse(int code) {     
        return map.get(code);
    }

    static {
        map.put(AUTHENTICATION_FAILED, "Errore di autenticazione.");
        map.put(LOGIN_MISSING, "La login non è stata impostata.");
        map.put(PASSWORD_MISSING, "La password non è stata impostata.");
        map.put(DESTINATION_MISSING, "Il destinatario non è stati impostati.");
        map.put(TYPE_OPERATION_MISSING, "Il tipo di operazione non è stato impostato.");
        map.put(SANDER_MISSING, "Il mittente non è stato impostato.");
        map.put(TEXT_MSG_MISSING, "Il testo del messaggio non è stato impostato.");
        map.put(LOGIN_EMPTY, "La login non può essere vuota.");
        map.put(PASSWORD_EMPTY, "La password non può essere vuota.");

        map.put(SANDER_EMPTY, "Il mittente non può essere vuoto.");
        map.put(OPERATION_NOTCORRETC, "Il tipo di operazione impostata non è corretta.");
        map.put(OVERFLOW_SANDER_LENGHT, "Il mittente non può essere più lungo di 11 caratteri.");
        map.put(AUTHENTICATION_FAILED, "Errore di autenticazione.");
        map.put(PARAMETERS_ERROR, "I parametri impostati non sono corretti.");
        map.put(GENERIC_ERROR, "Errore generico.");
        map.put(NO_CREDIT, "Crediti SMS esauriti.");
        map.put(MESSAGE_SENT_OK, "Messaggio Inviato con Successo!");
    }
}
