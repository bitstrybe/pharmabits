/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author JScare
 */
public class ItemInfoController implements Initializable {

    @FXML
    private Button closebtn;
    @FXML
    public ImageView itemimagename;
    @FXML
    public Label iteminfoname;
    @FXML
    public Label iteminfouom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void closefrom(ActionEvent event) {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }
    
}
