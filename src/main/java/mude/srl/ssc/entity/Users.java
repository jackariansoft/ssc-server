/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Jack
 */
@Entity
@Table(name = "users", catalog = "ssc", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"login"})})
public class Users implements Serializable,UserDetails {

    @Size(max = 10)
    @Column(name = "username")
    private String username;
    @Size(max = 20)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 20)
    @Column(name = "middle_name")
    private String middleName;
    @Size(max = 20)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @JsonIgnore
    @Size(min = 1, max = 32)
    @Column(name = "password")
    private String password;
    @Size(max = 150)
    @Column(name = "login")
    private String login;
    @Column(name = "access_type")
    private Short accessType;
   

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "lastlog")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastlog;
    @Column(name = "active")
    private Boolean active;
    @ManyToMany(mappedBy = "usersCollection")
    @JsonIgnore
    private Collection<Groups> groupsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    @JsonIgnore
    private Collection<UserLog> userLogCollection;
    @Transient
    private UserLog lastLog;

    public UserLog getLastLog() {
        return lastLog;
    }

    public void setLastLog(UserLog lastLog) {
        this.lastLog = lastLog;
    }
    public Users() {
    }

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Users(Integer userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Date getLastlog() {
        return lastlog;
    }

    public void setLastlog(Date lastlog) {
        this.lastlog = lastlog;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @XmlTransient
    public Collection<Groups> getGroupsCollection() {
        return groupsCollection;
    }

    public void setGroupsCollection(Collection<Groups> groupsCollection) {
        this.groupsCollection = groupsCollection;
    }

    @XmlTransient
    public Collection<UserLog> getUserLogCollection() {
        return userLogCollection;
    }

    public void setUserLogCollection(Collection<UserLog> userLogCollection) {
        this.userLogCollection = userLogCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servlet.Users[ userId=" + userId + " ]";
    }

    public Short getAccessType() {
        return accessType;
    }

    public void setAccessType(Short accessType) {
        this.accessType = accessType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ADMIN"));
        return auth;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

  

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
}
