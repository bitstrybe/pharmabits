
package lxe.pharmabitretail.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import lxe.pharmabitretail.bl.InsertUpdateBL;
import lxe.pharmabitretail.bl.ItemsBL;
import lxe.pharmabitretail.bl.UomBL;
import lxe.pharmabitretail.entity.Uom;
import lxe.pharmabitretail.entity.Users;
import lxe.pharmabitretail.tablemodel.UomTableModel;

/**
 * FXML Controller class
 *
 * @author JScare
 */
public class AddUomController implements Initializable {

    @FXML
    private JFXButton closebtn;
    @FXML
    private JFXTextField uomtextfield;
    @FXML
    private JFXButton save;
    @FXML
    private Label displayinfo;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private FontAwesomeIcon check;
    @FXML
    private FontAwesomeIcon duplicatelock;
    @FXML
    private JFXTextField searchbtn;
    @FXML
    private TableView<UomTableModel> cattableview;
    @FXML
    private TableColumn<UomTableModel, String> uomtb;
    @FXML
    private TableColumn<UomTableModel, Boolean> action;
    
     ObservableList<UomTableModel> data;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        UomBL ca = new UomBL();

        uomtextfield.textProperty().addListener(e -> {
            //  System.out.println(cattextfield.getText());
            check.setVisible(false);
            if (uomtextfield.getLength() > 0) {
                save.setDisable(false);
                String value = ca.getUomById(uomtextfield.getText());
                if (value != null) {
                    save.setDisable(true);
                    displayinfo.setText("DUPLICATE FORUND!!!");
                    duplicatelock.setVisible(true);
                } else if (value == null) {
                    save.setDisable(false);
                    displayinfo.setText(null);
                    duplicatelock.setVisible(false);
                }
            } else {
                save.setDisable(true);
            }

        });
        TableData();

        searchbtn.textProperty().addListener(e -> {
            if (searchbtn.getText().length() > 1) {
                TableData(searchbtn.getText());
            } else {
                TableData();
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

        System.out.println("testing addUOM");
        save.setDisable(true);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                spinner.setVisible(true);
                check.setVisible(false);
                updateMessage("PROCESSING PLS WAIT.....");
                Thread.sleep(1000);
                return null;
            }
        };
        displayinfo.textProperty().bind(task.messageProperty());
        task.setOnSucceeded(s -> {
            displayinfo.textProperty().unbind();
            Uom cat = new Uom();
            cat.setUomDesc(uomtextfield.getText());
            int result = new InsertUpdateBL().insertData(cat);
            switch (result) {
                case 1:
                    save.setDisable(true);
                    displayinfo.setText("SUCCESSFULLY SAVED");
                    uomtextfield.clear();
                    spinner.setVisible(false);
                    check.setVisible(true);
                    TableData();
                    break;
                default:
                    displayinfo.setText("NOTICE! AN ERROR OCCURED");
                    spinner.setVisible(false);
                    check.setVisible(false);
                    break;

            }
        });
        Thread d = new Thread(task);
        d.setDaemon(true);
        d.start();

    }
    public void TableData() {
        List<Uom> c = new UomBL().getAllUom();
        data = FXCollections.observableArrayList();
        c.forEach((template) -> {
            data.add(new UomTableModel(template.getUomDesc()));
        });
        uomtb.setCellValueFactory(cell -> cell.getValue().getUomNameProperty());
        action.setSortable(false);
        action.setMaxWidth(120);

        action.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UomTableModel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<UomTableModel, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        action.setCellFactory(new Callback<TableColumn<UomTableModel, Boolean>, TableCell<UomTableModel, Boolean>>() {
            @Override
            public TableCell<UomTableModel, Boolean> call(TableColumn<UomTableModel, Boolean> personBooleanTableColumn) {
                return new AddPersonCell();
            }
        });
        cattableview.setItems(data);
        cattableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }
    
    
    public void TableData(String value) {
        List<Uom> c = new UomBL().searchAllUom(value);
        data = FXCollections.observableArrayList();
        c.forEach((template) -> {
            data.add(new UomTableModel(template.getUomDesc()));
        });
        uomtb.setCellValueFactory(cell -> cell.getValue().getUomNameProperty());
        action.setSortable(false);
        action.setMaxWidth(120);

        action.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UomTableModel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<UomTableModel, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        action.setCellFactory(new Callback<TableColumn<UomTableModel, Boolean>, TableCell<UomTableModel, Boolean>>() {
            @Override
            public TableCell<UomTableModel, Boolean> call(TableColumn<UomTableModel, Boolean> personBooleanTableColumn) {
                return new AddPersonCell();
            }
        });
        cattableview.setItems(data);
        cattableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }
    
    
     public class AddPersonCell extends TableCell<UomTableModel, Boolean> {

