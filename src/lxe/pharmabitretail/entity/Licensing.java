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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author JScare
 */
@Entity
@Table(name = "licensing")
@NamedQueries({
    @NamedQuery(name = "Licensing.findAll", query = "SELECT l FROM Licensing l")
    , @NamedQuery(name = "Licensing.findByLicenseId", query = "SELECT l FROM Licensing l WHERE l.licenseId = :licenseId")
    , @NamedQuery(name = "Licensing.findByLicenseTo", query = "SELECT l FROM Licensing l WHERE l.licenseTo = :licenseTo")
    , @NamedQuery(name = "Licensing.findByLicensingAddr", query = "SELECT l FROM Licensing l WHERE l.licensingAddr = :licensingAddr")
    , @NamedQuery(name = "Licensing.findByLicensingSp", query = "SELECT l FROM Licensing l WHERE l.licensingSp = :licensingSp")
    , @NamedQuery(name = "Licensing.findByLicensingEp", query = "SELECT l FROM Licensing l WHERE l.licensingEp = :licensingEp")
    , @NamedQuery(name = "Licensing.findByLicenseType", query = "SELECT l FROM Licensing l WHERE l.licenseType = :licenseType")})
public class Licensing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "license_id", nullable = false)
    private Integer licenseId;
    @Basic(optional = false)
    @Column(name = "license_to", nullable = false, length = 145)
    private String licenseTo;
    @Basic(optional = false)
    @Column(name = "licensing_addr", nullable = false, length = 45)
    private String licensingAddr;
    @Basic(optional = false)
    @Column(name = "licensing_sp", nullable = false, length = 145)
    private String licensingSp;
    @Basic(optional = false)
    @Column(name = "licensing_ep", nullable = false, length = 145)
    private String licensingEp;
    @Column(name = "license_type", length = 45)
    private String licenseType;

    public Licensing() {
    }

    public Licensing(Integer licenseId) {
        this.licenseId = licenseId;
    }

    public Licensing(Integer licenseId, String licenseTo, String licensingAddr, String licensingSp, String licensingEp) {
        this.licenseId = licenseId;
        this.licenseTo = licenseTo;
        this.licensingAddr = licensingAddr;
        this.licensingSp = licensingSp;
        this.licensingEp = licensingEp;
    }

    public Integer getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Integer licenseId) {
        this.licenseId = licenseId;
    }

    public String getLicenseTo() {
        return licenseTo;
    }

    public void setLicenseTo(String licenseTo) {
        this.licenseTo = licenseTo;
    }

    public String getLicensingAddr() {
        return licensingAddr;
    }

    public void setLicensingAddr(String licensingAddr) {
        this.licensingAddr = licensingAddr;
    }

    public String getLicensingSp() {
        return licensingSp;
    }

    public void setLicensingSp(String licensingSp) {
        this.licensingSp = licensingSp;
    }

    public String getLicensingEp() {
        return licensingEp;
    }

    public void setLicensingEp(String licensingEp) {
        this.licensingEp = licensingEp;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (licenseId != null ? licenseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Licensing)) {
            return false;
        }
        Licensing other = (Licensing) object;
        if ((this.licenseId == null && other.licenseId != null) || (this.licenseId != null && !this.licenseId.equals(other.licenseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lxe.pharmabitretail.entity.Licensing[ licenseId=" + licenseId + " ]";
    }
    
}
