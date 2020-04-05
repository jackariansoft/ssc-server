/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jackarian
 */
@Entity
@Table(name = "user_log", catalog = "mag", schema = "public")
public class UserLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 250)
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
    @Size(min = 1, max = 20)
    @Column(name = "connection_from", nullable = false, length = 20)
    private String connectionFrom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "user_agent", nullable = false, length = 300)
    private String userAgent;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @JoinColumn(name = "users", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    @JsonIgnore
    private User users;

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

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
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
        return "servlet.UserLog[ id=" + id + " ]";
    }
    
}
