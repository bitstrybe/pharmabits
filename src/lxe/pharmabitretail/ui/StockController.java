
package lxe.pharmabitretail.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
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
import javafx.scene.control.ContentDisplay;
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
import lxe.pharmabitretail.bl.SalesDetailsBL;
import lxe.pharmabitretail.bl.StockinBL;
import lxe.pharmabitretail.bl.StockoutBL;
import lxe.pharmabitretail.entity.Items;
import lxe.pharmabitretail.entity.Stockin;
import lxe.pharmabitretail.entity.Stockout;
import lxe.pharmabitretail.entity.Users;
import lxe.pharmabitretail.tablemodel.StockTableModel;
import lxe.pharmabitretail.tablemodel.StockinTableModel;
import lxe.pharmabitretail.tablemodel.StockoutTableModel;
import lxe.pharmabitretail.utils.Utilities;

/**
 * FXML Controller class
 *
 * @author JScare
 */
public class StockController implements Initializable {

    ObservableList<StockTableModel> data;
    ObservableList<StockinTableModel> stkindata;
    ObservableList<StockoutTableModel> stkoutdata;

    @FXML
    private TableView<StockTableModel> stock;
    @FXML
    private TableColumn<StockTableModel, String> stkbatchno;
    @FXML
    private TableColumn<StockTableModel, String> stkitem;
    @FXML
    private TableColumn<StockTableModel, Number> stkinqty;
    @FXML
    private TableColumn<StockTableModel, Number> stkoutqty;
    @FXML
    private TableColumn<StockTableModel, Number> salesqty;
    @FXML
    private TableColumn<StockTableModel, Number> stkbal;
    @FXML
    private TableColumn<StockTableModel, Number> cstprice;
    @FXML
    private TableColumn<StockTableModel, Number> salesprice;
    @FXML
    private TableColumn<StockTableModel, Number> nhisprice;
    @FXML
    private TableView<StockinTableModel> stockin;
    @FXML
    private TableColumn<StockinTableModel, String> stkinbatchno;
    @FXML
    private TableColumn<StockinTableModel, String> stkinitems;
    @FXML
    private TableColumn<StockinTableModel, Number> stkqty;
    @FXML
    private TableColumn<StockinTableModel, String> stkindate;
    @FXML
    private TableColumn<StockinTableModel, String> expirydate;
    @FXML
    private TableColumn<StockinTableModel, Boolean> stkaction;
    @FXML
    private TableView<StockoutTableModel> stockout;
    @FXML
    private TableColumn<StockoutTableModel, String> stkoutbatchno;
    @FXML
    private TableColumn<StockoutTableModel, String> stkoutitems;
    @FXML
    private TableColumn<StockoutTableModel, Number> stkoutqtytb;
    @FXML
    private TableColumn<StockoutTableModel, String> stkoutdate;
    @FXML
    private TableColumn<StockoutTableModel, Boolean> stkoutaction;
    @FXML
    private JFXTextField search11;
    @FXML
    private JFXButton stockinbtn;
    @FXML
    private JFXButton stockoutbtn;
    @FXML
    private JFXTextField stocksearch;
    @FXML
    private JFXTextField stockinsearch;

    StockTableModel list;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        AllStockTableData();
        stocksearch.textProperty().addListener(e -> {
            if (stocksearch.getText().length() > 1) {
                searchAllStockTableData(stocksearch.getText());
            } else {
                AllStockTableData();
            }
        });
        stockinsearch.textProperty().addListener(e -> {
            if (stockinsearch.getText().length() > 1) {
                searchStockinTableData(stockinsearch.getText());
            } else {
                StockinTableData(list.getBatchNo());
            }
        });
        stock.addEventHandler(MouseEvent.MOUSE_CLICKED, v -> {
            list = stock.getSelectionModel().getSelectedItem();
            StockinTableData(list.getBatchNo());
            StockoutTableData(list.getBatchNo());
//            stockoutbtn.setDisable(false);
        });
        stockoutbtn.setDisable(true);

