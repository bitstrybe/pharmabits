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
@Table(name = "sales")
@NamedQueries({
    @NamedQuery(name = "Sales.findAll", query = "SELECT s FROM Sales s")
    , @NamedQuery(name = "Sales.findBySalesId", query = "SELECT s FROM Sales s WHERE s.salesId = :salesId")
    , @NamedQuery(name = "Sales.findByDateS", query = "SELECT s FROM Sales s WHERE s.dateS = :dateS")
    , @NamedQuery(name = "Sales.findByEntryDate", query = "SELECT s FROM Sales s WHERE s.entryDate = :entryDate")})
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sales_id", nullable = false)
    private Integer salesId;
    @Basic(optional = false)
    @Column(name = "dateS", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateS;
    @Basic(optional = false)
    @Column(name = "entry_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date entryDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "saleId")
    private Collection<SalesDetails> salesDetailsCollection;
    @JoinColumn(name = "customers", referencedColumnName = "customer_id", nullable = false)
    @ManyToOne(optional = false)
    private Customers customers;
    @JoinColumn(name = "users", referencedColumnName = "userid", nullable = false)
    @ManyToOne(optional = false)
    private Users users;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salesId")
    private Collection<Receipt> receiptCollection;

    public Sales() {
    }

    public Sales(Integer salesId) {
        this.salesId = salesId;
    }

    public Sales(Integer salesId, Date dateS, Date entryDate) {
        this.salesId = salesId;
        this.dateS = dateS;
        this.entryDate = entryDate;
    }

    public Integer getSalesId() {
        return salesId;
    }

    public void setSalesId(Integer salesId) {
        this.salesId = salesId;
    }

    public Date getDateS() {
        return dateS;
    }

    public void setDateS(Date dateS) {
        this.dateS = dateS;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Collection<SalesDetails> getSalesDetailsCollection() {
        return salesDetailsCollection;
    }

    public void setSalesDetailsCollection(Collection<SalesDetails> salesDetailsCollection) {
        this.salesDetailsCollection = salesDetailsCollection;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Collection<Receipt> getReceiptCollection() {
        return receiptCollection;
    }

    public void setReceiptCollection(Collection<Receipt> receiptCollection) {
        this.receiptCollection = receiptCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (salesId != null ? salesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sales)) {
            return false;
        }
        Sales other = (Sales) object;
        if ((this.salesId == null && other.salesId != null) || (this.salesId != null && !this.salesId.equals(other.salesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lxe.pharmabitretail.entity.Sales[ salesId=" + salesId + " ]";
    }
    
}
