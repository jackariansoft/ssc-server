/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Jack
 */
@Embeddable
public class EmailPK implements Serializable {

    @Basic(optional = false)
    @NotNull
     
    @Column(nullable = false, length = 150)
    private String login;
    @Basic(optional = false)
    @NotNull
     
    @Column(nullable = false, length = 150)
    private String password;
    @Basic(optional = false)
    @NotNull
     
    @Column(nullable = false, length = 150)
    private String smtp;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private long channel;

    public EmailPK() {
    }

    public EmailPK(String login, String password, String smtp, long channel) {
        this.login = login;
        this.password = password;
        this.smtp = smtp;
        this.channel = channel;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public long getChannel() {
        return channel;
    }

    public void setChannel(long channel) {
        this.channel = channel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        hash += (password != null ? password.hashCode() : 0);
        hash += (smtp != null ? smtp.hashCode() : 0);
        hash += (int) channel;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmailPK)) {
            return false;
        }
        EmailPK other = (EmailPK) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        if ((this.password == null && other.password != null) || (this.password != null && !this.password.equals(other.password))) {
            return false;
        }
        if ((this.smtp == null && other.smtp != null) || (this.smtp != null && !this.smtp.equals(other.smtp))) {
            return false;
        }
        if (this.channel != other.channel) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mude.srl.ssc.entity.utils.EmailPK[ login=" + login + ", password=" + password + ", smtp=" + smtp + ", channel=" + channel + " ]";
    }
    
}
