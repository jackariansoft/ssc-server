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
    ATTESA((short)0),
    AVVIATA((short)1),
    CONLCUSA((short)2),
    ANNULLATA((short)3),
    SOSPESA((short)4),
    ELIMINATA((short)5);
    
    private final short stato;
    private StatiInstallazione(short stato){
        this.stato = stato;
    }

    public short getStato() {
        return stato;
    }
    
    
    
}
