/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.ui;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lxe.pharmabitretail.tablemodel.SelectItemSaleTableModel;

/**
 * FXML Controller class
 *
 * @author JScare
 */
public class ItemCartController implements Initializable {

    @FXML
    private JFXTextField stocksearch;
    @FXML
    public static TableView<SelectItemSaleTableModel> selection;
    @FXML
    public static TableColumn<SelectItemSaleTableModel, String> batchno;
    @FXML
    public static TableColumn<SelectItemSaleTableModel, String> itemname;
    @FXML
    public static TableColumn<SelectItemSaleTableModel, Number> quantity;
    @FXML
    public static TableColumn<SelectItemSaleTableModel, Number> itemprice;
    @FXML
    public static TableColumn<SelectItemSaleTableModel, Number> nhisprice;
    @FXML
    public static TableColumn<SelectItemSaleTableModel, String> nhis;
    @FXML
    public static TableColumn<SelectItemSaleTableModel, Boolean> discount;
    @FXML
    public static TableColumn<SelectItemSaleTableModel, String> discountcent;
    @FXML
    public static TableColumn<SelectItemSaleTableModel, Boolean> action;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
