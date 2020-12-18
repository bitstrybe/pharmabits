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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author scarface
 */
@Entity
@Table(name = "uom_def")
@NamedQueries({
    @NamedQuery(name = "UomDef.findAll", query = "SELECT u FROM UomDef u")
    , @NamedQuery(name = "UomDef.findByUomItem", query = "SELECT u FROM UomDef u WHERE u.uomItem = :uomItem")
    , @NamedQuery(name = "UomDef.findByUomValue", query = "SELECT u FROM UomDef u WHERE u.uomValue = :uomValue")})
public class UomDef implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "uom_item", nullable = false)
    private Integer uomItem;
    @Basic(optional = false)
    @Column(name = "uom_value", nullable = false)
    private int uomValue;
    @JoinColumn(name = "item_code", referencedColumnName = "item_desc", nullable = false)
    @ManyToOne(optional = false)
    private Items itemCode;
    @JoinColumn(name = "uom_code", referencedColumnName = "uom_desc", nullable = false)
    @ManyToOne(optional = false)
    private Uom uomCode;

    public UomDef() {
    }

    public UomDef(Integer uomItem) {
        this.uomItem = uomItem;
    }

    public UomDef(Integer uomItem, int uomValue) {
        this.uomItem = uomItem;
        this.uomValue = uomValue;
    }

    public Integer getUomItem() {
        return uomItem;
    }

    public void setUomItem(Integer uomItem) {
        this.uomItem = uomItem;
    }

    public int getUomValue() {
        return uomValue;
    }

    public void setUomValue(int uomValue) {
        this.uomValue = uomValue;
    }

    public Items getItemCode() {
        return itemCode;
    }

    public void setItemCode(Items itemCode) {
        this.itemCode = itemCode;
    }

    public Uom getUomCode() {
        return uomCode;
    }

    public void setUomCode(Uom uomCode) {
        this.uomCode = uomCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uomItem != null ? uomItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UomDef)) {
            return false;
        }
        UomDef other = (UomDef) object;
        if ((this.uomItem == null && other.uomItem != null) || (this.uomItem != null && !this.uomItem.equals(other.uomItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lxe.pharmabitretail.entity.UomDef[ uomItem=" + uomItem + " ]";
    }
    
}
