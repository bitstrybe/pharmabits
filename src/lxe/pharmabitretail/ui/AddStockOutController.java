/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author JScare
 */
public class AddStockOutController implements Initializable {

    @FXML
    public DatePicker stockoutdate;
    @FXML
    public JFXTextField qnttextfield;
    @FXML
    public JFXButton save;
    @FXML
    private Button closeButton;
    @FXML
    public Label displayinfo;
    @FXML
    public JFXSpinner spinner;
    @FXML
    public FontAwesomeIcon check;
    @FXML
    public FontAwesomeIcon duplicatelock;
    @FXML
    public Label itemname;
    @FXML
    public JFXTextArea remarktxtarea;
    @FXML
    public Label batchnoname;
    @FXML
    public AnchorPane stockpane;
    
//    public void getBatchNo(){
//        List<Stockin> st = new StockinBL().getAllStockin();
//        ObservableList<Stockin> list = FXCollections.observableArrayList(st);
//        list.forEach(e->{
//            batchnocombo.getItems().add(e.getBatchNo());
//        });
//    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    

    @FXML
    private void clearall(ActionEvent event) {
    }

    @FXML
    private void closemtd(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
}
