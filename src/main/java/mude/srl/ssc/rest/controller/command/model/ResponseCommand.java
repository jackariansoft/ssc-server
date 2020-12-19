/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.command.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.ServerRequest;

/**
 *
 * @author Jack
 */
public class ResponseCommand {
    private Integer status;
    private String errorMessage;
    private String succeccMessage;
    private Exception ex;
    
    
    

    public Exception getEx() {
		return ex;
	}


	public void setEx(Exception ex) {
		this.ex = ex;
	}


	public ResponseCommand() {
        status = HttpStatus.OK.value();
    }

    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSucceccMessage() {
        return succeccMessage;
    }

    public void setSucceccMessage(String succeccMessage) {
        this.succeccMessage = succeccMessage;
    }
    
    
    
    
}
