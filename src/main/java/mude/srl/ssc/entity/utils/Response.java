/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.entity.utils;

/**
 *
 * @author upload
 * @param <T>
 */

public class Response<T> {

    public static final int BRAND_LOCKED_ACKING = 1;
    public static final int SESSION_EXPIRED = 2;
    public static final int UNEXPECTED_ERROR = 3;
    public static final int BAD_REQUEST = 4;
    public static final int INTERNAL_SERVER_ERROR = 5;
    public static final int NO_DATA_FOUND = 6;
    public static final int DATABASE_ERROR = 7;
    public static final int EAN_LIMIT_EXCEETED = 8;
    public static final int ORDER_DATA_ERROR = 9;
    public static final int BATCH_IN_PROGRESS = 10;
    public static final int ORDER_SHIPPING_CREATION_ERROR = 11;
    public static final int FILE_SHIPPING_CREATION_ERROR = 12;
    public static final int WEBSERICES_ERROR = 13;
    
    public static final int EAN_LIMIT_UPDATEBLE = 5000;
    public static final String ERRROR_MG_BRAND_LOCKED_ACKING = "BRAND_LOCKED_ACKING";
    public static final String ERROR_MG_SESSION_EXPIRED = "SESSION_EXPIRED";
    public static final String ERROR_MG_UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
    public static final String ERROR_MG_BAD_REQUEST = "BAD_REQUEST";
    public static final String ERROR_MG_INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String INFO_MG_DB_NOT_DATA_FOUND = "NO_DATA";
    public static final String ERROR_MSG_DATABASE_ERROR = "DATABASE_ERROR";
    public static final String ERROR_MSG_ORDEDER_DATA_ERROR="ORDER DATA ERROR";
    public static final String ERROR_MSG_BATCH_IN_PROGRESS="BATCH_IN_PROGRESS";
    public static final String ERROR_MSG_ORDER_SHIPPING_CREATION_ERROR ="ORDER_SHIPPING_CREATION_ERROR";
    public static final String ERROR_MSG_FILE_SHIPPING_CREATION_ERROR ="FILE_SHIPPING_CREATION_ERROR";
    public static final String ERROR_MSG_SHIPPING_ADDRESS_NOT_FOUND_ERROR ="SHIPPING_ADDRESS_NOT_FOUND";
    public static final String ERROR_MSG_WEBSERICES_ERROR ="WEBSERICES_ERROR";
    
    
    

    public static final String LIMIT_EAN_EXCEETED_ERROR = "LIMIT EAN EXCEETED";
    private T result;

    private boolean fault;
    private int errorType;
    private String errorDescription="";
    private String errorMessage="";
    private Exception exception;
    private String user ;
    private String ip;
    private String user_agent;
    private String date;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }       

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public Response() {
        fault = false;
        errorMessage = null;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isFault() {
        return fault;
    }

    public void setFault(boolean fault) {
        this.fault = fault;
    }

    public String getMessagebyCode(int code) {
        String res = null;
        switch (code) {
            case BAD_REQUEST:
                res = ERROR_MG_BAD_REQUEST;
                break;
            case BRAND_LOCKED_ACKING:
                res = ERRROR_MG_BRAND_LOCKED_ACKING;
                break;
            case INTERNAL_SERVER_ERROR:
                res = ERROR_MG_INTERNAL_SERVER_ERROR;
                break;
            case NO_DATA_FOUND:
                res = INFO_MG_DB_NOT_DATA_FOUND;
                break;
            case SESSION_EXPIRED:
                res = ERROR_MG_SESSION_EXPIRED;
                break;
            case UNEXPECTED_ERROR:
                res = ERROR_MG_UNEXPECTED_ERROR;
                break;
        }
        return res;
    }

}
