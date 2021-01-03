
package lxe.pharmabitretail.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lxe.pharmabitretail.bl.ItemsBL;
import lxe.pharmabitretail.bl.UomBL;
import lxe.pharmabitretail.entity.Items;
import lxe.pharmabitretail.entity.UomDef;
import org.apache.commons.io.FilenameUtils;

/**
 * FXML Controller class
 *
 * @author JScare
 */
public class AddStockInController implements Initializable {

    @FXML
    public JFXTextField batchtextfield;
    @FXML
    public JFXTextField qnttextfield;
    @FXML
    public JFXTextField costtextfield;
    @FXML
    public JFXTextField salestextfield;
    @FXML
    public DatePicker expirydate;
    @FXML
    public JFXButton save;
    @FXML
    public Label displayinfo;
    @FXML
    public JFXSpinner spinner;
    @FXML
    public FontAwesomeIcon check;
    @FXML
    private FontAwesomeIcon duplicatelock;
    public JFXTextField costpiecestextfield;
    public JFXTextField salespiecetextfield;
    @FXML
    public Label itemname;
    @FXML
    public JFXListView<String> itemlist;
    @FXML
    private JFXTextField search;
    @FXML
    public JFXTextField nhistextfield;
    public JFXTextField nhispiecetextfield;
    @FXML
    private Button closebtn;
    @FXML
    private ImageView itemimage;
    @FXML
    private Label uomitem;

    public void getItemList() {
        List<String> item = new ItemsBL().getAllItemsName();
        ObservableList<String> result = FXCollections.observableArrayList(item);
        itemlist.getItems().clear();
        itemlist.setItems(result);
//        Utilities.searchListView(itemlist.getItems(), search, itemlist);
    }

    public void searchItemList(String p) {
        List<String> item = new ItemsBL().searchItemsNames(p);
        ObservableList<String> result = FXCollections.observableArrayList(item);
        itemlist.getItems().clear();
        itemlist.setItems(result);
//        Utilities.searchListView(itemlist.getItems(), search, itemlist);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getItemList();
        search.textProperty().addListener(e -> {
            if (search.getText().length() > 4) {
                searchItemList(search.getText().toUpperCase());
            } else {
                getItemList();
            }
        });

        itemlist.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            itemname.setText(itemlist.getSelectionModel().getSelectedItem());
            UomDef ud = new UomBL().getUombyItemId(itemlist.getSelectionModel().getSelectedItem());
            Items its = new ItemsBL().getImageItembyCode(itemlist.getSelectionModel().getSelectedItem());
            uomitem.setText(ud.getUomCode().getUomDesc() + " " + ud.getUomNm() + " X " + ud.getUomDm());
            FileInputStream input;
            try {
                input = new FileInputStream(its.getItemImg());
                Image image = new Image(input);
                itemimage.setImage(image);
                save.setDisable(false);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AddStockInController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        costtextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    costtextfield.setText(newValue.replaceAll("[^\\d\\.]", ""));
                }
            }
        });
        salestextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    salestextfield.setText(newValue.replaceAll("[^\\d\\.]", ""));
                }
            }
        });
        qnttextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    qnttextfield.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
//        costtextfield.textProperty().addListener(e -> {
//            try {
//                if (costtextfield.getLength() >= 1) {
//                    float val = Float.parseFloat(costtextfield.getText()) / Float.parseFloat(qnttextfield.getText());
//                    costpiecestextfield.setText(String.valueOf(val));
//                }
//            } catch (NumberFormatException ex) {
//                System.out.println("formatting error");
//            }
//
//        });
//        salestextfield.textProperty().addListener(e -> {
//            try {
//                if (salestextfield.getLength() >= 1) {
//                    float val = Float.parseFloat(salestextfield.getText()) / Float.parseFloat(qnttextfield.getText());
//                    salespiecetextfield.setText(String.valueOf(val));
//                }
//            } catch (NumberFormatException ex) {
//                System.out.println("formatting error");
//            }
//        });
//        nhistextfield.textProperty().addListener(e -> {
//            try {
//                if (nhistextfield.getLength() >= 1) {
//                    float val = Float.parseFloat(nhistextfield.getText()) * Float.parseFloat(qnttextfield.getText());
//                    nhispiecetextfield.setText(String.valueOf(val));
//                }
//            } catch (NumberFormatException ex) {
//                System.out.println("formatting error");
//            }
//        });
        qnttextfield.textProperty().addListener(e -> {
            try {
                if (qnttextfield.getText().length() >= 1) {
                    float valcost = Float.parseFloat(costtextfield.getText()) * Float.parseFloat(qnttextfield.getText());
                    float valsales = Float.parseFloat(salestextfield.getText()) * Float.parseFloat(qnttextfield.getText());
                    float valnhis = Float.parseFloat(nhistextfield.getText()) * Float.parseFloat(qnttextfield.getText());
                    costpiecestextfield.setText(String.valueOf(valcost));
                    salespiecetextfield.setText(String.valueOf(valsales));
                    nhispiecetextfield.setText(String.valueOf(valnhis));
                }
            } catch (Exception ex) {
                System.out.println("formatting error");
            }

        });

        expirydate.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate object) {
                if (object == null) {
                    return "";
                }
                return dateTimeFormatter.format(object);
            }

            @Override
            public LocalDate fromString(String datestring) {
                if (datestring == null || datestring.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(datestring, dateTimeFormatter);
            }

        });
    }

    @FXML
    private void closefrom(ActionEvent event) {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveAction(ActionEvent event) {
    }

}
