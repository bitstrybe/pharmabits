/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.tablemodel;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author JScare
 */
public class StockTableModel {

//    private Integer stockinId;
    private SimpleStringProperty batchNo;
    private SimpleStringProperty items;
    private SimpleLongProperty stkinqty;
    private SimpleLongProperty stkoutqty;
    private SimpleLongProperty salesqty;
    private SimpleLongProperty stkbal;
    private SimpleDoubleProperty stkcostprice;
    private SimpleDoubleProperty stksalesprice;
    private SimpleDoubleProperty nhisprice;

    public StockTableModel() {
    }

    public StockTableModel(String batchNo, String items, long stkinqty, long stkoutqty, long salesqty, long stkbal, double stkcostprice, double stksalesprice, double nhisprice) {
        this.batchNo = new SimpleStringProperty(batchNo);
        this.items = new SimpleStringProperty(items);
        this.stkinqty = new SimpleLongProperty(stkinqty);
        this.stkoutqty = new SimpleLongProperty(stkoutqty);
        this.salesqty = new SimpleLongProperty(salesqty);
        this.stkbal = new SimpleLongProperty(stkbal);
        this.stkcostprice = new SimpleDoubleProperty(stkcostprice);
        this.stksalesprice = new SimpleDoubleProperty(stksalesprice);
        this.nhisprice = new SimpleDoubleProperty(nhisprice);
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

    public String getItems() {
        return items.get();
    }

    public SimpleStringProperty getItemsProperty() {
        return items;
    }

    public void setItemsProperty(String items) {
        this.items = new SimpleStringProperty(items);
    }

    public long getStockinQty() {
        return stkinqty.get();
    }

    public SimpleLongProperty getStockinQtyProperty() {
        return stkinqty;
    }

    public void setStockinQtyProperty(long stkinqty) {
        this.stkinqty = new SimpleLongProperty(stkinqty);
    }

    public long getStockoutQty() {
        return stkoutqty.get();
    }

    public SimpleLongProperty getStockoutQtyProperty() {
        return stkoutqty;
    }

    public void setStockoutQtyProperty(long stkoutqty) {
        this.stkoutqty = new SimpleLongProperty(stkoutqty);
    }

    public long getSalesQty() {
        return salesqty.get();
    }

    public SimpleLongProperty getSalesQtyProperty() {
        return salesqty;
    }

    public void setSalesQtyProperty(long salesqty) {
        this.salesqty = new SimpleLongProperty(salesqty);
    }

    public long getStockbal() {
        return stkbal.get();
    }

    public SimpleLongProperty getStockbalProperty() {
        return stkbal;
    }

    public void setStockbalProperty(int stkbal) {
        this.stkbal = new SimpleLongProperty(stkbal);
    }

    public double getStockCostPrice() {
        return stkcostprice.get();
    }

    public SimpleDoubleProperty getStockCostPriceProperty() {
        return stkcostprice;
    }

    public void setStockCostPriceProperty(double stkcostprice) {
        this.stkcostprice = new SimpleDoubleProperty(stkcostprice);
    }

    public double getStockSalesPrice() {
        return stksalesprice.get();
    }

    public SimpleDoubleProperty getStockSalesPriceProperty() {
        return stksalesprice;
    }

    public void setStockSalesPriceProperty(double stksalesprice) {
        this.stksalesprice = new SimpleDoubleProperty(stksalesprice);
    }
    
     public double getNhisPrice() {
        return nhisprice.get();
    }

    public SimpleDoubleProperty getNhisPriceProperty() {
        return nhisprice;
    }

    public void setNhisPriceProperty(double nhisprice) {
        this.nhisprice = new SimpleDoubleProperty(nhisprice);
    }

}
