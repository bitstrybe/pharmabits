/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author JScare
 */
@Entity
@Table(name = "manufacturer")
@NamedQueries({
    @NamedQuery(name = "Manufacturer.findAll", query = "SELECT m FROM Manufacturer m")
    , @NamedQuery(name = "Manufacturer.findByManufacturer", query = "SELECT m FROM Manufacturer m WHERE m.manufacturer = :manufacturer")
    , @NamedQuery(name = "Manufacturer.findByEntryLog", query = "SELECT m FROM Manufacturer m WHERE m.entryLog = :entryLog")
    , @NamedQuery(name = "Manufacturer.findByLastModified", query = "SELECT m FROM Manufacturer m WHERE m.lastModified = :lastModified")})
public class Manufacturer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "manufacturer", nullable = false, length = 245)
    private String manufacturer;
    @Column(name = "entry_log")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryLog;
    @Column(name = "last_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    @JoinColumn(name = "users", referencedColumnName = "userid")
    @ManyToOne
    private Users users;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manufacturer")
    private Collection<Items> itemsCollection;

    public Manufacturer() {
    }

    public Manufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getEntryLog() {
        return entryLog;
    }

    public void setEntryLog(Date entryLog) {
        this.entryLog = entryLog;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Collection<Items> getItemsCollection() {
        return itemsCollection;
    }

    public void setItemsCollection(Collection<Items> itemsCollection) {
        this.itemsCollection = itemsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (manufacturer != null ? manufacturer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manufacturer)) {
            return false;
        }
        Manufacturer other = (Manufacturer) object;
        if ((this.manufacturer == null && other.manufacturer != null) || (this.manufacturer != null && !this.manufacturer.equals(other.manufacturer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lxe.pharmabitretail.entity.Manufacturer[ manufacturer=" + manufacturer + " ]";
    }
    
}
