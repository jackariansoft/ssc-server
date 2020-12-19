/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.entity.utils;

/**
 * 0=attesa,1=avviata,2=terminata,3=interrotta,4=scaduta,5=payload non valido
 * @author Jack
 */
public enum ResourceStatus {
    ATTESA(Short.valueOf("0")),
    AVVIATA(Short.valueOf("1")),
    TERMINATA(Short.valueOf("2")),
    INTERROTTA(Short.valueOf("3")),
    SCADUTA(Short.valueOf("4")),
    PAYLOAD_NON_VALIDO(Short.valueOf("5")),
    SOSPESA(Short.valueOf("6"));
    
    private final Short status;

    private ResourceStatus(Short status) {
        this.status = status;
    }

    public Short getStatus() {
        return status;
    }
    
    
}
