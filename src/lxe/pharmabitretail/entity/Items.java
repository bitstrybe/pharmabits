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
 * @author scarface
 */
@Entity
@Table(name = "items")
@NamedQueries({
    @NamedQuery(name = "Items.findAll", query = "SELECT i FROM Items i")
    , @NamedQuery(name = "Items.findByItemDesc", query = "SELECT i FROM Items i WHERE i.itemDesc = :itemDesc")
    , @NamedQuery(name = "Items.findByItemName", query = "SELECT i FROM Items i WHERE i.itemName = :itemName")
    , @NamedQuery(name = "Items.findByItemImg", query = "SELECT i FROM Items i WHERE i.itemImg = :itemImg")
    , @NamedQuery(name = "Items.findByVom", query = "SELECT i FROM Items i WHERE i.vom = :vom")
    , @NamedQuery(name = "Items.findByVomDef", query = "SELECT i FROM Items i WHERE i.vomDef = :vomDef")
    , @NamedQuery(name = "Items.findByRol", query = "SELECT i FROM Items i WHERE i.rol = :rol")
    , @NamedQuery(name = "Items.findByEntryLog", query = "SELECT i FROM Items i WHERE i.entryLog = :entryLog")
    , @NamedQuery(name = "Items.findByLastModified", query = "SELECT i FROM Items i WHERE i.lastModified = :lastModified")})
public class Items implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "item_desc", nullable = false, length = 500)
    private String itemDesc;
    @Basic(optional = false)
    @Column(name = "item_name", nullable = false, length = 245)
    private String itemName;
    @Basic(optional = false)
    @Column(name = "item_img", nullable = false, length = 245)
    private String itemImg;
    @Basic(optional = false)
    @Column(name = "vom", nullable = false, length = 45)
    private String vom;
    @Basic(optional = false)
    @Column(name = "vom_def", nullable = false)
    private double vomDef;
    @Basic(optional = false)
    @Column(name = "rol", nullable = false)
    private int rol;
    @Basic(optional = false)
    @Column(name = "entry_log", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryLog;
    @Basic(optional = false)
    @Column(name = "last_modified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "items")
    private Collection<Stockin> stockinCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    private Collection<UomDef> uomDefCollection;
    @JoinColumn(name = "form", referencedColumnName = "form_name", nullable = false)
    @ManyToOne(optional = false)
    private Forms form;
    @JoinColumn(name = "generic", referencedColumnName = "gene_name")
    @ManyToOne
    private Generic generic;
    @JoinColumn(name = "manufacturer", referencedColumnName = "manufacturer", nullable = false)
    @ManyToOne(optional = false)
    private Manufacturer manufacturer;
    @JoinColumn(name = "users", referencedColumnName = "userid", nullable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Items() {
    }

    public Items(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Items(String itemDesc, String itemName, String itemImg, String vom, double vomDef, int rol, Date entryLog, Date lastModified) {
        this.itemDesc = itemDesc;
        this.itemName = itemName;
        this.itemImg = itemImg;
        this.vom = vom;
        this.vomDef = vomDef;
        this.rol = rol;
        this.entryLog = entryLog;
        this.lastModified = lastModified;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getVom() {
        return vom;
    }

    public void setVom(String vom) {
        this.vom = vom;
    }

    public double getVomDef() {
        return vomDef;
    }

    public void setVomDef(double vomDef) {
        this.vomDef = vomDef;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
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

    public Collection<Stockin> getStockinCollection() {
        return stockinCollection;
    }

    public void setStockinCollection(Collection<Stockin> stockinCollection) {
        this.stockinCollection = stockinCollection;
    }

    public Collection<UomDef> getUomDefCollection() {
        return uomDefCollection;
    }

    public void setUomDefCollection(Collection<UomDef> uomDefCollection) {
        this.uomDefCollection = uomDefCollection;
    }

    public Forms getForm() {
        return form;
    }

    public void setForm(Forms form) {
        this.form = form;
    }

    public Generic getGeneric() {
        return generic;
    }

    public void setGeneric(Generic generic) {
        this.generic = generic;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
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
        hash += (itemDesc != null ? itemDesc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Items)) {
            return false;
        }
        Items other = (Items) object;
        if ((this.itemDesc == null && other.itemDesc != null) || (this.itemDesc != null && !this.itemDesc.equals(other.itemDesc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lxe.pharmabitretail.entity.Items[ itemDesc=" + itemDesc + " ]";
    }
    
}
