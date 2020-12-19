/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

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
    @FXML
    private JFXButton closebtn;

 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    @FXML
    public void closefrom() {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

}
