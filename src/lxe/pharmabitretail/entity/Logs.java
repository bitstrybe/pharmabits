/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.entity;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author JScare
 */
@Entity
@Table(name = "logs")
@NamedQueries({
    @NamedQuery(name = "Logs.findAll", query = "SELECT l FROM Logs l")
    , @NamedQuery(name = "Logs.findByLogsid", query = "SELECT l FROM Logs l WHERE l.logsid = :logsid")
    , @NamedQuery(name = "Logs.findByLoginDatetime", query = "SELECT l FROM Logs l WHERE l.loginDatetime = :loginDatetime")
    , @NamedQuery(name = "Logs.findByLogoutDatetime", query = "SELECT l FROM Logs l WHERE l.logoutDatetime = :logoutDatetime")})
public class Logs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "logsid", nullable = false)
    private Integer logsid;
    @Column(name = "login_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginDatetime;
    @Column(name = "logout_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logoutDatetime;
    @JoinColumn(name = "username", referencedColumnName = "userid", nullable = false)
    @ManyToOne(optional = false)
    private Users username;

    public Logs() {
    }

    public Logs(Integer logsid) {
        this.logsid = logsid;
    }

    public Integer getLogsid() {
        return logsid;
    }

    public void setLogsid(Integer logsid) {
        this.logsid = logsid;
    }

    public Date getLoginDatetime() {
        return loginDatetime;
    }

    public void setLoginDatetime(Date loginDatetime) {
        this.loginDatetime = loginDatetime;
    }

    public Date getLogoutDatetime() {
        return logoutDatetime;
    }

    public void setLogoutDatetime(Date logoutDatetime) {
        this.logoutDatetime = logoutDatetime;
    }

    public Users getUsername() {
        return username;
    }

    public void setUsername(Users username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logsid != null ? logsid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logs)) {
            return false;
        }
        Logs other = (Logs) object;
        if ((this.logsid == null && other.logsid != null) || (this.logsid != null && !this.logsid.equals(other.logsid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lxe.pharmabitretail.entity.Logs[ logsid=" + logsid + " ]";
    }
    
}
