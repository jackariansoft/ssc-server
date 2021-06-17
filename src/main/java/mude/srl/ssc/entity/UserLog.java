/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jack
 */
@Entity
@Table(name = "user_log", catalog = "ssc", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserLog.findAll", query = "SELECT u FROM UserLog u"),
    @NamedQuery(name = "UserLog.findByUserComment", query = "SELECT u FROM UserLog u WHERE u.userComment = :userComment"),
    @NamedQuery(name = "UserLog.findByConnectionnStart", query = "SELECT u FROM UserLog u WHERE u.connectionnStart = :connectionnStart"),
    @NamedQuery(name = "UserLog.findByConnectionEnd", query = "SELECT u FROM UserLog u WHERE u.connectionEnd = :connectionEnd"),
    @NamedQuery(name = "UserLog.findByConnectionFrom", query = "SELECT u FROM UserLog u WHERE u.connectionFrom = :connectionFrom"),
    @NamedQuery(name = "UserLog.findByUserAgent", query = "SELECT u FROM UserLog u WHERE u.userAgent = :userAgent"),
    @NamedQuery(name = "UserLog.findById", query = "SELECT u FROM UserLog u WHERE u.id = :id")})
public class UserLog implements Serializable {

    private static final long serialVersionUID = 1L;
     
    @Column(name = "user_comment", length = 250)
    private String userComment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "connectionn_start", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date connectionnStart;
    @Column(name = "connection_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date connectionEnd;
    @Basic(optional = false)
    @NotNull
     
    @Column(name = "connection_from", nullable = false, length = 20)
    private String connectionFrom;
    @Basic(optional = false)
    @NotNull
     
    @Column(name = "user_agent", nullable = false, length = 300)
    private String userAgent;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    @JoinColumn(name = "users", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users users;

    public UserLog() {
    }

    public UserLog(Long id) {
        this.id = id;
    }

    public UserLog(Long id, Date connectionnStart, String connectionFrom, String userAgent) {
        this.id = id;
        this.connectionnStart = connectionnStart;
        this.connectionFrom = connectionFrom;
        this.userAgent = userAgent;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public Date getConnectionnStart() {
        return connectionnStart;
    }

    public void setConnectionnStart(Date connectionnStart) {
        this.connectionnStart = connectionnStart;
    }

    public Date getConnectionEnd() {
        return connectionEnd;
    }

    public void setConnectionEnd(Date connectionEnd) {
        this.connectionEnd = connectionEnd;
    }

    public String getConnectionFrom() {
        return connectionFrom;
    }

    public void setConnectionFrom(String connectionFrom) {
        this.connectionFrom = connectionFrom;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserLog)) {
            return false;
        }
        UserLog other = (UserLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mude.srl.ssc.entity.utils.UserLog[ id=" + id + " ]";
    }
    
}
