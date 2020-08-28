/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.config.endpoint;

/**
 *
 * @author jackarian
 */
public class ServiceEndpoint {

    public static final String ROOT_END_POINT = "/api";

    /**
     * Autenticazione
     */
    public static final String AUTH = ROOT_END_POINT + "/auth";
    public static final String LOGIN = AUTH + "/login";
    public static final String LOGOUT = AUTH + "/logout";
    public static final String REGISTER = AUTH + "/register";

    /**
     * Rirsorsa user
     */
    public static final String USER = ROOT_END_POINT + "/user";
    public static final String USER_GROUPS = USER + "/{user}/groups";
    /**
     *
     * Risorsa installazione
     *
     */
    public static final String INSTALLAZIONE = ROOT_END_POINT + "/installazione";//GET
    public static final String INSTALLAZIONE_LIST = INSTALLAZIONE + "/{page_size}/{offset}";//GET
    public static final String INSTALLAZIONE_DETAILS = INSTALLAZIONE + "/{id}/details";//GET
    public static final String INSTALLAZIONE_CREATE = INSTALLAZIONE;//POST
    public static final String INSTALLAZIONE_UPDATE = INSTALLAZIONE + "/update/{id}";//PUT
    public static final String INSTALLAZIONE_REMOVE = INSTALLAZIONE + "/remove/{id}";//DELETE
    /**
     * Risorsa location
     */
    public static final String LOCATION = ROOT_END_POINT + "/location";//GET}
    public static final String LOCATION_LIST = LOCATION + "/{page_size}/{offset}";//GET
    public static final String LOCATION_DETAILS = LOCATION + "/{id}";//GET
    public static final String LOCATION_CREATE = LOCATION;//POST
    public static final String LOCATION_UPDATE = LOCATION + "/update/{id}";//PUT
    public static final String LOCATION_REMOVE = LOCATION + "/remove/{id}";//DELETE
}
