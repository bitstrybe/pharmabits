package lxe.pharmabitretail.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.imageio.ImageIO;
import lxe.pharmabitretail.bl.CategoryBL;
import lxe.pharmabitretail.bl.InsertUpdateBL;
import lxe.pharmabitretail.bl.ItemsBL;
import lxe.pharmabitretail.bl.ManufacturerBL;
import lxe.pharmabitretail.bl.StockinBL;
import lxe.pharmabitretail.bl.UomBL;
import lxe.pharmabitretail.bl.VomBL;
import lxe.pharmabitretail.entity.Category;
import lxe.pharmabitretail.entity.Items;
import lxe.pharmabitretail.entity.Manufacturer;
import lxe.pharmabitretail.entity.Uom;
import lxe.pharmabitretail.entity.UomDef;
import lxe.pharmabitretail.entity.Users;
import lxe.pharmabitretail.tablemodel.ItemTableModel;
import lxe.pharmabitretail.utils.FilterComboBox;
import lxe.pharmabitretail.utils.Utilities;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/**
 * FXML Controller class
 *
 * @author JScare
 */
public class AddItemsController implements Initializable {

    private Desktop desktop = Desktop.getDesktop();
    final FileChooser fileChooser = new FileChooser();

    ObservableList<ItemTableModel> data;

    public JFXTextField itmtextfield;
    private Button closeButton;
    public Label displayinfo;
    public JFXSpinner spinner;
    public FontAwesomeIcon check;
    public FontAwesomeIcon duplicatelock;
    public Pane itemspane;
    @FXML
    private ComboBox<String> categorycombo;
    @FXML
    private ComboBox<String> manufacturercombo;
    @FXML
    private ComboBox<String> uomcombo;
    @FXML
    private JFXTextField searchbtn;
    @FXML
    private TableView<ItemTableModel> itemtableview;
    @FXML
    private TableColumn<ItemTableModel, ImageView> itemimage;
    @FXML
    private TableColumn<ItemTableModel, String> itemname;
    @FXML
    private TableColumn<ItemTableModel, String> category;
    @FXML
    private TableColumn<ItemTableModel, String> manufacturer;
    @FXML
    private TableColumn<ItemTableModel, String> uomtb;
    @FXML
    private TableColumn<ItemTableModel, String> vomtb;

    @FXML
    private TableColumn<ItemTableModel, Number> rol;
    @FXML
    private TableColumn<ItemTableModel, Boolean> action;
    @FXML
    private Button closebtn;
    @FXML
    private JFXTextField uom_val1;
    @FXML
    private JFXTextField uom_val2;
    @FXML
    private ComboBox<String> vom;
    @FXML
    private JFXTextField vom_val;
    @FXML
    private JFXTextField roltextfield;
    @FXML
    private Button browse;
    @FXML
    private ImageView itemimages;

    byte[] item_image = null;
    File ifile;
    InputStream initialStream;

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

    public void getUOM() {
        List<Uom> list = new VomBL().getAllUom();
        ObservableList<Uom> result = FXCollections.observableArrayList(list);
        result.forEach((cat) -> {
            uomcombo.getItems().add(cat.getUomDesc());
        });
    }

