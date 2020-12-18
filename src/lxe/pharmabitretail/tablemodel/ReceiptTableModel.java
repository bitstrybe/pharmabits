/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.tablemodel;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleDoubleProperty;
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
public class ReceiptTableModel implements Serializable {

    private SimpleIntegerProperty receiptId;
    private SimpleIntegerProperty salesId;
    private SimpleDoubleProperty amountPaid;
    private SimpleStringProperty date;

    public ReceiptTableModel() {
    }

    
    public ReceiptTableModel(Integer receiptId,Integer salesid, double amountPaid, String date) {
        this.receiptId = new SimpleIntegerProperty(receiptId);
        this.salesId = new SimpleIntegerProperty(salesid);
        this.amountPaid = new SimpleDoubleProperty(amountPaid);
        this.date = new SimpleStringProperty(date);
    }

    public Integer getReceiptId() {
        return receiptId.get();
    }
    public SimpleIntegerProperty getReceiptIdProperty() {
        return receiptId;
    }

    public void setReceiptIdProperty(Integer receiptId) {
        this.receiptId = new SimpleIntegerProperty(receiptId);
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

    public double getAmountPaid() {
        return amountPaid.get();
    }
    public SimpleDoubleProperty getAmountPaidProperty() {
        return amountPaid;
    }

    public void setAmountPaidProperty(double amountPaid) {
        this.amountPaid =new SimpleDoubleProperty(amountPaid);
    }

    public String getDate() {
        return date.get();
    }
    public SimpleStringProperty getDateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }


}
