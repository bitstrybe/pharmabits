/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author scarface
 */
@Entity
@Table(name = "business")
@NamedQueries({
    @NamedQuery(name = "Business.findAll", query = "SELECT b FROM Business b")
    , @NamedQuery(name = "Business.findByBName", query = "SELECT b FROM Business b WHERE b.bName = :bName")
    , @NamedQuery(name = "Business.findByBAddr", query = "SELECT b FROM Business b WHERE b.bAddr = :bAddr")
    , @NamedQuery(name = "Business.findByBMobile", query = "SELECT b FROM Business b WHERE b.bMobile = :bMobile")
    , @NamedQuery(name = "Business.findByBLogo", query = "SELECT b FROM Business b WHERE b.bLogo = :bLogo")})
public class Business implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "b_name", nullable = false, length = 145)
    private String bName;
    @Basic(optional = false)
    @Column(name = "b_addr", nullable = false, length = 45)
    private String bAddr;
    @Basic(optional = false)
    @Column(name = "b_mobile", nullable = false, length = 45)
    private String bMobile;
    @Column(name = "b_logo", length = 245)
    private String bLogo;

    public Business() {
    }

    public Business(String bName) {
        this.bName = bName;
    }

    public Business(String bName, String bAddr, String bMobile) {
        this.bName = bName;
        this.bAddr = bAddr;
        this.bMobile = bMobile;
    }

    public String getBName() {
        return bName;
    }

    public void setBName(String bName) {
        this.bName = bName;
    }

    public String getBAddr() {
        return bAddr;
    }

    public void setBAddr(String bAddr) {
        this.bAddr = bAddr;
    }

    public String getBMobile() {
        return bMobile;
    }

    public void setBMobile(String bMobile) {
        this.bMobile = bMobile;
    }

    public String getBLogo() {
        return bLogo;
    }

    public void setBLogo(String bLogo) {
        this.bLogo = bLogo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bName != null ? bName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Business)) {
            return false;
        }
        Business other = (Business) object;
        if ((this.bName == null && other.bName != null) || (this.bName != null && !this.bName.equals(other.bName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lxe.pharmabitretail.entity.Business[ bName=" + bName + " ]";
    }
    
}
