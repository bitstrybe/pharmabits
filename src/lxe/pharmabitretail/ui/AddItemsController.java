
package lxe.pharmabitretail.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import lxe.pharmabitretail.bl.CategoryBL;
import lxe.pharmabitretail.bl.InsertUpdateBL;
import lxe.pharmabitretail.bl.ItemsBL;
import lxe.pharmabitretail.bl.ManufacturerBL;
import lxe.pharmabitretail.bl.StockinBL;
import lxe.pharmabitretail.entity.Category;
import lxe.pharmabitretail.entity.Items;
import lxe.pharmabitretail.entity.Manufacturer;
import lxe.pharmabitretail.entity.Users;
import lxe.pharmabitretail.tablemodel.ItemTableModel;
import lxe.pharmabitretail.utils.FilterComboBox;
import lxe.pharmabitretail.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author JScare
 */
public class AddItemsController implements Initializable {

    ObservableList<ItemTableModel> data;

    @FXML
    public JFXTextField itmtextfield;
    private Button closeButton;
    public Label displayinfo;
    public JFXSpinner spinner;
    public FontAwesomeIcon check;
    public FontAwesomeIcon duplicatelock;
    public AnchorPane itemspane;
    @FXML
    private ComboBox<String> categorycombo;
    @FXML
    private ComboBox<String> manufacturercombo;
    @FXML
    private JFXTextField searchbtn;
    @FXML
    private TableView<ItemTableModel> itemtableview;
    @FXML
    private TableColumn<ItemTableModel, String> itemname;
    @FXML
    private TableColumn<ItemTableModel, String> category;
    @FXML
    private TableColumn<ItemTableModel, String> manufacturer;
    @FXML
    private TableColumn<ItemTableModel, String> uom;
    @FXML
    private TableColumn<ItemTableModel, Number> rol;
    @FXML
    private TableColumn<ItemTableModel, Boolean> action;
    private JFXTextField roltextfield;
    private JFXTextField volume;
    private ComboBox<String> volumevalue;
    private ChoiceBox<String> uomlist;
    @FXML
    private ComboBox<?> manufacturercombo1;
    @FXML
    private JFXTextField itmtextfield1;
    @FXML
    private ComboBox<?> umocombo;

    public void getManufacturer() {
        List<Manufacturer> list = new ManufacturerBL().getAllManufacturer();
        ObservableList<Manufacturer> result = FXCollections.observableArrayList(list);
        result.forEach((man) -> {
            manufacturercombo.getItems().add(man.getManufacturer());
        });
    }

    public void getCategory() {
        List<Category> list = new CategoryBL().getAllCategory();
        ObservableList<Category> result = FXCollections.observableArrayList(list);
        result.forEach((cat) -> {
            categorycombo.getItems().add(cat.getCategoryName());
        });
    }

