/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jack
 */
@Entity
@Table(catalog = "ssc", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})})
//@XmlRootElement
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmailPK emailPK;
     
    @Column(length = 150)
    private String imap;
     
    @Column(length = 150)
    private String pop;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean valido;
     
    @Column(length = 10)
    private String smtpport;
    private Boolean sslenabled;
    private Boolean ttlenabled;
     
    @Column(length = 10)
    private String imapport;
     
    @Column(length = 10)
    private String popport;
    @Column(name = "is_default")
    private Boolean isDefault;
    @Basic(optional = false)
    @Column(name="id",nullable = false)
    private short ref;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emailConfig")
//    private Collection<Ssc> sscCollection;

    public Email() {
    }

    public Email(EmailPK emailPK) {
        this.emailPK = emailPK;
    }

    public Email(EmailPK emailPK, boolean valido, short ref) {
        this.emailPK = emailPK;
        this.valido = valido;
        this.ref = ref;
    }

    public Email(String login, String password, String smtp, long channel) {
        this.emailPK = new EmailPK(login, password, smtp, channel);
    }

    public EmailPK getEmailPK() {
        return emailPK;
    }

    public void setEmailPK(EmailPK emailPK) {
        this.emailPK = emailPK;
    }

    public String getImap() {
        return imap;
    }

    public void setImap(String imap) {
        this.imap = imap;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public boolean getValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public String getSmtpport() {
        return smtpport;
    }

    public void setSmtpport(String smtpport) {
        this.smtpport = smtpport;
    }

    public Boolean getSslenabled() {
        return sslenabled;
    }

    public void setSslenabled(Boolean sslenabled) {
        this.sslenabled = sslenabled;
    }

    public Boolean getTtlenabled() {
        return ttlenabled;
    }

    public void setTtlenabled(Boolean ttlenabled) {
        this.ttlenabled = ttlenabled;
    }

    public String getImapport() {
        return imapport;
    }

    public void setImapport(String imapport) {
        this.imapport = imapport;
    }

    public String getPopport() {
        return popport;
    }

    public void setPopport(String popport) {
        this.popport = popport;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public short getRef() {
        return ref;
    }

    public void setRef(short ref) {
        this.ref = ref;
    }

   

//    @XmlTransient
//    public Collection<Ssc> getSscCollection() {
//        return sscCollection;
//    }
//
//    public void setSscCollection(Collection<Ssc> sscCollection) {
//        this.sscCollection = sscCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emailPK != null ? emailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Email)) {
            return false;
        }
        Email other = (Email) object;
        if ((this.emailPK == null && other.emailPK != null) || (this.emailPK != null && !this.emailPK.equals(other.emailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mude.srl.ssc.entity.utils.Email[ emailPK=" + emailPK + " ]";
    }
    
}
