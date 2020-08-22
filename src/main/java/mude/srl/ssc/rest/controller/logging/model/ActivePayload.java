/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.rest.controller.logging.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Jack
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivePayload {

    @JsonProperty("Date")
    private String Date;
    @JsonProperty("Value")
    private ActvePayloadValue Value;

    public String getDate() {
        return Date;
    }

    @JsonProperty("Date")
    public void setDate(String Date) {
        this.Date = Date;
    }

    @JsonProperty("Value")
    public ActvePayloadValue getValue() {
        return Value;
    }

    public void setValue(ActvePayloadValue Value) {
        this.Value = Value;
    }

}
