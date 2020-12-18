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
public class SalesTableModel {

    private SimpleIntegerProperty salesId;
//    private SimpleStringProperty customer;
    private SimpleDoubleProperty totalsales;
    private SimpleDoubleProperty totalpaid;
    private SimpleStringProperty balance;
    private SimpleStringProperty date;
    private SimpleStringProperty user;
    private SimpleStringProperty entrylog;

    public SalesTableModel() {
    }

    public SalesTableModel(Integer salesId,  double totalsales,double totalpaid,String balance, String date, String user,String entrylog) {
        this.salesId = new SimpleIntegerProperty(salesId);
//        this.customer = new SimpleStringProperty(customer);
        this.totalsales = new SimpleDoubleProperty(totalsales);
        this.totalpaid = new SimpleDoubleProperty(totalpaid);
        this.balance = new SimpleStringProperty(balance);
        this.date = new SimpleStringProperty(date);
        this.user = new SimpleStringProperty(user);
        this.entrylog = new SimpleStringProperty(entrylog);
    }

    public Integer getSalesId() {
        return salesId.get();
    }

    public SimpleIntegerProperty getSalesIdProperty() {
        return salesId;
    }

    public void setSalesIdProperty(Integer salesId) {
        this.salesId = new SimpleIntegerProperty(salesId);
    }

//    public String getCustomer() {
//        return customer.get();
//    }
//
//    public SimpleStringProperty getCustomerProperty() {
//        return customer;
//    }
//
//    public void setCustomerProperty(String customer) {
//        this.customer = new SimpleStringProperty(customer);
//    }

   
    public Double getTotalSales() {
        return totalsales.get();
    }

    public SimpleDoubleProperty getTotalSalesProperty() {
        return totalsales;
    }

    public void setTotalSalesProperty(double totalsales) {
        this.totalsales = new SimpleDoubleProperty(totalsales);
    }
    public Double getTotalPaid() {
        return totalpaid.get();
    }

    public SimpleDoubleProperty getTotalPaidProperty() {
        return totalpaid;
    }

    public void setTotalPaidProperty(double totalpaid) {
        this.totalpaid = new SimpleDoubleProperty(totalpaid);
    }
    public String getBalance() {
        return balance.get();
    }

    public SimpleStringProperty getBalanceProperty() {
        return balance;
    }

    public void setBalanceProperty(String balance) {
        this.balance = new SimpleStringProperty(balance);
    }
    
     public String getDate() {
        return date.get();
    }

    public SimpleStringProperty getDateProperty() {
        return date;
    }

    public void setDateProperty(String date) {
        this.date = new SimpleStringProperty(date);
    }
     public String getUser() {
        return user.get();
    }

    public SimpleStringProperty getUserProperty() {
        return user;
    }

    public void setUserProperty(String user) {
        this.user = new SimpleStringProperty(user);
    }
    
      public String getEntryLog() {
        return entrylog.get();
    }

    public SimpleStringProperty getEntryLogProperty() {
        return entrylog;
    }

    public void setEntryLogProperty(String entrylog) {
        this.entrylog = new SimpleStringProperty(entrylog);
    }

}
