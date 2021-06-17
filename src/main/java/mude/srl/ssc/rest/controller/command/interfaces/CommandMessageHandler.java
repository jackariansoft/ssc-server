/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.command.interfaces;

import java.util.concurrent.locks.ReentrantLock;

import mude.srl.ssc.rest.controller.command.model.ResponseCommand;

/**
 *
 * @author Jack
 */
public interface CommandMessageHandler {
    
    public void handle(Command command,ResponseCommand responce) throws Exception;
   // public void handle(Command command,ResponseCommand responce,ReentrantLock lock) throws Exception;
    
}
