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
@Table(name = "sales_details")
@NamedQueries({
    @NamedQuery(name = "SalesDetails.findAll", query = "SELECT s FROM SalesDetails s")
    , @NamedQuery(name = "SalesDetails.findBySalesDetailsId", query = "SELECT s FROM SalesDetails s WHERE s.salesDetailsId = :salesDetailsId")
    , @NamedQuery(name = "SalesDetails.findByBatchNo", query = "SELECT s FROM SalesDetails s WHERE s.batchNo = :batchNo")
    , @NamedQuery(name = "SalesDetails.findByQuantity", query = "SELECT s FROM SalesDetails s WHERE s.quantity = :quantity")
    , @NamedQuery(name = "SalesDetails.findByCostPrice", query = "SELECT s FROM SalesDetails s WHERE s.costPrice = :costPrice")
    , @NamedQuery(name = "SalesDetails.findBySalesPrice", query = "SELECT s FROM SalesDetails s WHERE s.salesPrice = :salesPrice")
    , @NamedQuery(name = "SalesDetails.findByNhisPrice", query = "SELECT s FROM SalesDetails s WHERE s.nhisPrice = :nhisPrice")
    , @NamedQuery(name = "SalesDetails.findByNhisCondition", query = "SELECT s FROM SalesDetails s WHERE s.nhisCondition = :nhisCondition")
    , @NamedQuery(name = "SalesDetails.findByDiscount", query = "SELECT s FROM SalesDetails s WHERE s.discount = :discount")
    , @NamedQuery(name = "SalesDetails.findByEntryDate", query = "SELECT s FROM SalesDetails s WHERE s.entryDate = :entryDate")
    , @NamedQuery(name = "SalesDetails.findByModifiedDate", query = "SELECT s FROM SalesDetails s WHERE s.modifiedDate = :modifiedDate")})
public class SalesDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sales_details_id", nullable = false)
    private Integer salesDetailsId;
    @Basic(optional = false)
    @Column(name = "batch_no", nullable = false, length = 250)
    private String batchNo;
    @Basic(optional = false)
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Basic(optional = false)
    @Column(name = "cost_price", nullable = false)
    private double costPrice;
    @Basic(optional = false)
    @Column(name = "sales_price", nullable = false)
    private double salesPrice;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nhis_price", precision = 17, scale = 0)
    private Double nhisPrice;
    @Basic(optional = false)
    @Column(name = "nhis_condition", nullable = false)
    private int nhisCondition;
    @Basic(optional = false)
    @Column(name = "discount", nullable = false)
    private double discount;
    @Column(name = "entry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "sale_id", referencedColumnName = "sales_id", nullable = false)
    @ManyToOne(optional = false)
    private Sales saleId;
    @JoinColumn(name = "uom_code", referencedColumnName = "uom_desc", nullable = false)
    @ManyToOne(optional = false)
    private Uom uomCode;
    @JoinColumn(name = "users", referencedColumnName = "userid", nullable = false)
    @ManyToOne(optional = false)
    private Users users;

    public SalesDetails() {
    }

    public SalesDetails(Integer salesDetailsId) {
        this.salesDetailsId = salesDetailsId;
    }

    public SalesDetails(Integer salesDetailsId, String batchNo, int quantity, double costPrice, double salesPrice, int nhisCondition, double discount) {
        this.salesDetailsId = salesDetailsId;
        this.batchNo = batchNo;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.salesPrice = salesPrice;
        this.nhisCondition = nhisCondition;
        this.discount = discount;
    }

    public Integer getSalesDetailsId() {
        return salesDetailsId;
    }

    public void setSalesDetailsId(Integer salesDetailsId) {
        this.salesDetailsId = salesDetailsId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Double getNhisPrice() {
        return nhisPrice;
    }

    public void setNhisPrice(Double nhisPrice) {
        this.nhisPrice = nhisPrice;
    }

    public int getNhisCondition() {
        return nhisCondition;
    }

    public void setNhisCondition(int nhisCondition) {
        this.nhisCondition = nhisCondition;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Sales getSaleId() {
        return saleId;
    }

    public void setSaleId(Sales saleId) {
        this.saleId = saleId;
    }

    public Uom getUomCode() {
        return uomCode;
    }

    public void setUomCode(Uom uomCode) {
        this.uomCode = uomCode;
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
        hash += (salesDetailsId != null ? salesDetailsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalesDetails)) {
            return false;
        }
        SalesDetails other = (SalesDetails) object;
        if ((this.salesDetailsId == null && other.salesDetailsId != null) || (this.salesDetailsId != null && !this.salesDetailsId.equals(other.salesDetailsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lxe.pharmabitretail.entity.SalesDetails[ salesDetailsId=" + salesDetailsId + " ]";
    }
    
}
