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

/**
 *
 * @author Jack
 */
@Embeddable
public class UserGroupsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "group_id", nullable = false)
    private int groupId;

    public UserGroupsPK() {
    }

    public UserGroupsPK(int userId, int groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) groupId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserGroupsPK)) {
            return false;
        }
        UserGroupsPK other = (UserGroupsPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.groupId != other.groupId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mude.srl.ssc.entity.utils.UserGroupsPK[ userId=" + userId + ", groupId=" + groupId + " ]";
    }
    
}
