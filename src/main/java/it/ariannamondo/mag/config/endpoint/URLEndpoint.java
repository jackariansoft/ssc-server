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
public enum URLEndpoint {
    ROOT_END_POINT("/api"),
    AUTH(ROOT_END_POINT.url + "/auth"),
    LOGIN(AUTH.url + "/login"),
    LOGOUT(AUTH.url + "/logout"),
    REGISTER(AUTH.url + "/register");

    private String url;

    private URLEndpoint(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     */
    public String url() {
        return url;
    }
}
