/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.command.handler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

import mude.srl.ssc.rest.controller.command.interfaces.Command;
import mude.srl.ssc.rest.controller.command.interfaces.CommandMessageHandler;
import mude.srl.ssc.rest.controller.command.model.CommandMessageType;
import mude.srl.ssc.rest.controller.command.model.MessageActivationCommand;
import mude.srl.ssc.rest.controller.command.model.ResponseCommand;
import mude.srl.ssc.rest.controller.common.client.CommonRestClient;
import mude.srl.ssc.service.log.LoggerSSC;

/**
 *
 * @author Jack
 */
public class ActivationCommandHandler extends CommonRestClient implements CommandMessageHandler {

   
    
	@Override
	public  void  handle(Command command, ResponseCommand response) throws Exception {

		
		if (command.getType().equals(CommandMessageType.ACTIVATION_TYPE.getType())) {

			MessageActivationCommand c = (MessageActivationCommand) command;

			Socket socket  = null;
			try {
							
				
				socket = new Socket(url, Integer.valueOf(port));
				socket.setTcpNoDelay(true);
				OutputStream output = socket.getOutputStream();
				PrintWriter writer = new PrintWriter(output, true);
				InputStream input = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));

				String cmd = "action:"+((c.getDestination()*10)+c.getAction());

				writer.println(cmd);
				writer.println();

				String resp="";
				String line;

				while ((line = reader.readLine()) != null) {
					
					resp += line;
					
				}
				if(resp==null||!resp.trim().equals(cmd)) {
					response.setErrorMessage("No message from plc");
					response.setStatus(500);
					response.setSucceccMessage(null);
					
				}
				
				socket.close();
			}catch (Exception e) {				
				response.setErrorMessage(e.getMessage());
				response.setStatus(500);
				response.setSucceccMessage(null);
				response.setEx(e);
				
			}finally {
				
				if(socket!=null&&!socket.isClosed()) {
					socket.close();
				}
			} 
		}

	}

	@Override
	public void handle(Command command, ResponseCommand response, ReentrantLock lock) throws Exception {
		try {
			lock.lock();
			this.handle(command, response);
		}finally {
			lock.unlock();
		}
		
	}

}

