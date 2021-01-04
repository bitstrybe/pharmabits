/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author scarface
 */
@Entity
@Table(name = "generic")
@NamedQueries({
    @NamedQuery(name = "Generic.findAll", query = "SELECT g FROM Generic g")
    , @NamedQuery(name = "Generic.findByGeneName", query = "SELECT g FROM Generic g WHERE g.geneName = :geneName")})
public class Generic implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "gene_name", nullable = false, length = 250)
    private String geneName;
    @OneToMany(mappedBy = "generic")
    private Collection<Items> itemsCollection;

    public Generic() {
    }

    public Generic(String geneName) {
        this.geneName = geneName;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
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
        hash += (geneName != null ? geneName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Generic)) {
            return false;
        }
        Generic other = (Generic) object;
        if ((this.geneName == null && other.geneName != null) || (this.geneName != null && !this.geneName.equals(other.geneName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lxe.pharmabitretail.entity.Generic[ geneName=" + geneName + " ]";
    }
    
}
