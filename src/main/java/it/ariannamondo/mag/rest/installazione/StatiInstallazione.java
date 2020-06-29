/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.rest.installazione;

/**
 *
 * @author jackarian
 * //stato del task 0=ATTESA,1=AVVIATO,2=CONCLUSO,3=ANNULLATO,4=SOSPESO,5=ELIMINATA/NON ATTIVA
 */
public enum StatiInstallazione {
    ATTESA(0),
    AVVIATA(1),
    CONLCUSA(2),
    ANNULLATA(3),
    SOSPESA(4),
    ELIMINATA(5);
    
    private final int stato;
    private StatiInstallazione(int stato){
        this.stato = stato;
    }

    public int getStato() {
        return stato;
    }
    
    
    
}