    public void getVolumeValue() {
        vom.getItems().add("g");
        vom.getItems().add("mg");
        vom.getItems().add("l");
        vom.getItems().add("ml");
        vom.getItems().add("mega");
        vom.getItems().add("others");

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getCategory();
        getManufacturer();
        getVolumeValue();
        getUOM();
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

        uomcombo.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String s = FilterComboBox.jumpTo(event.getText(), uomcombo.getValue(), uomcombo.getItems());
                if (s != null) {
                    uomcombo.setValue(s);
                }
            }

        });
        browse.setOnAction(new EventHandler<ActionEvent>() {
            Stage st = new Stage();

            @Override
            public void handle(ActionEvent event) {

                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
                FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

                //Show open file dialog
                ifile = fileChooser.showOpenDialog(null);
                //File ofile = new File
                try {
                    BufferedImage bufferedImage = ImageIO.read(ifile);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    //image.setPreserveRatio(true);
                    itemimages.setImage(image);
                    itemimages.setFitWidth(200);
                    itemimages.setFitWidth(200);
                    itemimages.setPreserveRatio(true);
                    itemimages.scaleXProperty();
                    itemimages.scaleYProperty();
                    itemimages.setSmooth(true);
                    itemimages.setCache(true);
                    //WritableImage wimage = SwingFXUtils.toFXImage(bufferedImage, null);
                    //FileInputStream fin = new FileInputStream(ifile);
                    //File fout = new File("./img/");
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    byte[] buf = new byte[1024];
//
//                    for (int readNum; (readNum = fin.read(buf)) != -1;) {
//                        bos.write(buf, 0, readNum);
//                    }
                    //item_image = bos.toByteArray();

                } catch (IOException ex) {
                    Logger.getLogger(AddItemsController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(AddItemsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
//        volumevalue.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                String s = FilterComboBox.jumpTo(event.getText(), volumevalue.getValue(), volumevalue.getItems());
//                if (s != null) {
//                    volumevalue.setValue(s);
//                }
//            }
//
//        });
    }

    private void closemtd(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveAction() {
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
            Items cat = new Items();
            String itemdesc = itmtextfield.getText().toUpperCase() + " " + categorycombo.getValue().toUpperCase() + " " + vom_val.getText() + vom.getSelectionModel().getSelectedItem() + " (" + manufacturercombo.getValue() + ")";
            try {
                cat.setItemDesc(itemdesc);
                cat.setItemName(itmtextfield.getText());
                cat.setCategory(new Category(categorycombo.getValue()));
                cat.setManufacturer(new Manufacturer(manufacturercombo.getValue()));
                cat.setVomDef(Double.parseDouble(vom_val.getText()));
                cat.setVom(vom.getSelectionModel().getSelectedItem());
                cat.setRol(Integer.parseInt(roltextfield.getText()));
                cat.setUsers(new Users(LoginController.u.getUserid()));
                cat.setEntryLog(new Date());
                cat.setLastModified(new Date());
                //adding image file to directory
                initialStream = new FileInputStream(ifile);
                File targetFile = new File("./img/" + itemdesc + "." + FilenameUtils.getExtension(ifile.getName()));
                cat.setItemImg("./img/" + itemdesc + "." + FilenameUtils.getExtension(ifile.getName()));

                List<UomDef> udf = new ArrayList<>();
                UomDef df = new UomDef();
                df.setItemCode(cat);
                df.setUomCode(new Uom(uomcombo.getSelectionModel().getSelectedItem()));
                df.setUomNm(Integer.parseInt(uom_val1.getText()));
                df.setUomDm(Integer.parseInt(uom_val2.getText()));
                udf.add(df);
                cat.setUomDefCollection(udf);
                int result = new InsertUpdateBL().insertData(cat);
                switch (result) {
                    case 1:
                        java.nio.file.Files.copy(initialStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
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
                Logger.getLogger(AddItemsController.class.getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(AddItemsController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    IOUtils.close(initialStream);
                } catch (IOException ex) {
                    Logger.getLogger(AddItemsController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            UomDef domf = new UomBL().getUombyItemId(item.getItemCode());
            int uomitem = domf.getUomItem();
            String uom_val = String.valueOf(domf.getUomCode().getUomDesc() + " " + domf.getUomNm() + " X " + domf.getUomDm());
            String vom_value = String.valueOf(item.getVomDef()) + item.getVom();
            try {
                ImageView itemimage = new ImageView();
                File file = new File(item.getItemImg());
                Image image = new Image(file.toURI().toString());
                itemimage.setImage(image);
                itemimage.setFitWidth(70);
                itemimage.setFitWidth(70);
                itemimage.setPreserveRatio(true);
                itemimage.scaleXProperty();
                itemimage.scaleYProperty();
                itemimage.setSmooth(true);
                itemimage.setCache(true);
                data.add(new ItemTableModel(item.getItemCode(), item.getItemDesc(), item.getItemName(), item.getCategory().getCategoryName(), item.getManufacturer().getManufacturer(),uom_val , uomitem, vom_value, item.getRol(), itemimage));
//                System.out.println(item.getItemCodeFullname() + " " + item.getItemName() + " " + item.getCategory().getCategoryName() + "" + item.getManufacturer().getManufacturer() + " " + uom_value + " " + item.getRol());
            } catch (Exception ex) {
                Logger.getLogger(AddItemsController.class.getName()).log(Level.SEVERE, null, ex);

            }
        });

        itemname.setCellValueFactory(cell -> cell.getValue().getItemNameProperty());
        category.setCellValueFactory(cell -> cell.getValue().getCategoryProperty());
        manufacturer.setCellValueFactory(cell -> cell.getValue().getManufacturerProperty());
        uomtb.setCellValueFactory(cell -> cell.getValue().getUomProperty());
        vomtb.setCellValueFactory(cell -> cell.getValue().getVomProperty());
        rol.setCellValueFactory(cell -> cell.getValue().getRolProperty());
        itemimage.setCellValueFactory(new PropertyValueFactory<ItemTableModel, ImageView>("image"));
        itemimage.setPrefWidth(90);
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
            UomDef domf = new UomBL().getUombyItemId(item.getItemCode());
            int uomitem = domf.getUomItem();
            String uom_val = String.valueOf(domf.getUomCode().getUomDesc() + " " + domf.getUomNm() + " X " + domf.getUomDm());
            String vom_value = String.valueOf(item.getVomDef()) + item.getVom();
            try {

                ImageView itemimage = new ImageView();
                File file = new File(item.getItemImg());
                Image image = new Image(file.toURI().toString());
                itemimage.setImage(image);
                itemimage.setFitWidth(70);
                itemimage.setFitWidth(70);
                itemimage.setPreserveRatio(true);
                itemimage.scaleXProperty();
                itemimage.scaleYProperty();
                itemimage.setSmooth(true);
                itemimage.setCache(true);
                data.add(new ItemTableModel(item.getItemCode(), item.getItemDesc(), item.getItemName(), item.getCategory().getCategoryName(), item.getManufacturer().getManufacturer(),uom_val , uomitem, vom_value, item.getRol(), itemimage));
            } catch (Exception ex) {
                Logger.getLogger(AddItemsController.class.getName()).log(Level.SEVERE, null, ex);

            }
        });
        itemname.setCellValueFactory(cell -> cell.getValue().getItemNameProperty());
        category.setCellValueFactory(cell -> cell.getValue().getCategoryProperty());
        manufacturer.setCellValueFactory(cell -> cell.getValue().getManufacturerProperty());
        vomtb.setCellValueFactory(cell -> cell.getValue().getUomProperty());
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
//                                List ls = new StockinBL().getStockinFromItems(selectedRecord.getItemCodeName());
//                                if (ls.isEmpty()) {
                                int uom_result = new UomBL().removeData(selectedRecord.getUomItem());

                                if (uom_result == 1) {
                                    int result = new ItemsBL().removeData(selectedRecord.getItemCode());
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
                                }

//                                } else {
//                                    childController.displayinfo.setText("UNABLE TO DELETE RECORD");
//                                    childController.spinner.setVisible(false);
//                                    childController.check.setVisible(false);
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

    @FXML
    public void closefrom() {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

}