        //Image img = new Image(getClass().getResourceAsStream("edit.png"));
        Image img2 = new Image(getClass().getResourceAsStream("delete.png"));

        // a button for adding a new person.
        JFXButton addButton = new JFXButton();

        // pads and centers the add button in the cell.
        HBox paddedButton = new HBox();

        JFXButton delButton = new JFXButton();
        // records the y pos of the last button press so that the add person dialog can be shown next to the cell.
        final DoubleProperty buttonY = new SimpleDoubleProperty();

        /**
         * AddPersonCell constructor
         *
         * @param stage the stage in which the table is placed.
         * @param table the table to which a new person can be added.
         */
        AddPersonCell() {
            paddedButton.setStyle("-fx-alignment: CENTER;");
            paddedButton.getChildren().add(delButton);
            delButton.setGraphic(new ImageView(img2));
            delButton.setRipplerFill(Paint.valueOf("#D8E1DC"));
            delButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override

                public void handle(ActionEvent event) {

                    try {

                        int selectdIndex = getTableRow().getIndex();
                        //Create a new table show details of the selected item
                        UomTableModel selectedRecord = (UomTableModel) cattableview.getItems().get(selectdIndex);
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Delete.fxml"));
                        Parent parent = (Parent) fxmlLoader.load();
                        DeleteController childController = fxmlLoader.getController();
                        childController.delete.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                            Task<Void> task = new Task<Void>() {
                                @Override
                                protected Void call() throws Exception {
                                    childController.spinner.setVisible(true);
                                    updateMessage("PROCESSING PLS WAIT.....");
                                    Thread.sleep(1000);
                                    return null;
                                }
                            };
                            childController.displayinfo.textProperty().bind(task.messageProperty());
                            task.setOnSucceeded(f -> {
                                childController.displayinfo.textProperty().unbind();
//                                List catname = new ItemsBL().getItemsFromCategory(selectedRecord.getUomName());
//                                if (catname.isEmpty()) {
                                    int result = new UomBL().removeData(selectedRecord.getUomName());
                                    System.out.println(result);
                                    switch (result) {
                                        case 1:
                                            childController.displayinfo.setText("SUCCESSFULLY DELETED");
                                            childController.spinner.setVisible(false);
                                            childController.check.setVisible(true);
                                            TableData();
                                            stage.close();
                                            break;
                                        default:
                                            childController.displayinfo.setText("NOTICE! AN ERROR OCCURED");
                                            childController.spinner.setVisible(false);
                                            childController.check.setVisible(false);
                                            break;

                                    }
//                                } else {
                                    childController.displayinfo.setText("UNABLE TO DELETE RECORD");
                                    childController.spinner.setVisible(false);
                                    childController.check.setVisible(false);
//                                }
                            });
                            Thread d = new Thread(task);
                            d.setDaemon(true);
                            d.start();

                        });
                        Scene scene = new Scene(parent);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parent.getScene().getWindow());
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.resizableProperty().setValue(false);
                        stage.showAndWait();

                    } catch (IOException ex) {
                        Logger.getLogger(AddUomController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });

        }

        /**
         * places an add button in the row only if the row is not empty.
         */
        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(paddedButton);
            } else {
                setGraphic(null);
            }
        }
    }

}
