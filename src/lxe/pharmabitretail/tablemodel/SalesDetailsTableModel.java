/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.tablemodel;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
public class SalesDetailsTableModel implements Serializable {

    private SimpleIntegerProperty salesDetailsId;
    private SimpleIntegerProperty saleId;
    private SimpleStringProperty batchNo;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty salesPrice;
    private SimpleDoubleProperty nhisprice;
    private SimpleStringProperty nhisstatus;
    private SimpleDoubleProperty discountsalesdetailstb;

    public SalesDetailsTableModel() {
    }

    public SalesDetailsTableModel(Integer salesDetailsId, Integer saleId, String batchNo, Integer quantity, double SalesPrice, Double nhisprice, String nhisstatus, double discountsalesdetailstb) {
        this.salesDetailsId = new SimpleIntegerProperty(salesDetailsId);
        this.saleId = new SimpleIntegerProperty(saleId);
        this.batchNo = new SimpleStringProperty(batchNo);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.salesPrice = new SimpleDoubleProperty(SalesPrice);
        this.nhisprice = new SimpleDoubleProperty(nhisprice);
        this.nhisstatus = new SimpleStringProperty(nhisstatus);
        this.discountsalesdetailstb = new SimpleDoubleProperty(discountsalesdetailstb);
    }

    public Integer getSalesDetailsId() {
        return salesDetailsId.get();
    }

    public SimpleIntegerProperty getSalesDetailsIdProperty() {
        return salesDetailsId;
    }

    public void setSalesDetailsIdProperty(Integer salesDetailsId) {
        this.salesDetailsId = new SimpleIntegerProperty(salesDetailsId);
    }

    public Integer getSalesId() {
        return saleId.get();
    }

    public SimpleIntegerProperty getSalesIdProperty() {
        return saleId;
    }

    public void setSalesIdProperty(Integer salesId) {
        this.saleId = new SimpleIntegerProperty(salesId);
    }

    public String getBatchNo() {
        return batchNo.get();
    }

    public SimpleStringProperty getBatchNoProperty() {
        return batchNo;
    }

    public void setBatchNoProperty(String batchNo) {
        this.batchNo = new SimpleStringProperty(batchNo);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty getQuantityProperty() {
        return quantity;
    }

    public void setQuantityProperty(int quantity) {
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public double getSalesPrice() {
        return salesPrice.get();
    }

    public SimpleDoubleProperty getSalesPriceProperty() {
        return salesPrice;
    }

    public void setSalesPriceProperty(double salesPrice) {
        this.salesPrice = new SimpleDoubleProperty(salesPrice);
    }
    public double getNHISPrice() {
        return nhisprice.get();
    }

    public SimpleDoubleProperty getNHISPriceProperty() {
        return nhisprice;
    }

    public void setNHISPriceProperty(double nhisprice) {
        this.nhisprice = new SimpleDoubleProperty(nhisprice);
    }
     public String getNHISStatus() {
        return nhisstatus.get();
    }

    public SimpleStringProperty getNHISStatusProperty() {
        return nhisstatus;
    }

    public void setNHISStatusProperty(String nhisstatus) {
        this.nhisstatus = new SimpleStringProperty(nhisstatus);
    }
    
     public double getDiscount() {
        return discountsalesdetailstb.get();
    }

    public SimpleDoubleProperty getDiscountProperty() {
        return discountsalesdetailstb;
    }

    public void setDoubleProperty(double discountsalesdetailstb) {
        this.discountsalesdetailstb = new SimpleDoubleProperty(discountsalesdetailstb);
    }

}
