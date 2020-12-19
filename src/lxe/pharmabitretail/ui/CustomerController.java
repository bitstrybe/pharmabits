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

/**
 * FXML Controller class
 *
 * @author JScare
 */
public class CustomerController implements Initializable {

    @FXML
    private JFXTextField custextfield;
    @FXML
    private JFXTextField cusphone;
    @FXML
    private JFXTextField searchbtn;
    @FXML
    private TableView<?> customertbview;
    @FXML
    private TableColumn<?, ?> custbname;
    @FXML
    private TableColumn<?, ?> custbphone;
    @FXML
    private TableColumn<?, ?> action;

 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