    public void getVolumeValue() {
        volumevalue.getItems().add("g");
        volumevalue.getItems().add("mg");
        volumevalue.getItems().add("l");
        volumevalue.getItems().add("ml");
        volumevalue.getItems().add("mega");
        volumevalue.getItems().add("others");
    }
    public void getUnitOfMeasurement(){
        uomlist.getItems().add("Box");
        uomlist.getItems().add("Pack");
        uomlist.getItems().add("Strips");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODOg
        getCategory();
        getManufacturer();
        getVolumeValue();
        getUnitOfMeasurement();
        TableData();
        searchbtn.textProperty().addListener(e -> {
            if (searchbtn.getText().length() > 1) {
                TableData(searchbtn.getText());
            } else {
                TableData();
            }
        });
        manufacturercombo.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String s = FilterComboBox.jumpTo(event.getText(), manufacturercombo.getValue(), manufacturercombo.getItems());
                if (s != null) {
                    manufacturercombo.setValue(s);
                }
            }

        });

        categorycombo.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String s = FilterComboBox.jumpTo(event.getText(), categorycombo.getValue(), categorycombo.getItems());
                if (s != null) {
                    categorycombo.setValue(s);
                }
            }

        });
        volumevalue.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String s = FilterComboBox.jumpTo(event.getText(), volumevalue.getValue(), volumevalue.getItems());
                if (s != null) {
                    volumevalue.setValue(s);
                }
            }

        });
    }


    private void closemtd(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void saveAction() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                spinner.setVisible(true);
                check.setVisible(false);
                updateMessage("PROCESSING PLS WAIT.....");
                Thread.sleep(2000);
                return null;
            }
        };
        displayinfo.textProperty().bind(task.messageProperty());
        task.setOnSucceeded(s -> {
            displayinfo.textProperty().unbind();
            Items cat = new Items();
            String itemcode = itmtextfield.getText() + " " + categorycombo.getValue() + " " + volume.getText() + volumevalue.getValue() + " (" + manufacturercombo.getValue() + ")";
            try {
                cat.setItemDesc(itemcode);
                cat.setItemName(itmtextfield.getText());
                cat.setCategory(new Category(categorycombo.getValue()));
                cat.setManufacturer(new Manufacturer(manufacturercombo.getValue()));
                cat.setVomDef(Double.parseDouble(volume.getText()));
                cat.setVom(volumevalue.getValue());
                cat.setRol(Integer.parseInt(roltextfield.getText()));
                cat.setUsers(new Users(LoginController.u.getUserid()));
                cat.setEntryLog(new Date());
                cat.setLastModified(new Date());

                int result = new InsertUpdateBL().insertData(cat);
                switch (result) {
                    case 1:
                        displayinfo.setText("SUCCESSFULLY SAVED");
                        Utilities.clearAllField(itemspane);
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
            } catch (NumberFormatException ex) {
                spinner.setVisible(false);
                displayinfo.setText("Invalid Input");

            }
        });
        Thread d = new Thread(task);
        d.setDaemon(true);
        d.start();
    }

    public void TableData() {
        List<Items> c = new ItemsBL().getAllItems();
        data = FXCollections.observableArrayList();
        c.forEach((item) -> {
            String uom_value = String.valueOf(item.getVomDef()) + item.getVom();
            try {
                data.add(new ItemTableModel(item.getItemDesc(), item.getItemName(), item.getCategory().getCategoryName(), item.getManufacturer().getManufacturer(), uom_value, item.getRol()));
//                System.out.println(item.getItemCodeFullname() + " " + item.getItemName() + " " + item.getCategory().getCategoryName() + "" + item.getManufacturer().getManufacturer() + " " + uom_value + " " + item.getRol());
            } catch (Exception ex) {
                Logger.getLogger(AddItemsController.class.getName()).log(Level.SEVERE, null, ex);

            }
        });
        itemname.setCellValueFactory(cell -> cell.getValue().getItemNameProperty());
        category.setCellValueFactory(cell -> cell.getValue().getCategoryProperty());
        manufacturer.setCellValueFactory(cell -> cell.getValue().getManufacturerProperty());
        uom.setCellValueFactory(cell -> cell.getValue().getUomProperty());
        rol.setCellValueFactory(cell -> cell.getValue().getRolProperty());
        action.setSortable(false);
        action.setMaxWidth(480);

        action.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ItemTableModel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ItemTableModel, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        action.setCellFactory(new Callback<TableColumn<ItemTableModel, Boolean>, TableCell<ItemTableModel, Boolean>>() {
            @Override
            public TableCell<ItemTableModel, Boolean> call(TableColumn<ItemTableModel, Boolean> personBooleanTableColumn) {
                return new AddPersonCell();
            }
        });
        itemtableview.setItems(data);
//        clientTable.getColumns().add(action);
        itemtableview.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

    }

    public void TableData(String p) {
        List<Items> c = new ItemsBL().searchAllItems(p);
        data = FXCollections.observableArrayList();

        c.forEach((item) -> {
            String uom_value = String.valueOf(item.getVomDef()) + item.getVom();
            try {
                data.add(new ItemTableModel(item.getItemDesc(), item.getItemName(), item.getCategory().getCategoryName(), item.getManufacturer().getManufacturer(), uom_value, item.getRol()));
            } catch (Exception ex) {
                Logger.getLogger(AddItemsController.class.getName()).log(Level.SEVERE, null, ex);

            }
        });
        itemname.setCellValueFactory(cell -> cell.getValue().getItemNameProperty());
        category.setCellValueFactory(cell -> cell.getValue().getCategoryProperty());
        manufacturer.setCellValueFactory(cell -> cell.getValue().getManufacturerProperty());
        uom.setCellValueFactory(cell -> cell.getValue().getUomProperty());
        rol.setCellValueFactory(cell -> cell.getValue().getRolProperty());
        action.setSortable(false);
        action.setMaxWidth(480);

        action.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ItemTableModel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ItemTableModel, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        action.setCellFactory(new Callback<TableColumn<ItemTableModel, Boolean>, TableCell<ItemTableModel, Boolean>>() {
            @Override
            public TableCell<ItemTableModel, Boolean> call(TableColumn<ItemTableModel, Boolean> personBooleanTableColumn) {
                return new AddPersonCell();
            }
        });
        itemtableview.setItems(data);
//        clientTable.getColumns().add(action);
        itemtableview.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

    }

    public class AddPersonCell extends TableCell<ItemTableModel, Boolean> {

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
                    int selectdIndex = getTableRow().getIndex();
                    //Create a new table show details of the selected item
                    ItemTableModel selectedRecord = (ItemTableModel) itemtableview.getItems().get(selectdIndex);

                    try {

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
                                List ls = new StockinBL().getStockinFromItems(selectedRecord.getItemCodeName());
                                if (ls.isEmpty()) {
                                    int result = new ItemsBL().removeData(selectedRecord.getItemCodeName());
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
                                } else {
                                    childController.displayinfo.setText("UNABLE TO DELETE RECORD");
                                    childController.spinner.setVisible(false);
                                    childController.check.setVisible(false);
                                }

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
                        Logger.getLogger(AddItemsController.class.getName()).log(Level.SEVERE, null, ex);
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
