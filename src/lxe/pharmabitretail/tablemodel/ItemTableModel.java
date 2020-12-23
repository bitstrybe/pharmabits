/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.tablemodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author JScare
 */
public class ItemTableModel {

    private SimpleStringProperty itemCodeName;
    private SimpleStringProperty itemName;
    private SimpleStringProperty category;
    private SimpleStringProperty manufacturer;
    private SimpleStringProperty uom_val;
    private SimpleLongProperty rol;
    private ImageView itemimage;

    public ItemTableModel() {
    }

    public ItemTableModel(String img, String itemcodename, String itemName, String category, String manufacturer, String uom_val, long rol) {
        this.itemimage = new ImageView(img);
        this.itemCodeName = new SimpleStringProperty(itemcodename);
        this.itemName = new SimpleStringProperty(itemName);
        this.category = new SimpleStringProperty(category);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.uom_val = new SimpleStringProperty(uom_val);
        this.rol = new SimpleLongProperty(rol);
    }

    public String getItemCodeName() {
        return itemCodeName.get();
    }

    public SimpleStringProperty getItemCodeNameProperty() {
        return itemCodeName;
    }

    public void setItemCodeNameProperty(String itemCode) {
        this.itemCodeName = new SimpleStringProperty(itemCode);
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

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty getCategoryProperty() {
        return category;
    }

    public void setCategoryProperty(String category) {
        this.category = new SimpleStringProperty(category);
    }

    public String getManufacturer() {
        return manufacturer.get();
    }

    public SimpleStringProperty getManufacturerProperty() {
        return manufacturer;
    }

    public void setManufacturerProperty(String manufacturer) {
        this.manufacturer = new SimpleStringProperty(manufacturer);
    }

    public String getUmo() {
        return uom_val.get();
    }

    public SimpleStringProperty getUomProperty() {
        return uom_val;
    }

    public void setUomProperty(String uom) {
        this.uom_val = new SimpleStringProperty(uom);
    }

    public Long getRol() {
        return rol.get();
    }

    public SimpleLongProperty getRolProperty() {
        return rol;
    }

    public void setRolProperty(Long uom) {
        this.rol = new SimpleLongProperty(uom);
    }

    /**
     * @return the itemimage
     */
    public ImageView getItemimage() {
        return itemimage;
    }

    /**
     * @param itemimage the itemimage to set
     */
    public void setItemimage(ImageView itemimage) {
        this.itemimage = itemimage;
    }

}
