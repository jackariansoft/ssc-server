/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.sms;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giacomo
 */
public class SMSPostSender {
    //Definizione parametri pe invio messaggio.

    //private String login    = "sbuser13760";
    //private String password = "7esaibf";
    private String login    = "sbuser13762";
    private String password = "oyh7cgd";
    private String dest     = "";
    private String testo    = "";
    private String mitt     = "";
    private String tipo     = "";
    //Opzionale
    private int status = -1;
    private int flash = -1;
    //Keys
    private final String klogin    = "login";
    private final String kpassword = "password";
    private final String kdest     = "dest";
    private final String ktesto    = "testo";
    private final String kmitt     = "mitt";
    private final String ktipo     = "tipo";

    public SMSPostSender() {
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public int getFlash() {
        return flash;
    }

    public void setFlash(int flash) {
        this.flash = flash;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMitt() {
        return mitt;
    }

    public void setMitt(String mitt) {
        this.mitt = mitt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    /**
     * 
     * @return message code response.
     */
    public int sendMessage() {
        int response = -1;
        try {
            // Construct data
            String data = URLEncoder.encode(klogin,   "UTF-8") + "=" + URLEncoder.encode(this.login,    "UTF-8");
            data += "&" + URLEncoder.encode(kpassword,"UTF-8") + "=" + URLEncoder.encode(this.password, "UTF-8");
            data += "&" + URLEncoder.encode(kdest,    "UTF-8") + "=" + URLEncoder.encode(this.dest,     "UTF-8");
            data += "&" + URLEncoder.encode(ktipo,    "UTF-8") + "=" + URLEncoder.encode(this.tipo,     "UTF-8");
            data += "&" + URLEncoder.encode(kmitt,    "UTF-8") + "=" + URLEncoder.encode(this.mitt,     "UTF-8");
            data += "&" + URLEncoder.encode(ktesto,   "UTF-8") + "=" + URLEncoder.encode(this.testo,    "UTF-8");

            // Send data
            URL url = new URL("http://www.nsgateway.net/smsscript/sendsms.php ");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            BufferedReader rd;
            try (OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream())) {
                wr.write(data);
                wr.flush();
                // Get the response
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    // Process line...
                    if(!line.isEmpty())
                        response = processResponse(line.trim());
                }
            }
            rd.close();
        } catch (IOException e) {
            Logger.getLogger("SMSTest").log(Level.SEVERE, "", e);
        }
        return response;
    }

    public static void main(String[] args) {

        SMSPostSender sander = new SMSPostSender();       
        sander.setTipo("1");
        sander.setMitt("multiverso");
        sander.setDest("+393385272058");
        sander.setTesto("vcodetest:"+"14908ghjk9");
        
        System.out.println(SMSResponse.getMsgResponse(sander.sendMessage()));

    }

    private int processResponse(String line) {
      
        int code = -1;
        line = line.trim();
        if (line.startsWith("OK") && getTipo().equals("2")) {
            
            String[] lines = line.split(" ");
            code = Integer.parseInt(lines[1]);
            
        } else if (line.equals("OK")) {
            code = 10;
        } else {
            try{
            code = Integer.parseInt(line);
            }catch(NumberFormatException ex){
                code = 1000;
            }
        }
       
        return code;
    }
    /**
     * Crea un messaggio secondo le specifiche dell'operatore.
     * @param sms
     * @return
     */
   
    public void reset(){
         dest     = "";
         testo    = "";
         mitt     = "";
         tipo     = "";
    }
}
