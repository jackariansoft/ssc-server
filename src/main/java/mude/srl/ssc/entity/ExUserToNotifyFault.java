/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jack
 */
@Entity
@Table(name = "ex_user_to_notify_fault", catalog = "ssc", schema = "public")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExUserToNotifyFault.findAll", query = "SELECT e FROM ExUserToNotifyFault e"),
    @NamedQuery(name = "ExUserToNotifyFault.findByEmail", query = "SELECT e FROM ExUserToNotifyFault e WHERE e.email = :email"),
    @NamedQuery(name = "ExUserToNotifyFault.findByActive", query = "SELECT e FROM ExUserToNotifyFault e WHERE e.active = :active"),
    @NamedQuery(name = "ExUserToNotifyFault.findByChannel", query = "SELECT e FROM ExUserToNotifyFault e WHERE e.channel = :channel"),
    @NamedQuery(name = "ExUserToNotifyFault.findByAllChannel", query = "SELECT e FROM ExUserToNotifyFault e WHERE e.allChannel = :allChannel"),
    @NamedQuery(name = "ExUserToNotifyFault.findByPhone", query = "SELECT e FROM ExUserToNotifyFault e WHERE e.phone = :phone")})
public class ExUserToNotifyFault implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(nullable = false, length = 150)
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean active;
    private BigInteger channel;
    @Column(name = "all_channel")
    private Boolean allChannel;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 30)
    @Column(length = 30)
    private String phone;
    @JoinColumn(name = "plc", referencedColumnName = "id")
    @ManyToOne
    private Plc plc;

    public ExUserToNotifyFault() {
    }

    public ExUserToNotifyFault(String email) {
        this.email = email;
    }

    public ExUserToNotifyFault(String email, boolean active) {
        this.email = email;
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigInteger getChannel() {
        return channel;
    }

    public void setChannel(BigInteger channel) {
        this.channel = channel;
    }

    public Boolean getAllChannel() {
        return allChannel;
    }

    public void setAllChannel(Boolean allChannel) {
        this.allChannel = allChannel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Plc getPlc() {
        return plc;
    }

    public void setPlc(Plc plc) {
        this.plc = plc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExUserToNotifyFault)) {
            return false;
        }
        ExUserToNotifyFault other = (ExUserToNotifyFault) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mude.srl.ssc.entity.utils.ExUserToNotifyFault[ email=" + email + " ]";
    }
    
}
