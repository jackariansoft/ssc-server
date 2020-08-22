/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.entity.utils;

/**
 *
 * @author giacomo
 */
public class ResponseRequest<T> {
    
   
    private T data;
    private Exception ex;
    private boolean error;
    private int messageCode;
    private int errorCode;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    
    

    public ResponseRequest() {
        this.error =false;
    }

    
    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }
    
    

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

//    public String getTarget() {
//        return target;
//    }
//
//    public void setTarget(String target) {
//        this.target = target;
//    }
    
    
    
}
