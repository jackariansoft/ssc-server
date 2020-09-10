/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.ariannamondo.mag.sms;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author giacomo
 */

public class SmsPK implements Serializable {

    private int cli;

    private Date data;
    
    private String cellnum;

    public SmsPK() {
    }

    public SmsPK(int cli, Date data, String cellnum) {
        this.cli = cli;
        this.data = data;
        this.cellnum = cellnum;
    }

    public int getCli() {
        return cli;
    }

    public void setCli(int cli) {
        this.cli = cli;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getCellnum() {
        return cellnum;
    }

    public void setCellnum(String cellnum) {
        this.cellnum = cellnum;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += cli;
        hash += (data != null ? data.hashCode() : 0);
        hash += (cellnum != null ? cellnum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmsPK)) {
            return false;
        }
        SmsPK other = (SmsPK) object;
        if (this.cli != other.cli) {
            return false;
        }
        if ((this.data == null && other.data != null) || (this.data != null && !this.data.equals(other.data))) {
            return false;
        }
        if ((this.cellnum == null && other.cellnum != null) || (this.cellnum != null && !this.cellnum.equals(other.cellnum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SmsPK[cli=" + cli + ", data=" + data + ", cellnum=" + cellnum + "]";
    }

}
