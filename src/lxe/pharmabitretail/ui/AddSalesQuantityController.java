/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.ui;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author JScare
 */
public class AddSalesQuantityController implements Initializable {

    public Label itemname;
    public Label stockval;
    public TextField qnttextfield;
    @FXML
    public Button closebtn;
    public JFXButton additem;
    @FXML
    private Button plusButton;
    @FXML
    private Button minusButton;
    
    @FXML
    public Button addtocartbtn;

    
    AtomicInteger rowCounter = new AtomicInteger(0);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        plusButton.setOnAction(e -> {
            qnttextfield.setText(Integer.toString(rowCounter.incrementAndGet()));
        });
        minusButton.setOnAction(e -> {
            qnttextfield.setText(Integer.toString(rowCounter.decrementAndGet()));
        });
    }

    @FXML
    private void closeForm(ActionEvent event) {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

}
