/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.config.endpoint;

/**
 *
 * @author jackarian
 */
public class ServiceEndpoint {

    public static final String ROOT_END_POINT = "/api";

    /**
     * Autenticazione
     */
    public static final String AUTH      = ROOT_END_POINT + "/auth";
    public static final String LOGIN     = AUTH + "/login";
    public static final String LOGOUT    = AUTH + "/logout";
    public static final String REGISTER   = AUTH + "/register";

    /**
     * Rirsorsa user
     */
    public static final String USER        = ROOT_END_POINT + "/user";
    public static final String USER_GROUPS = USER + "/{user}/groups";
    /**
     * 
     * Risorsa installazione
     * 
     */
    public static final String INSTALLAZIONE  = ROOT_END_POINT+"/installazione";//GET
    public static final String INSTALLAZIONE_LIST  = INSTALLAZIONE+"/{page_size}/{offset}";//GET
    public static final String INSTALLAZIONE_DETAILS  = INSTALLAZIONE+"/{id}/details";//GET
    public static final String INSTALLAZIONE_CREATE  = INSTALLAZIONE;//POST
    public static final String INSTALLAZIONE_UPDATE  = INSTALLAZIONE+"/{id}";//PUT
    
    /**
     * Gestione Risorsa
     */
    public static final String RESOURCE = ROOT_END_POINT+"/resource";
    public static final String UPDATE_RESOURCE = ROOT_END_POINT+"/resource/{id}";//POST
    /**
     * Aggiornamento PLC e Risorse collegate
     */
    public static final String LIST_PLC = ROOT_END_POINT+"/plc";//GET
    public static final String ADD_PLC = ROOT_END_POINT+"/plc";//POST
    public static final String UPDATE_PLC = ROOT_END_POINT+"/plc/{id}";//POST
    public static final String ADD_RESOURCE_TO_PLC = ROOT_END_POINT+"/plc/{id}/resource";//POST
    /**
     * Funzionalita'
     */
    public static final String RESOURCE_COMMAND =RESOURCE+"/command";
    public static final String RESOURCE_ATTIVA =RESOURCE+"/attiva-prenotazione";
    public static final String RESOURCE_RESERVATIONS =RESOURCE+"/reservation";
    /**
     * Gestione dei task collegati alla prenotazioni
     * 
     */
    public static final String SCHEDULER = RESOURCE+"/scheduler";
    public static final String RESOURCE_JOB_SUSPEND = SCHEDULER+"/suspend_job";
    public static final String RESOURCE_JOB_TERM = SCHEDULER+"/terminate_job";
    public static final String RESOURCE_JOB_RESUME = SCHEDULER+"/resume_job";
    
    
}
