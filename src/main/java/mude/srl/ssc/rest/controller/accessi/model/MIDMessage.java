package mude.srl.ssc.rest.controller.accessi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Classe che rappresenta il messagio inviato dal Plc 
 * per sengnalare la sua attivita
 *
 * @author Jack
 */
@JsonPropertyOrder({"MID","ST","UID","MV"})
public class MIDMessage {

    @JsonProperty("MID")
    private long MID;
    
    @JsonProperty("ST")
    private long ST;
    
    @JsonProperty("UID")
    private long UID;
    
    @JsonProperty("MV")
    private String MV;

    public MIDMessage(long MID, long ST, long UID, String MV) {
        this.MID = MID;
        this.ST = ST;
        this.UID = UID;
        this.MV = MV;
    }

    public MIDMessage() {
    }

    @JsonProperty("MID")
    public long getMID() {
        return MID;
    }

    public void setMID(long MID) {
        this.MID = MID;
    }

    @JsonProperty("ST")
    public long getST() {
        return ST;
    }

    public void setST(long ST) {
        this.ST = ST;
    }

    @JsonProperty("UID")
    public long getUID() {
        return UID;
    }

    public void setUID(long UID) {
        this.UID = UID;
    }

    @JsonProperty("MV")
    public String getMV() {
        return MV;
    }

    public void setMV(String MV) {
        this.MV = MV;
    }

}
