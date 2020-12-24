
package lxe.pharmabitretail.tablemodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author JScare
 */
public class ItemTableModel {

    //private SimpleIntegerProperty itemCode;
    private SimpleStringProperty itemCodeName;
    private SimpleStringProperty itemName;
    private SimpleStringProperty category;
    private SimpleStringProperty manufacturer;
    private SimpleStringProperty uom;
    private SimpleIntegerProperty uomitem;
    private SimpleStringProperty vom_val;
    private SimpleLongProperty rol;
    private ImageView image;

    public ItemTableModel() {
    }

    public ItemTableModel(String itemcodename, String itemName, String category, String manufacturer,String uom, int uomitem, String vom_val, long rol, ImageView img) {
       // this.itemCode = new SimpleIntegerProperty(itemCode);
        this.itemCodeName = new SimpleStringProperty(itemcodename);
        this.itemName = new SimpleStringProperty(itemName);
        this.category = new SimpleStringProperty(category);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.uom = new SimpleStringProperty(uom);
        this.uomitem = new SimpleIntegerProperty(uomitem);
        this.vom_val = new SimpleStringProperty(vom_val);
        this.rol = new SimpleLongProperty(rol);
        this.image = img;

    }

//    public Integer getItemCode() {
//        return itemCode.get();
//    }
//
//    public SimpleIntegerProperty getItemCodeProperty() {
//        return itemCode;
//    }
//
//    public void setItemCodeProperty(Integer itemCode) {
//        this.itemCode = new SimpleIntegerProperty(itemCode);
//    }

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
        return uom.get();
    }

    public SimpleStringProperty getUomProperty() {
        return uom;
    }

    public void setUomProperty(String uom) {
        this.uom = new SimpleStringProperty(uom);
    }
    
    public Integer getUomItem() {
        return uomitem.get();
    }

    public SimpleIntegerProperty getUomItemProperty() {
        return uomitem;
    }

    public void setUomItemProperty(Integer uomitem) {
        this.uomitem = new SimpleIntegerProperty(uomitem);
    }


    public String getVmo() {
        return vom_val.get();
    }

    public SimpleStringProperty getVomProperty() {
        return vom_val;
    }

    public void setVomProperty(String vom) {
        this.vom_val = new SimpleStringProperty(vom);
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

    public void setImage(ImageView value) {
        image = value;
    }

    public ImageView getImage() {
        return image;
    }
}
