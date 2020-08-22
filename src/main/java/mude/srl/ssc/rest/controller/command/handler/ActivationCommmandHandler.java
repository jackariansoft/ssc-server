/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.command.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mude.srl.ssc.rest.controller.command.interfaces.Command;
import mude.srl.ssc.rest.controller.command.interfaces.CommandMessageHandler;
import mude.srl.ssc.rest.controller.command.model.CommandMessageType;
import mude.srl.ssc.rest.controller.command.model.MessageActivationCommand;
import mude.srl.ssc.rest.controller.common.client.CommonRestClient;
import mude.srl.ssc.rest.controller.logging.ActivationController;

/**
 *
 * @author Jack
 */
public class ActivationCommmandHandler extends CommonRestClient implements CommandMessageHandler {

    @Override
    public void handle(Command command) throws URISyntaxException {
        if (command.getType().equals(CommandMessageType.ACTIVATION_TYPE.getType())) {
            MessageActivationCommand c = (MessageActivationCommand) command;
            ObjectMapper mapper = new ObjectMapper();  
            
            
            try  {
                Socket socket = new Socket(url, Integer.valueOf(port));
                socket.setTcpNoDelay(true);
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                
                //mapper.writeValue(writer, c);
                writer.println("type:ACTIVATION command:ENABLE action:1 message:"+c.getAction()+" destination:cabina");
                
                
                
                //socket.close();

                String line;

                while ((line  = reader.readLine()) != null) {
                
                  
                   Logger.getLogger(ActivationController.class.getName()).log(Level.INFO,line);
                }
               socket.close();
               Logger.getLogger(ActivationController.class.getName()).log(Level.INFO,"Socket Close");
            } catch (UnknownHostException ex) {
                System.out.println("Server not found: " + ex.getMessage());
            }
            catch (IOException ex) {
                Logger.getLogger(ActivationCommmandHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
    }
}