        if (LoginController.u.getRoles().equals("Administrator") || LoginController.u.getRoles().equals("Sales Supervisor")) {
            stockinbtn.setDisable(false);
//            stockoutbtn.setDisable(false);
            stock.addEventHandler(MouseEvent.MOUSE_CLICKED, v -> {
                stockoutbtn.setDisable(false);
            });

        } else {
            stockinbtn.setDisable(true);
            stockoutbtn.setDisable(true);
        }

    }

    public void AllStockTableData() {
        List<Stockin> stk = new StockinBL().getAllStockinGroup();
        data = FXCollections.observableArrayList();
        stk.forEach(e -> {
            long stockoutqty;

            try {
                stockoutqty = new StockoutBL().getStockoutqty(e.getBatchNo());
            } catch (Exception ex) {
                stockoutqty = 0;
            }
            long salesqty;
            try {
                salesqty = new StockinBL().getSalesTotal(e.getBatchNo());
            } catch (Exception ex) {
                salesqty = 0;
            }
            long stockinqty = new StockinBL().getStockInTotal(e.getBatchNo());
//            long stkbal = stockinqty - (salesqty + stockoutqty);
            long balance = new StockinBL().getStockBalance(e.getBatchNo());

            data.add(new StockTableModel(e.getBatchNo(), e.getItems().getItemDesc().toUpperCase(), stockinqty, stockoutqty, salesqty, balance, Utilities.roundToTwoDecimalPlace(e.getCostPrice(), 2), Utilities.roundToTwoDecimalPlace(e.getSalesPrice(), 2), Utilities.roundToTwoDecimalPlace(e.getNhisPrice(), 2)));
        });
        stkbatchno.setCellValueFactory(cell -> cell.getValue().getBatchNoProperty());
        stkitem.setCellValueFactory(cell -> cell.getValue().getItemsProperty());
        stkinqty.setCellValueFactory(cell -> cell.getValue().getStockinQtyProperty());
        stkoutqty.setCellValueFactory(cell -> cell.getValue().getStockoutQtyProperty());
        salesqty.setCellValueFactory(cell -> cell.getValue().getSalesQtyProperty());
        stkbal.setCellValueFactory(cell -> cell.getValue().getStockbalProperty());
        cstprice.setCellValueFactory(cell -> cell.getValue().getStockCostPriceProperty());
        salesprice.setCellValueFactory(cell -> cell.getValue().getStockSalesPriceProperty());
        nhisprice.setCellValueFactory(cell -> cell.getValue().getNhisPriceProperty());

        stock.setItems(data);
//        clientTable.getColumns().add(action);
        stock.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    public void searchAllStockTableData(String p) {
        List<Stockin> stk = new StockinBL().searchAllStockinGroup(p);
        data = FXCollections.observableArrayList();
        stk.forEach(e -> {
            long stockoutqty;
            long salesqty;
            try {
                stockoutqty = new StockoutBL().getStockoutqty(e.getBatchNo());
            } catch (Exception ex) {
                stockoutqty = 0;
            }

            try {
                salesqty = new StockinBL().getSalesTotal(e.getBatchNo());
            } catch (Exception ex) {
                salesqty = 0;
            }
            long stockinqty = new StockinBL().getStockInTotal(e.getBatchNo());
            long stkbal = stockinqty - (salesqty + stockoutqty);

            data.add(new StockTableModel(e.getBatchNo(), e.getItems().getItemDesc().toUpperCase(), stockinqty, stockoutqty, salesqty, stkbal, Utilities.roundToTwoDecimalPlace(e.getCostPrice(), 2), Utilities.roundToTwoDecimalPlace(e.getSalesPrice(), 2), Utilities.roundToTwoDecimalPlace(e.getNhisPrice(), 2)));
        });
        stkbatchno.setCellValueFactory(cell -> cell.getValue().getBatchNoProperty());
        stkitem.setCellValueFactory(cell -> cell.getValue().getItemsProperty());
        stkinqty.setCellValueFactory(cell -> cell.getValue().getStockinQtyProperty());
        stkoutqty.setCellValueFactory(cell -> cell.getValue().getStockoutQtyProperty());
        salesqty.setCellValueFactory(cell -> cell.getValue().getSalesQtyProperty());
        stkbal.setCellValueFactory(cell -> cell.getValue().getStockbalProperty());
        cstprice.setCellValueFactory(cell -> cell.getValue().getStockCostPriceProperty());
        salesprice.setCellValueFactory(cell -> cell.getValue().getStockSalesPriceProperty());
        nhisprice.setCellValueFactory(cell -> cell.getValue().getNhisPriceProperty());

        stock.setItems(data);
//        clientTable.getColumns().add(action);
        stock.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    public void StockinTableData(String batchno) {
        List<Stockin> v = new StockinBL().getAllStockinBatchNo(batchno);
        stkindata = FXCollections.observableArrayList();
        v.forEach((stockin) -> {
            stkindata.add(new StockinTableModel(stockin.getStockinId(), stockin.getBatchNo(), stockin.getItems().getItemDesc().toUpperCase(), stockin.getQuantity(), Utilities.convertDateToString(stockin.getStockinDate()), Utilities.convertDateToString(stockin.getExpiryDate()), Utilities.roundToTwoDecimalPlace(stockin.getCostPrice(), 2), Utilities.roundToTwoDecimalPlace(stockin.getSalesPrice(), 2), Utilities.roundToTwoDecimalPlace(stockin.getNhisPrice(), 2)));
        });
        stkinbatchno.setCellValueFactory(cell -> cell.getValue().getBatchNoProperty());
        stkinitems.setCellValueFactory(cell -> cell.getValue().getItemProperty());
        stkqty.setCellValueFactory(cell -> cell.getValue().getQuantityProperty());
        stkindate.setCellValueFactory(cell -> cell.getValue().getStockinDateProperty());
        expirydate.setCellValueFactory(cell -> cell.getValue().getExpiryDateProperty());
        stkaction.setSortable(false);
//        action.setMaxWidth(480);

        stockin.setItems(stkindata);
//        mainTableModel.getColumns().add(action);
        stockin.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        stkaction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StockinTableModel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<StockinTableModel, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        stkaction.setCellFactory(new Callback<TableColumn<StockinTableModel, Boolean>, TableCell<StockinTableModel, Boolean>>() {
            @Override
            public TableCell<StockinTableModel, Boolean> call(TableColumn<StockinTableModel, Boolean> personBooleanTableColumn) {
                return new AddPersonCell();
            }
        });

    }

    public void searchStockinTableData(String p) {
//        stockinbtn.setDisable(false);
        List<Stockin> v = new StockinBL().searchAllStockin(p);
        stkindata = FXCollections.observableArrayList();
        v.forEach((stockin) -> {
            stkindata.add(new StockinTableModel(stockin.getStockinId(), stockin.getBatchNo(), stockin.getItems().getItemDesc(), stockin.getQuantity(), Utilities.convertDateToString(stockin.getStockinDate()), Utilities.convertDateToString(stockin.getExpiryDate()), Utilities.roundToTwoDecimalPlace(stockin.getCostPrice(), 2), Utilities.roundToTwoDecimalPlace(stockin.getSalesPrice(), 2), Utilities.roundToTwoDecimalPlace(stockin.getNhisPrice(), 2)));
        });
        stkinbatchno.setCellValueFactory(cell -> cell.getValue().getBatchNoProperty());
        stkinitems.setCellValueFactory(cell -> cell.getValue().getItemProperty());
        stkqty.setCellValueFactory(cell -> cell.getValue().getQuantityProperty());
        stkindate.setCellValueFactory(cell -> cell.getValue().getStockinDateProperty());
        expirydate.setCellValueFactory(cell -> cell.getValue().getExpiryDateProperty());
        stkaction.setSortable(false);
//        action.setMaxWidth(480);

        stockin.setItems(stkindata);
//        mainTableModel.getColumns().add(action);
        stockin.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        stkaction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StockinTableModel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<StockinTableModel, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        stkaction.setCellFactory(new Callback<TableColumn<StockinTableModel, Boolean>, TableCell<StockinTableModel, Boolean>>() {
            @Override
            public TableCell<StockinTableModel, Boolean> call(TableColumn<StockinTableModel, Boolean> personBooleanTableColumn) {
                return new AddPersonCell();
            }
        });

    }

    public void StockoutTableData(String batchNo) {
        List<Stockout> v = new StockoutBL().getAllStockoutbyBatchNo(batchNo);
        stkoutdata = FXCollections.observableArrayList();
        v.forEach((out) -> {
            List<Stockin> batchno = new StockinBL().getItemStockinBatchNo(out.getBatchNo());
            stkoutdata.add(new StockoutTableModel(out.getStockoutId(), out.getBatchNo(), batchno.get(0).getItems().getItemDesc().toUpperCase(), out.getQuantity(), out.getRemarks(), Utilities.convertDateToString(out.getStkDate())));
        });
        stkoutbatchno.setCellValueFactory(cell -> cell.getValue().getBatchNoProperty());
        stkoutitems.setCellValueFactory(cell -> cell.getValue().getItemProperty());
        stkoutqtytb.setCellValueFactory(cell -> cell.getValue().getQuantityProperty());
        stkoutdate.setCellValueFactory(cell -> cell.getValue().getDateProperty());
        stkoutaction.setSortable(false);
//        action.setMaxWidth(480);

        stockout.setItems(stkoutdata);
//        mainTableModel.getColumns().add(action);
        stockout.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        stkoutaction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StockoutTableModel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<StockoutTableModel, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        stkoutaction.setCellFactory(new Callback<TableColumn<StockoutTableModel, Boolean>, TableCell<StockoutTableModel, Boolean>>() {
            @Override
            public TableCell<StockoutTableModel, Boolean> call(TableColumn<StockoutTableModel, Boolean> personBooleanTableColumn) {
                return new AddPersonCellStockout();
            }
        });

    }

    @FXML
    private void addstockinpopup() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddStockIn.fxml"));
        Parent parent = (Parent) fxmlLoader.load();
        AddStockInController childController = fxmlLoader.getController();
        childController.save.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            childController.save.setDisable(true);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    childController.spinner.setVisible(true);
                    childController.check.setVisible(false);
                    updateMessage("PROCESSING PLS WAIT.....");
                    Thread.sleep(2000);
                    return null;
                }
            };
            childController.displayinfo.textProperty().bind(task.messageProperty());
            task.setOnSucceeded(s -> {
                childController.displayinfo.textProperty().unbind();
                Stockin cat = new Stockin();
                List<Integer> stcinid = new StockinBL().getStockinCount();
                  if(stcinid.isEmpty()){
                       int stkval = 1;
                       cat.setStockinId(stkval);
                  }else{
                       int stkval = stcinid.get(0);
                       cat.setStockinId(++stkval);
                  }
               
              
                
                cat.setBatchNo(childController.batchtextfield.getText());
                cat.setItems(new Items(childController.itemlist.getSelectionModel().getSelectedItem()));
                cat.setQuantity(Integer.parseInt(childController.qnttextfield.getText()));
                cat.setCostPrice(Utilities.roundToTwoDecimalPlace(Double.parseDouble(childController.costtextfield.getText()), 2));
                cat.setSalesPrice(Utilities.roundToTwoDecimalPlace(Double.parseDouble(childController.salestextfield.getText()), 2));
                cat.setNhisPrice(Utilities.roundToTwoDecimalPlace(Double.parseDouble(childController.nhistextfield.getText()), 2));
                cat.setStockinDate(new Date());
                cat.setExpiryDate(Utilities.convertToDateViaSqlDate(childController.expirydate.getValue()));
                cat.setUsers(new Users(LoginController.u.getUserid()));
                cat.setEntryLog(new Date());
                cat.setLastModified(new Date());
                int result = new InsertUpdateBL().insertData(cat);
                switch (result) {
                    case 1:
                        childController.displayinfo.setText("SUCCESSFULLY SAVED");
                        Utilities.clearAllField(childController.stockpane);
                        childController.itemname.setText(null);
                        System.out.println(childController.stockpane.getChildren());
                        childController.spinner.setVisible(false);
                        childController.check.setVisible(true);
                        list = stock.getSelectionModel().getSelectedItem();
                        AllStockTableData();
                        stockin.getItems().clear();
                        stockout.getItems().clear();
                        break;
                    default:
                        childController.displayinfo.setText("NOTICE! AN ERROR OCCURED");
                        childController.spinner.setVisible(false);
                        childController.check.setVisible(false);
                        break;

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
    }

    @FXML
    private void addstockoutpopup() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddStockOut.fxml"));
        Parent parent = (Parent) fxmlLoader.load();
        AddStockOutController childController = fxmlLoader.getController();
        list = stock.getSelectionModel().getSelectedItem();
        childController.itemname.setText(list.getItems());
        childController.batchnoname.setText(list.getBatchNo());
        childController.save.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    childController.spinner.setVisible(true);
                    updateMessage("PROCESSING PLS WAIT.....");
                    Thread.sleep(2000);
                    return null;
                }
            };
            childController.displayinfo.textProperty().bind(task.messageProperty());
            task.setOnSucceeded(s -> {
                childController.displayinfo.textProperty().unbind();
                Stockout cat = new Stockout();
                cat.setBatchNo(childController.batchnoname.getText());
//                cat.setItems(new Items(itemlist.getSelectionModel().getSelectedItem()));
                cat.setQuantity(Integer.parseInt(childController.qnttextfield.getText()));
//                cat.setCostPrice(Utilities.roundToTwoDecimalPlace(Float.parseFloat(childController.costpiecestextfield.getText()), 2));
//                cat.setSalesPrice(Utilities.roundToTwoDecimalPlace(Float.parseFloat(childController.salespiecetextfield.getText()), 2));
                cat.setStkDate(new Date());
                cat.setRemarks(childController.remarktxtarea.getText());
//                cat.setExpiryDate(Utilities.convertToDateViaSqlDate(childController.expirydate.getValue()));
                cat.setUsers(new Users(LoginController.u.getUserid()));
                cat.setEntryLog(new Date());
                cat.setModifiedDate(new Date());
                int result = new InsertUpdateBL().insertData(cat);
                switch (result) {
                    case 1:
                        childController.displayinfo.setText("SUCCESSFULLY SAVED");
                        Utilities.clearAllField(childController.stockpane);
                        childController.spinner.setVisible(false);
                        childController.check.setVisible(true);
                        list = stock.getSelectionModel().getSelectedItem();
                        AllStockTableData();
                        StockoutTableData(list.getBatchNo());
                        break;
                    default:
                        childController.displayinfo.setText("NOTICE! AN ERROR OCCURED");
                        childController.spinner.setVisible(false);
                        childController.check.setVisible(false);
                        break;

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

    }

    public class AddPersonCell extends TableCell<StockinTableModel, Boolean> {

        Image img = new Image(getClass().getResourceAsStream("edit.png"));
        Image img2 = new Image(getClass().getResourceAsStream("delete.png"));

        // a button for adding a new person.
//        JFXButton addButton = new JFXButton();
        // pads and centers the add button in the cell.
        HBox paddedButton = new HBox();
        JFXButton delButton = new JFXButton();
        JFXButton editButton = new JFXButton();
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
            paddedButton.getChildren().add(editButton);
            paddedButton.getChildren().add(delButton);
            delButton.setGraphic(new ImageView(img2));
            editButton.setGraphic(new ImageView(img));
            delButton.setRipplerFill(Paint.valueOf("#D8E1DC"));
            editButton.setRipplerFill(Paint.valueOf("#D8E1DC"));
            editButton.setOnAction(new EventHandlerImpl());

            delButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override

                public void handle(ActionEvent event) {
                    try {
                        int selectdIndex = getTableRow().getIndex();
                        //Create a new table show details of the selected item
                        StockinTableModel selectedRecord = (StockinTableModel) stockin.getItems().get(selectdIndex);
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Delete.fxml"));
                        Parent parent = (Parent) fxmlLoader.load();
                        DeleteController childController = fxmlLoader.getController();
                        childController.delete.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                            childController.displayinfo.setText("PROCESSING PLS WAIT.....");
                            Task<Void> task = new Task<Void>() {
                                @Override
                                protected Void call() throws Exception {
                                    childController.spinner.setVisible(true);
                                    updateMessage("Processing...");
                                    Thread.sleep(1000);
                                    return null;
                                }
                            };
                            task.setOnSucceeded(f -> {
                                List st = new SalesDetailsBL().getStockinFromSalesDetails(selectedRecord.getBatchNo());
                                if (st.isEmpty()) {
                                    int result = new StockinBL().removeData(selectedRecord.getStockinCode());
                                    switch (result) {
                                        case 1:
                                            childController.displayinfo.setText("SUCCESSFULLY DELETED");
                                            childController.spinner.setVisible(false);
                                            childController.check.setVisible(true);
                                            AllStockTableData();
                                            stockin.getItems().clear();
                                            stockout.getItems().clear();
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
                        Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
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

        private class EventHandlerImpl implements EventHandler<ActionEvent> {

            public EventHandlerImpl() {
            }

            @Override
            public void handle(ActionEvent event) {
                try {
                    int selectedIndex = getTableRow().getIndex();
                    StockinTableModel selectedRecord = (StockinTableModel) stockin.getItems().get(selectedIndex);
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditSkockIn.fxml"));
                    Parent parent = (Parent) fxmlLoader.load();
                    EditSkockInController childController = fxmlLoader.getController();
                    childController.itemname.setText(selectedRecord.getItem());
                    childController.batchtextfield.setText(selectedRecord.getBatchNo());
                    LocalDate expdate = Utilities.LOCAL_DATE(selectedRecord.getExpiryDate());
                    childController.expirydate.setValue(expdate);
                    childController.costtextfield.setText(String.valueOf(selectedRecord.getCostPrice()));
                    double costtotal = selectedRecord.getCostPrice() * selectedRecord.getQuantity();
                    childController.costpiecestextfield.setText(String.valueOf(costtotal));
                    childController.salestextfield.setText(String.valueOf(selectedRecord.getSalesPrice()));
                    double salestotal = selectedRecord.getSalesPrice() * selectedRecord.getQuantity();
                    childController.salespiecetextfield.setText(String.valueOf(salestotal));
                    childController.nhistextfield.setText(String.valueOf(selectedRecord.getNHISPrice()));
                    double nhistotal = selectedRecord.getNHISPrice() * selectedRecord.getQuantity();
                    childController.nhispiecetextfield.setText(String.valueOf(nhistotal));
                    childController.qnttextfield.setText(String.valueOf(selectedRecord.getQuantity()));
                    childController.save.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                childController.spinner.setVisible(true);
                                updateMessage("Processing...");
                                Thread.sleep(1000);
                                return null;
                            }

                        };
                        childController.displayinfo.textProperty().bind(task.messageProperty());
                        task.setOnSucceeded(s -> {
                            try {
                                childController.displayinfo.textProperty().unbind();
                                Stockin st = new Stockin();
                                st.setStockinId(selectedRecord.getStockinCode());
                                st.setBatchNo(childController.batchtextfield.getText());
                                st.setItems(new Items(childController.itemname.getText()));
                                st.setExpiryDate(Utilities.convertToDateViaSqlDate(childController.expirydate.getValue()));
                                st.setCostPrice(Double.parseDouble(childController.costtextfield.getText()));
                                st.setSalesPrice(Double.parseDouble(childController.salestextfield.getText()));
                                st.setNhisPrice(Double.parseDouble(childController.nhistextfield.getText()));
                                st.setQuantity(Integer.parseInt(childController.qnttextfield.getText()));
                                Date date = Utilities.convertStringToDate(selectedRecord.getStockinDate());
                                st.setStockinDate(date);
                                st.setUsers(new Users(LoginController.u.getUserid()));
                                st.setEntryLog(new Date());
                                st.setLastModified(new Date());
                                int result = new InsertUpdateBL().updateData(st);
                                switch (result) {
                                    case 1:
                                        childController.displayinfo.setText("SUCCESSFULLY SAVED");
                                        Utilities.clearAllField(childController.stockpane);
                                        childController.spinner.setVisible(false);
                                        childController.check.setVisible(true);
                                        list = stock.getSelectionModel().getSelectedItem();
                                        AllStockTableData();
                                        stockin.getItems().clear();
                                        stockout.getItems().clear();
                                        stage.close();
                                        break;
                                    default:
                                        childController.displayinfo.setText("NOTICE! AN ERROR OCCURED");
                                        childController.spinner.setVisible(false);
                                        childController.check.setVisible(false);
                                        break;

                                }
                            } catch (ParseException ex) {
                                Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public class AddPersonCellStockout extends TableCell<StockoutTableModel, Boolean> {

//        Image img = new Image(getClass().getResourceAsStream("edit.png"));
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
        AddPersonCellStockout() {
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
                        StockoutTableModel selectedRecord = (StockoutTableModel) stockout.getItems().get(selectdIndex);
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Delete.fxml"));
                        Parent parent = (Parent) fxmlLoader.load();
                        DeleteController childController = fxmlLoader.getController();
                        childController.delete.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                            childController.displayinfo.setText("PROCESSING PLS WAIT.....");
                            Task<Void> task = new Task<Void>() {
                                @Override
                                protected Void call() throws Exception {
                                    childController.spinner.setVisible(true);
                                    updateMessage("Processing...");
                                    Thread.sleep(1000);
                                    return null;
                                }
                            };
                            task.setOnSucceeded(f -> {
                                int result = new StockoutBL().removeData(selectedRecord.getStockoutId());
                                switch (result) {
                                    case 1:
                                        childController.displayinfo.setText("SUCCESSFULLY DELETED");
                                        childController.spinner.setVisible(false);
                                        childController.check.setVisible(true);
                                        AllStockTableData();
                                        StockoutTableData(selectedRecord.getBatchNo());
                                        stage.close();
                                        break;
                                    default:
                                        childController.displayinfo.setText("NOTICE! AN ERROR OCCURED");
                                        childController.spinner.setVisible(false);
                                        childController.check.setVisible(false);
                                        break;

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
                        Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
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
