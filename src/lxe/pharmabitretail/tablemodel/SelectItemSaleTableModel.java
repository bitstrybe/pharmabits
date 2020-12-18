/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.tablemodel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author JScare
 */
public class SelectItemSaleTableModel {

    private SimpleStringProperty itemName;
    private SimpleStringProperty batchcode;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty cost;
    private SimpleDoubleProperty price;
    private SimpleDoubleProperty total;
    private SimpleDoubleProperty nhisvalprice;
    private SimpleStringProperty nhis;
    private SimpleDoubleProperty discountval;

    public SelectItemSaleTableModel() {
    }

    public SelectItemSaleTableModel(String itemName, String batchcode, int quantity,double cost, double price, double total, double nhisvalprice, String nhis, double discountval) {
        this.itemName = new SimpleStringProperty(itemName);
        this.batchcode = new SimpleStringProperty(batchcode);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.cost = new SimpleDoubleProperty(cost);
        this.price = new SimpleDoubleProperty(price);
        this.total = new SimpleDoubleProperty(total);
        this.nhisvalprice = new SimpleDoubleProperty(nhisvalprice);
        this.nhis = new SimpleStringProperty(nhis);
        this.discountval = new SimpleDoubleProperty(discountval);
    }

    public String getItemName() {
        return itemName.get();
    }

    public SimpleStringProperty getItemNameProperty() {
        return itemName;
    }

    public void setItemNameProperty(String itemName) {
        this.itemName = new SimpleStringProperty(itemName);
    }
     public String getBatchCode() {
        return batchcode.get();
    }

    public SimpleStringProperty getBatchCodeProperty() {
        return batchcode;
    }

    public void setBatchCodeProperty(String batchcode) {
        this.batchcode = new SimpleStringProperty(batchcode);
    }
    

    public Integer getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty getQuantityProperty() {
        return quantity;
    }

    public void setQuantityProperty(int quantity) {
        this.quantity = new SimpleIntegerProperty(quantity);
    }

     public double getCost() {
        return cost.get();
    }

    public SimpleDoubleProperty getCostProperty() {
        return cost;
    }

    public void setCostProperty(double cost) {
        this.cost = new SimpleDoubleProperty(cost);
    }
    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty getPriceProperty() {
        return price;
    }

    public void setPriceProperty(double price) {
        this.price = new SimpleDoubleProperty(price);
    }

    public double getTotal() {
        return total.get();
    }

    public SimpleDoubleProperty getTotalProperty() {
        return total;
    }

    public void setTotalProperty(double total) {
        this.total = new SimpleDoubleProperty(total);
    }
     public Double getNHISPrice() {
        return nhisvalprice.get();
    }

    public SimpleDoubleProperty getNHISPriceProperty() {
        return nhisvalprice;
    }

    public void setNHISPriceProperty(Double nhisvalprice) {
        this.nhisvalprice = new SimpleDoubleProperty(nhisvalprice);
    }
    public String getNHIS() {
        return nhis.get();
    }

    public SimpleStringProperty getNHISProperty() {
        return nhis;
    }

    public void setNHISProperty(String nhis) {
        this.nhis = new SimpleStringProperty(nhis);
    }
     public Double getDiscountValue() {
        return discountval.get();
    }

    public SimpleDoubleProperty getDiscountValueProperty() {
        return discountval;
    }

    public void setDiscountValueProperty(Double discountval) {
        this.discountval = new SimpleDoubleProperty(discountval);
    }

}
