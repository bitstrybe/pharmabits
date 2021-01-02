package lxe.pharmabitretail.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import lxe.pharmabitretail.bl.InsertUpdateBL;
import lxe.pharmabitretail.bl.ItemsBL;
import lxe.pharmabitretail.bl.ReceiptBL;
import lxe.pharmabitretail.bl.SalesBL;
import lxe.pharmabitretail.bl.SalesDetailsBL;
import lxe.pharmabitretail.bl.StockinBL;
import lxe.pharmabitretail.entity.Receipt;
import lxe.pharmabitretail.entity.Sales;
import lxe.pharmabitretail.entity.SalesDetails;
import lxe.pharmabitretail.entity.Stockin;
import lxe.pharmabitretail.entity.Users;
import lxe.pharmabitretail.tablemodel.ReceiptTableModel;
import lxe.pharmabitretail.tablemodel.SalesDetailsTableModel;
import lxe.pharmabitretail.tablemodel.SalesTableModel;
import lxe.pharmabitretail.tablemodel.SelectItemSaleTableModel;
import lxe.pharmabitretail.utils.PrintReport;
import lxe.pharmabitretail.utils.Utilities;
import net.sf.jasperreports.engine.JRException;

/**
 * FXML Controller class
 *
 * @author JScare
 */
public class StockNController implements Initializable {

    private final ObservableList<SelectItemSaleTableModel> data = FXCollections.observableArrayList();
    ObservableList<String> search;
    ObservableList<SalesTableModel> salesdata;
    ObservableList<SalesDetailsTableModel> salesdetailsdata;
//    private final ObservableList<Person> data
//            = FXCollections.observableArrayList();

    @FXML
    private JFXListView<String> listviewsales;
    @FXML
    private JFXTextField itemsearch;
    @FXML
    private TableView<SelectItemSaleTableModel> seleteditemtableview;
    @FXML
    private TableColumn<SelectItemSaleTableModel, String> batchcode;
    @FXML
    private TableColumn<SelectItemSaleTableModel, String> itemname;
    @FXML
    private TableColumn<SelectItemSaleTableModel, Number> qnt;
    @FXML
    private TableColumn<SelectItemSaleTableModel, Number> price;
    @FXML
    private TableColumn<SelectItemSaleTableModel, Number> total;

    @FXML
    private TableColumn<SelectItemSaleTableModel, Number> nhisvalprice;
    @FXML
    private TableColumn<SelectItemSaleTableModel, String> nhis;
    @FXML
    private TableColumn<SelectItemSaleTableModel, Boolean> action;
    @FXML
    private TableColumn<SelectItemSaleTableModel, Boolean> discount;
    @FXML
    private TableColumn<SelectItemSaleTableModel, Number> discountval;
    @FXML
    private Button itemaddbtn;
    @FXML
    private JFXButton sellbtn;
    @FXML
    private Label totalprice;
    //Sales Table
    @FXML
    private TableView<SalesTableModel> salestable;
    @FXML
    private TableColumn<SalesTableModel, Number> salecode;
    @FXML
    private TableColumn<SalesTableModel, Number> saleprice;
    @FXML
    private TableColumn<SalesTableModel, String> date;
    @FXML
    private TableColumn<SalesTableModel, Number> paidvalue;
    @FXML
    private TableColumn<SalesTableModel, String> salesbalance;
    @FXML
    private TableColumn<SalesTableModel, Boolean> action1;
    @FXML
    private TableColumn<SalesTableModel, Boolean> action2;
    @FXML
    private TableColumn<SalesTableModel, Boolean> actionsales;
    //Sales Details Table
    @FXML
    private TableView<SalesDetailsTableModel> salesdetailstable;
    @FXML
    private TableColumn<SalesDetailsTableModel, String> batchno;
    @FXML
    private TableColumn<SalesDetailsTableModel, Number> scode;
    @FXML
    private TableColumn<SalesDetailsTableModel, Number> qty;
    @FXML
    private TableColumn<SalesDetailsTableModel, Number> sp;
    @FXML
    private TableColumn<SalesDetailsTableModel, Number> np;
    @FXML
    private TableColumn<SalesDetailsTableModel, String> ns;
    @FXML
    private TableColumn<SalesDetailsTableModel, Number> discountsalesdetails;
    @FXML
    private DatePicker startdate;
    @FXML
    private DatePicker enddate;
    @FXML
    private Label totalsalesprice;

    DecimalFormat df = new DecimalFormat("0.00");
    SalesBL sb = new SalesBL();
    ReceiptBL rec = new ReceiptBL();
    double totalp;
    @FXML
    private Button removeitem;
    @FXML
    private Button salespdfbtn;

    static int bal = 0;
    static int qntval1 = 0;
    static int stockbal = 0;
    String lsv;
    @FXML
    private AnchorPane saleanchorpane;
    @FXML
    private JFXButton cuspopup;

    public void getStockingItemList() {
        StockinBL sk = new StockinBL();
        List<Stockin> list = sk.getAllStockinGroup();
        ObservableList<Stockin> result = FXCollections.observableArrayList(list);
        listviewsales.getItems().clear();
//        listviewsales.setOrientation(Orientation.HORIZONTAL);
        result.forEach((man) -> {
            long balance = sk.getStockBalance(man.getBatchNo());
            if (balance != 0) {
                listviewsales.getItems().add(man.getBatchNo() + ": " + man.getItems().getItemDesc().toUpperCase() + " -> " + balance + " Remaining");
                listviewsales.setCellFactory(value -> new ListCell<String>() {
                    private VBox vb = new VBox();
                    private ImageView imageView = new ImageView();
                    private Label lb = new Label();

                    @Override
                    public void updateItem(String name, boolean empty) {
                        super.updateItem(name, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            try {
                                imageView.setFitWidth(200);
                                imageView.setPreserveRatio(true);
                                imageView.setSmooth(true);
                                imageView.setCache(true);
                                vb.setAlignment(Pos.CENTER);
                                FileInputStream input;
                                input = new FileInputStream(man.getItems().getItemImg());
                                Image image = new Image(input);
                                imageView.setImage(image);
                                lb.setText(man.getBatchNo() + ": " + man.getItems().getItemDesc().toUpperCase() + " -> " + balance + " Remaining");
                                vb.getChildren().addAll(imageView, lb);
                                setGraphic(vb);

                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });

            }
        });
    }

    public void SearchStockingItemList(String p) {
        StockinBL sb = new StockinBL();
        List<Stockin> list = sb.searchAllStockinGroup(p);
        ObservableList<Stockin> result = FXCollections.observableArrayList(list);
        listviewsales.getItems().clear();
        listviewsales.setOrientation(Orientation.HORIZONTAL);
        result.forEach((man) -> {
            long balance = sb.getStockBalance(man.getBatchNo());
            if (balance != 0) {
                listviewsales.getItems().add(man.getBatchNo() + ": " + man.getItems().getItemDesc().toUpperCase() + " -> " + balance + " Remaining");
            }
        });

    }

    public List getBatchNo() {
        List<String> columnData = new ArrayList<>();
        for (SelectItemSaleTableModel item : seleteditemtableview.getItems()) {
            columnData.add(batchcode.getCellObservableValue(item).getValue());
        }
        return columnData;
    }

    public List getPrice() {
        List<Number> columnData = new ArrayList<>();
        for (SelectItemSaleTableModel item : seleteditemtableview.getItems()) {
            Number qunt = qnt.getCellObservableValue(item).getValue();
            Number nhisps = nhisvalprice.getCellObservableValue(item).getValue();
            double actprice = qnt.getCellObservableValue(item).getValue().doubleValue() * price.getCellObservableValue(item).getValue().doubleValue();
            double discount = discountval.getCellObservableValue(item).getValue().doubleValue();
            double nhistotal = qunt.intValue() * nhisps.doubleValue();
            double nhistopup = actprice - nhistotal;

            if (nhis.getCellObservableValue(item).getValue().equals("INACTIVE")) {
                if (discount > 0) {
                    double discountval2 = discount / 100;
                    double disactval = actprice * discountval2;
                    columnData.add(actprice - disactval);
                } else {
                    columnData.add(actprice);
                }

            } else if (nhis.getCellObservableValue(item).getValue().equals("ACTIVE")) {
                if (discount > 0) {
                    double discountval2 = discount / 100;
                    double disactval = nhistopup * discountval2;
//                System.out.println(nhistopup - Utilities.roundToTwoDecimalPlace(disactval, 2));
                    System.out.println(nhistopup);
                    columnData.add(nhistopup - disactval);
                } else {
                    columnData.add(nhistopup);
                }

            }

        }
        return columnData;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getTodaysDate();
        getStockingItemList();

        itemsearch.textProperty().addListener(e -> {
            if (itemsearch.getText().length() > 1) {
                SearchStockingItemList(itemsearch.getText());
            } else {
                getStockingItemList();
            }
        });

        listviewsales.setOnMouseClicked(r -> {
            if (r.getClickCount() == 2) {
                try {
                    addtoitemtable();
                } catch (IOException ex) {
                    Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        seleteditemtableview.setOnMouseClicked(a -> {
            if (a.getClickCount() == 2) {
                removeItemsFromTable();
            }
        });

    }

    public void getTodaysDate() {
        Date dd = new Date(System.currentTimeMillis());
        SalesTableData(dd, dd);
        startdate.setValue(Utilities.convertDateToLocalDate(new Date(System.currentTimeMillis())));
        enddate.setValue(Utilities.convertDateToLocalDate(new Date(System.currentTimeMillis())));
    }

    @FXML
    private void addtoitemtable() throws IOException {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddSalesQuantity.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            AddSalesQuantityController childController = fxmlLoader.getController();
            String[] listval = listviewsales.getSelectionModel().getSelectedItem().split("[:>]");
            List<String> spiltedval = Arrays.asList(listval);
            List<Stockin> s = new StockinBL().getItemStockinBatchNo(spiltedval.get(0));
            childController.itemname.setText(spiltedval.get(1));
            String qntval = spiltedval.get(2);
            String qnttrim = qntval.trim();
            String[] qntlist = qnttrim.split("\\s+");
            List<String> splitqnt = Arrays.asList(qntlist);
//        System.out.println(splitqnt.get(0));
            childController.stockval.setText(splitqnt.get(0));

            childController.qnttextfield.textProperty().addListener(e -> {
                try {
                    qntval1 = Integer.parseInt(childController.qnttextfield.getText());
                    stockbal = Integer.parseInt(splitqnt.get(0));
                    bal = stockbal - qntval1;
                    childController.stockval.setText(String.valueOf(bal));

                    if (childController.qnttextfield.getLength() >= 1 && qntval1 != 0) {
                        childController.additem.setDisable(false);

                        System.out.println("QTY : " + qntval1);
//                        double balval = Double.parseDouble(childController.stockval.getText());
                        if (bal < 0) {

                            childController.stockval.setText(String.valueOf(bal));
                            childController.additem.setDisable(true);
                            System.out.println(childController.stockval.getText());
                        } else {
                            childController.qnttextfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                @Override
                                public void handle(KeyEvent event) {
                                    if (bal >= 0) {
                                        if (event.getCode() == KeyCode.ENTER) {
                                            try {
                                                double totalval = Double.parseDouble(childController.qnttextfield.getText()) * s.get(0).getSalesPrice();
                                                double formatprice = Double.parseDouble(df.format(s.get(0).getSalesPrice()));
                                                double formatcost = Double.parseDouble(df.format(s.get(0).getCostPrice()));
                                                double formatotal = Double.parseDouble(df.format(totalval));
                                                double nhisprice = Double.parseDouble(df.format(s.get(0).getNhisPrice()));

                                                ObservableList<SelectItemSaleTableModel> ls = seleteditemtableview.getItems();
                                                if (ls.isEmpty()) {
                                                    data.add(new SelectItemSaleTableModel(s.get(0).getItems().getItemDesc().toUpperCase(), s.get(0).getBatchNo(), Integer.parseInt(childController.qnttextfield.getText()), formatcost, formatprice, formatotal, nhisprice, "INACTIVE", 0.00));
                                                    stage.close();
                                                } else {
                                                    if (getBatchNo().contains(spiltedval.get(0))) {
                                                        System.out.println("True");
                                                        childController.stockval.setFont(Font.font(12));
                                                        childController.stockval.setText("Already Added");
                                                    } else {
                                                        data.add(new SelectItemSaleTableModel(s.get(0).getItems().getItemDesc().toUpperCase(), s.get(0).getBatchNo(), Integer.parseInt(childController.qnttextfield.getText()), formatcost, formatprice, formatotal, nhisprice, "INACTIVE", 0.00));
                                                        stage.close();
                                                    }
                                                }
                                                itemname.setCellValueFactory(cell -> cell.getValue().getItemNameProperty());
                                                batchcode.setCellValueFactory(cell -> cell.getValue().getBatchCodeProperty());
                                                qnt.setCellValueFactory(cell -> cell.getValue().getQuantityProperty());
                                                price.setCellValueFactory(cell -> cell.getValue().getPriceProperty());
                                                total.setCellValueFactory(cell -> cell.getValue().getTotalProperty());
                                                nhisvalprice.setCellValueFactory(cell -> cell.getValue().getNHISPriceProperty());
                                                nhis.setCellValueFactory(cell -> cell.getValue().getNHISProperty());
                                                discountval.setCellValueFactory(cell -> cell.getValue().getDiscountValueProperty());
                                                discount.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SelectItemSaleTableModel, Boolean>, ObservableValue<Boolean>>() {
                                                    @Override
                                                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SelectItemSaleTableModel, Boolean> features) {
                                                        return new SimpleBooleanProperty(features.getValue() != null);
                                                    }
                                                });

                                                discount.setCellFactory(new Callback<TableColumn<SelectItemSaleTableModel, Boolean>, TableCell<SelectItemSaleTableModel, Boolean>>() {
                                                    @Override
                                                    public TableCell<SelectItemSaleTableModel, Boolean> call(TableColumn<SelectItemSaleTableModel, Boolean> personBooleanTableColumn) {
                                                        return new AddPersonDiscountCell();
                                                    }
                                                });

                                                action.setSortable(false);
                                                action.setMaxWidth(480);

                                                action.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SelectItemSaleTableModel, Boolean>, ObservableValue<Boolean>>() {
                                                    @Override
                                                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SelectItemSaleTableModel, Boolean> features) {
                                                        return new SimpleBooleanProperty(features.getValue() != null);
                                                    }
                                                });

                                                action.setCellFactory(new Callback<TableColumn<SelectItemSaleTableModel, Boolean>, TableCell<SelectItemSaleTableModel, Boolean>>() {
                                                    @Override
                                                    public TableCell<SelectItemSaleTableModel, Boolean> call(TableColumn<SelectItemSaleTableModel, Boolean> personBooleanTableColumn) {
                                                        return new AddPersonCell();
                                                    }
                                                });

                                                seleteditemtableview.setItems(data);
                                                seleteditemtableview.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                                                totalp = Float.parseFloat(df.format(Utilities.sumList(getPrice())));
                                                totalprice.setText(df.format(totalp));

                                            } catch (Exception ex) {
                                                childController.stockval.setText("Invalid Format");

                                            }
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        childController.additem.setDisable(true);
                        childController.stockval.setText(splitqnt.get(0));
                    }
                } catch (Exception ex) {
                    childController.stockval.setText(splitqnt.get(0));
                    System.out.println("null");
                }

            });

            childController.additem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    try {
                        double totalval = Double.parseDouble(childController.qnttextfield.getText()) * s.get(0).getSalesPrice();
                        double formatprice = Double.parseDouble(df.format(s.get(0).getSalesPrice()));
                        double formatcost = Double.parseDouble(df.format(s.get(0).getCostPrice()));
                        double formatotal = Double.parseDouble(df.format(totalval));
                        double nhisprice = Double.parseDouble(df.format(s.get(0).getNhisPrice()));

                        ObservableList<SelectItemSaleTableModel> ls = seleteditemtableview.getItems();
                        if (ls.isEmpty()) {
                            data.add(new SelectItemSaleTableModel(s.get(0).getItems().getItemDesc().toUpperCase(), s.get(0).getBatchNo(), Integer.parseInt(childController.qnttextfield.getText()), formatcost, formatprice, formatotal, nhisprice, "INACTIVE", 0.00));
                            stage.close();
                        } else {
                            if (getBatchNo().contains(spiltedval.get(0))) {
                                System.out.println("True");
                                childController.stockval.setFont(Font.font(12));
                                childController.stockval.setText("Already Added");
                            } else {
                                data.add(new SelectItemSaleTableModel(s.get(0).getItems().getItemDesc().toUpperCase(), s.get(0).getBatchNo(), Integer.parseInt(childController.qnttextfield.getText()), formatcost, formatprice, formatotal, nhisprice, "INACTIVE", 0.00));
                                stage.close();
                            }
                        }
                        itemname.setCellValueFactory(cell -> cell.getValue().getItemNameProperty());
                        batchcode.setCellValueFactory(cell -> cell.getValue().getBatchCodeProperty());
                        qnt.setCellValueFactory(cell -> cell.getValue().getQuantityProperty());
                        price.setCellValueFactory(cell -> cell.getValue().getPriceProperty());
                        total.setCellValueFactory(cell -> cell.getValue().getTotalProperty());
                        nhisvalprice.setCellValueFactory(cell -> cell.getValue().getNHISPriceProperty());
                        nhis.setCellValueFactory(cell -> cell.getValue().getNHISProperty());
                        discount.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SelectItemSaleTableModel, Boolean>, ObservableValue<Boolean>>() {
                            @Override
                            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SelectItemSaleTableModel, Boolean> features) {
                                return new SimpleBooleanProperty(features.getValue() != null);
                            }
                        });

                        discount.setCellFactory(new Callback<TableColumn<SelectItemSaleTableModel, Boolean>, TableCell<SelectItemSaleTableModel, Boolean>>() {
                            @Override
                            public TableCell<SelectItemSaleTableModel, Boolean> call(TableColumn<SelectItemSaleTableModel, Boolean> personBooleanTableColumn) {
                                return new AddPersonDiscountCell();
                            }
                        });
                        discountval.setCellValueFactory(cell -> cell.getValue().getDiscountValueProperty());

                        action.setSortable(false);
                        action.setMaxWidth(480);

                        action.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SelectItemSaleTableModel, Boolean>, ObservableValue<Boolean>>() {
                            @Override
                            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SelectItemSaleTableModel, Boolean> features) {
                                return new SimpleBooleanProperty(features.getValue() != null);
                            }
                        });

                        action.setCellFactory(new Callback<TableColumn<SelectItemSaleTableModel, Boolean>, TableCell<SelectItemSaleTableModel, Boolean>>() {
                            @Override
                            public TableCell<SelectItemSaleTableModel, Boolean> call(TableColumn<SelectItemSaleTableModel, Boolean> personBooleanTableColumn) {
                                return new AddPersonCell();
                            }
                        });

                        seleteditemtableview.setItems(data);
                        seleteditemtableview.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                        totalp = Float.parseFloat(df.format(Utilities.sumList(getPrice())));
                        totalprice.setText(df.format(totalp));

                    } catch (Exception ex) {
                        childController.stockval.setText("Invalid Format");

                    }

                }
            });
            Scene scene = new Scene(parent);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(parent.getScene().getWindow());
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.resizableProperty().setValue(false);
            stage.showAndWait();
        } catch (Exception ex) {
            System.out.println("null");

        }

    }

    @FXML
    private void salesPDF(ActionEvent event) {
        try {
            if (LoginController.u.getRoles().equals("Administrator")) {
                new PrintReport().showSalesDateRangeReport(Utilities.convertToDateViaSqlDate(startdate.getValue()), Utilities.convertToDateViaSqlDate(enddate.getValue()));
            } else {
                new PrintReport().showSalesUserDateRangeReport(Utilities.convertToDateViaSqlDate(startdate.getValue()), Utilities.convertToDateViaSqlDate(enddate.getValue()));

            }
        } catch (JRException ex) {
            Logger.getLogger(SalesController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(SalesController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(SalesController.class
                    .getName()).log(Level.SEVERE, null, ex);

        }
    }

    @FXML
    private void clearall(ActionEvent event) {
        seleteditemtableview.getItems().clear();
        sellbtn.setText("SALES");
        salesdetailstable.getItems().clear();
        getStockingItemList();
        Date dd = new Date(System.currentTimeMillis());
        SalesTableData(dd, dd);
        totalprice.setText(null);
    }

    public class AddPersonCell extends TableCell<SelectItemSaleTableModel, Boolean> {

        //Image img = new Image(getClass().getResourceAsStream("edit.png"));
        Image img2 = new Image(getClass().getResourceAsStream("/resources/addnhis.png"));
        // a button for adding a new person.
        JFXButton nhisButton = new JFXButton();
        // pads and centers the add button in the cell.
        HBox paddedButton = new HBox();

        // records the y pos of the last button press so that the add person dialog can be shown next to the cell.
        final DoubleProperty buttonY = new SimpleDoubleProperty();

        /**
         * AddPersonCell constructor
         *
         * @param stage the stage in which the table is placed.
         * @param table the table to which a new person can be added.
         */
        AddPersonCell() {
            paddedButton.getChildren().add(nhisButton);
            nhisButton.setGraphic(new ImageView(img2));
//            delButton.setRipplerFill(Paint.valueOf("#D8E1DC"));
//            nhisButton.setCheckedColor(Paint.valueOf("#6699ff"));
            nhisButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    try {
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddNHIS.fxml"));
                        Parent parent = (Parent) fxmlLoader.load();
                        AddNHISController childController = fxmlLoader.getController();
                        childController.closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, m -> {
                            stage.close();
                        });
                        childController.addnhis.addEventHandler(MouseEvent.MOUSE_CLICKED, a -> {
                            int selectdIndex = getTableRow().getIndex();
                            //Create a new table show details of the selected item

                            nhisvalprice.setCellValueFactory(cell -> cell.getValue().getNHISPriceProperty());
                            ObservableList<SelectItemSaleTableModel> selectedRecord = seleteditemtableview.getItems();

//                                selectedRecord.setNHISPriceProperty(Double.parseDouble(childController.nhistextfield.getText()));
//                                checkbox.setText(childController.nhistextfield.getText());
//                                selectedRecord.setNHISPriceProperty(Double.parseDouble(childController.nhistextfield.getText()));
                            selectedRecord.forEach(e -> {
                                if (childController.nhistoggle.isSelected()) {
                                    e.setNHISProperty("ACTIVE");
                                } else {
                                    e.setNHISProperty("INACTIVE");
                                }
                            });
                            totalp = Float.parseFloat(df.format(Utilities.sumList(getPrice())));
                            totalprice.setText(df.format(totalp));
                            // seleteditemtableview.refresh();
                            stage.close();
                        });
                        Scene scene = new Scene(parent);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parent.getScene().getWindow());
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.resizableProperty().setValue(false);
                        stage.showAndWait();
                    } catch (IOException ex) {
                        Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            });
//            checkbox.setSelected(true);

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

    public class AddPersonDiscountCell extends TableCell<SelectItemSaleTableModel, Boolean> {

        //Image img = new Image(getClass().getResourceAsStream("edit.png"));
        Image img2 = new Image(getClass().getResourceAsStream("/resources/discount.png"));
        // a button for adding a new person.
        JFXButton nhisButton = new JFXButton();
        // pads and centers the add button in the cell.
        HBox paddedButton = new HBox();
        HBox discountButton = new HBox();
        Label disval = new Label();

        // records the y pos of the last button press so that the add person dialog can be shown next to the cell.
        final DoubleProperty buttonY = new SimpleDoubleProperty();

        /**
         * AddPersonCell constructor
         *
         * @param stage the stage in which the table is placed.
         * @param table the table to which a new person can be added.
         */
        AddPersonDiscountCell() {
            paddedButton.setStyle("-fx-alignment: CENTER;");
            discountButton.setStyle("-fx-alignment: CENTER;");
            paddedButton.getChildren().add(nhisButton);
            nhisButton.setGraphic(new ImageView(img2));

//            delButton.setRipplerFill(Paint.valueOf("#D8E1DC"));
//            nhisButton.setCheckedColor(Paint.valueOf("#6699ff"));
            nhisButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    try {
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Discount.fxml"));
                        Parent parent = (Parent) fxmlLoader.load();
                        DiscountController childController = fxmlLoader.getController();

                        childController.adddiscount.addEventHandler(MouseEvent.MOUSE_CLICKED, a -> {
                            int selectdIndex = getTableRow().getIndex();
                            //Create a new table show details of the selected item
                            SelectItemSaleTableModel selectedRecord = (SelectItemSaleTableModel) seleteditemtableview.getItems().get(selectdIndex);
                            selectedRecord.setDiscountValueProperty(Double.parseDouble(childController.discounttextfield.getText()));
//                            totalp = Float.parseFloat(df.format(Utilities.sumList(getPrice())));
//                            float totaldis = Float.parseFloat(df.format(Utilities.sumList(getDiscount())));
//                            totalp = totaldis;
//                            System.out.println(totalp);
//                            totalprice.setText(df.format(totalp));
                            if (selectedRecord.getDiscountValue() >= 0) {
                                totalp = Float.parseFloat(df.format(Utilities.sumList(getPrice())));
                                totalprice.setText(df.format(totalp));
                                // seleteditemtableview.refresh();
                                stage.close();
                            }

                        });
                        Scene scene = new Scene(parent);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parent.getScene().getWindow());
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.resizableProperty().setValue(false);
                        stage.showAndWait();
                    } catch (IOException ex) {
                        Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            });
//            checkbox.setSelected(true);

        }

        /**
         * places an add button in the row only if the row is not empty.
         */
        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
//                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(paddedButton);
            } else {
                setGraphic(null);
            }
        }
    }

    @FXML
    public void makeSalesAction() throws IOException {
        if (sellbtn.getText().equals("SALES")) {
//            String[] customer = customerlabel.getText().split(":");
//            List<String> spiltedval = Arrays.asList(customer[0].trim());
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SalesSlip.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            SalesSlipController childController = fxmlLoader.getController();
            childController.totalitemprice.setText(df.format(totalp));
            childController.confirmheader.setText("CONFIRM SAVE");
            childController.confirm.addEventHandler(MouseEvent.MOUSE_CLICKED, a -> {
                childController.confirm.setDisable(true);
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
                task.setOnSucceeded(f -> {
                    try {
                        childController.displayinfo.textProperty().unbind();
                        Sales sale = new Sales();
                        List<Integer> salescount = sb.getSalesCount();
                        if (salescount.isEmpty()) {
                            int slc = 1;
                            sale.setSalesId(1);
                        } else {
                            int slc = salescount.get(0);
                            sale.setSalesId(++slc);
                        }

//                    System.out.println("Sales Count "+ ++slc);
//                        sale.setCustomer(new Customer(spiltedval.get(0)));
                        sale.setDateS(new Date(System.currentTimeMillis()));
                        sale.setUsers(new Users(LoginController.u.getUserid()));
                        sale.setEntryDate(new Date(System.currentTimeMillis()));
                        List<SalesDetails> sds = new ArrayList<>();
                        ObservableList<SelectItemSaleTableModel> list = seleteditemtableview.getItems();
                        list.forEach(e -> {
                            SalesDetails sd = new SalesDetails();
                            sd.setBatchNo(e.getBatchCode());
                            sd.setSaleId(sale);
                            sd.setQuantity(e.getQuantity());
                            sd.setCostPrice(e.getCost());
                            sd.setSalesPrice(e.getPrice());
                            sd.setNhisPrice(e.getNHISPrice());
                            sd.setDiscount(e.getDiscountValue());
                            sd.setUsers(new Users(LoginController.u.getUserid()));
                            sd.setEntryDate(new Date(System.currentTimeMillis()));
                            if (e.getNHIS().equals("ACTIVE")) {
                                sd.setNhisCondition(1);
                            } else {
                                sd.setNhisCondition(0);
                            }
                            sds.add(sd);
                        });
                        sale.setSalesDetailsCollection(sds);
                        int result1 = new InsertUpdateBL().insertData(sale);
                        //System.out.println("result: " + result1);
                        if (result1 == 1) {
                            childController.displayinfo.setText("SUCCESSFULLY SAVED");
                            childController.spinner.setVisible(false);
                            childController.check.setVisible(true);
                            getStockingItemList();
                            seleteditemtableview.getItems().clear();
                            Date dd = new Date(System.currentTimeMillis());
                            SalesTableData(dd, dd);
                            totalprice.setText(null);
                            stage.close();

                        } else {
                            System.out.println("Unable to save");
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    public void SalesTableData(Date startdate, Date enddate) {
        List<Sales> c = sb.getSalesDateRange(startdate, enddate);
        salesdata = FXCollections.observableArrayList();
        System.out.println("sales:" + c.size());
        c.forEach((s) -> {
            double totoalsalesnonhis;
            try {
                totoalsalesnonhis = Double.parseDouble(df.format(sb.getTotalSalesNoNHIS(s.getSalesId())));
            } catch (Exception ex) {
                totoalsalesnonhis = 0.0;
            }
            double totoalsalesnhis;
            try {
                totoalsalesnhis = Double.parseDouble(df.format(sb.getTotalSalesNHIS(s.getSalesId())));
            } catch (Exception ex) {
                totoalsalesnhis = 0.0;
            }
            double totalsales = totoalsalesnonhis + totoalsalesnhis;

//            double salesdiscount = 
            System.out.println("TOTAL SALES :" + (totalsales));
            double totalpaid;
            try {
                totalpaid = Double.parseDouble(df.format(rec.getTotalPaidbySalesCode(s.getSalesId())));
                System.out.println(totalpaid);
            } catch (IllegalArgumentException ex) {
                totalpaid = 0;
            }
            double balance = totalsales - totalpaid;
            String bal;
            if (balance <= 0) {
                bal = "Paid✔";
            } else {
                bal = String.valueOf(balance);
            }

//            double profit = Double.parseDouble(df.format(sb.getTotalSales(sales.getSalesId()) - sb.getTotalCost(sales.getSalesId())));
//            System.out.println(s.getCustomer().getFName() + " " + s.getCustomer().getLName());
//            String customerfullname = new CustomerBL().getCustomerNamebyId(s.getCustomer().getCustomerId());
            String userkey = s.getUsers().getFname() + " " + s.getUsers().getLname();
            Date entrylog = s.getEntryDate();
            salesdata.add(new SalesTableModel(s.getSalesId(), totalsales, totalpaid, bal, Utilities.convertDateToString(s.getDateS()), userkey, Utilities.convertDateToString(entrylog)));
        });
        salecode.setCellValueFactory(cell -> cell.getValue().getSalesIdProperty());
//        customer.setCellValueFactory(cell -> cell.getValue().getCustomerProperty());
        saleprice.setCellValueFactory(cell -> cell.getValue().getTotalSalesProperty());
        paidvalue.setCellValueFactory(cell -> cell.getValue().getTotalPaidProperty());
        salesbalance.setCellValueFactory(cell -> cell.getValue().getBalanceProperty());
        date.setCellValueFactory(cell -> cell.getValue().getDateProperty());
        //Preview Invoice For payment table start
        action1.setSortable(false);
        action1.setMaxWidth(480);

        action1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SalesTableModel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SalesTableModel, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        action1.setCellFactory(new Callback<TableColumn<SalesTableModel, Boolean>, TableCell<SalesTableModel, Boolean>>() {
            @Override
            public TableCell<SalesTableModel, Boolean> call(TableColumn<SalesTableModel, Boolean> personBooleanTableColumn) {
                return new AddPersonCellPayment();
            }
        });

        //Payment view table start
        action2.setSortable(false);
        action2.setMaxWidth(480);

        action2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SalesTableModel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SalesTableModel, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        action2.setCellFactory(new Callback<TableColumn<SalesTableModel, Boolean>, TableCell<SalesTableModel, Boolean>>() {
            @Override
            public TableCell<SalesTableModel, Boolean> call(TableColumn<SalesTableModel, Boolean> personBooleanTableColumn) {
                return new AddPersonCellPreview();
            }
        });
        actionsales.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SalesTableModel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SalesTableModel, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        actionsales.setCellFactory(new Callback<TableColumn<SalesTableModel, Boolean>, TableCell<SalesTableModel, Boolean>>() {
            @Override
            public TableCell<SalesTableModel, Boolean> call(TableColumn<SalesTableModel, Boolean> personBooleanTableColumn) {
                return new AddPersonCellSales();
            }
        });

        if (LoginController.u.getRoles().equals("Administrator")) {
            salestable.setRowFactory(tv -> new TableRow<SalesTableModel>() {
                private Tooltip tooltip = new Tooltip();

                @Override
                public void updateItem(SalesTableModel person, boolean empty) {
                    super.updateItem(person, empty);
                    if (person == null) {
                        setTooltip(null);
                    } else {
                        tooltip.setText(person.getUser() + "\n " + person.getEntryLog());
                        setTooltip(tooltip);
                    }
                }
            });
        }

        salestable.setItems(salesdata);
//        clientTable.getColumns().add(action);
        salestable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        salestable.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                SalesTableModel t = salestable.getSelectionModel().getSelectedItem();
                List<SalesDetails> sd = sb.getAllSalesDetailsbySalesCode(t.getSalesId());
                double totoalsalesnonhis;
                try {
                    totoalsalesnonhis = Double.parseDouble(df.format(sb.getTotalSalesNoNHIS(t.getSalesId())));
                } catch (Exception ex) {
                    totoalsalesnonhis = 0.0;
                }
                double totoalsalesnhis;
                try {
                    totoalsalesnhis = Double.parseDouble(df.format(sb.getTotalSalesNHIS(t.getSalesId())));
                } catch (Exception ex) {
                    totoalsalesnhis = 0.0;
                }
                double totalsales = totoalsalesnonhis + totoalsalesnhis;
//                double totalsales = Double.parseDouble(df.format(sb.getTotalSales(t.getSalesId())));
                totalsalesprice.setText(String.valueOf(totalsales));
                salesdetailsdata = FXCollections.observableArrayList();
//
                sd.forEach((sales) -> {
                    String nhisstatus;
                    if (sales.getNhisCondition() == 1) {
                        nhisstatus = "ACTIVE";
                    } else {
                        nhisstatus = "INACTIVE";
                    }
                    salesdetailsdata.add(new SalesDetailsTableModel(sales.getSalesDetailsId(), sales.getSaleId().getSalesId(), sales.getBatchNo(), sales.getQuantity(), Double.parseDouble(df.format(sales.getSalesPrice())), Double.parseDouble(df.format(sales.getNhisPrice())), nhisstatus, sales.getDiscount()));
                });
                batchno.setCellValueFactory(cell -> cell.getValue().getBatchNoProperty());
                scode.setCellValueFactory(cell -> cell.getValue().getSalesIdProperty());
                qty.setCellValueFactory(cell -> cell.getValue().getQuantityProperty());
                sp.setCellValueFactory(cell -> cell.getValue().getSalesPriceProperty());
                np.setCellValueFactory(cell -> cell.getValue().getNHISPriceProperty());
                ns.setCellValueFactory(cell -> cell.getValue().getNHISStatusProperty());
                discountsalesdetails.setCellValueFactory(cell -> cell.getValue().getDiscountProperty());
//
                salesdetailstable.setItems(salesdetailsdata);
                salesdetailstable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            } catch (Exception ex) {
                System.out.println("null");
            }
//            
        });

    }

    public class AddPersonCellPayment extends TableCell<SalesTableModel, Boolean> {

        double amountpaid;

        Image img = new Image(getClass().getResourceAsStream("/resources/pay.png"));
//        Image imgpaid = new Image(getClass().getResourceAsStream("/resources/tick.png"));
//        Image img2 = new Image(getClass().getResourceAsStream("delete.png"));
        // a button for adding a new person.
//        JFXButton addButton = new JFXButton();
        // pads and centers the add button in the cell.
//        Label label = new Label("Paid");
        HBox paddedButton = new HBox();

        JFXButton payButton = new JFXButton();
        // records the y pos of the last button press so that the add person dialog can be shown next to the cell.
        final DoubleProperty buttonY = new SimpleDoubleProperty();

        /**
         * AddPersonCell constructor
         *
         * @param stage the stage in which the table is placed.
         * @param table the table to which a new person can be added.
         */
        AddPersonCellPayment() {
            paddedButton.setStyle("-fx-alignment: CENTER;");
            paddedButton.getChildren().add(payButton);
//            delButton.setGraphic(new ImageView(img2));
//            delButton.setRipplerFill(Paint.valueOf("#D8E1DC"));
            payButton.setGraphic(new ImageView(img));
            payButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddPayment.fxml"));
                        Parent parent = (Parent) fxmlLoader.load();
                        AddPaymentController childController = fxmlLoader.getController();
                        int selectdIndex = getTableRow().getIndex();
                        SalesTableModel selectedRecord = (SalesTableModel) salestable.getItems().get(selectdIndex);
                        childController.paymentTableData(selectedRecord.getSalesId());
//                        childController.customername.setText(selectedRecord.getCustomer());
                        double totoalsalesnonhis;
                        try {
                            totoalsalesnonhis = Double.parseDouble(df.format(sb.getTotalSalesNoNHIS(selectedRecord.getSalesId())));
                        } catch (Exception ex) {
                            totoalsalesnonhis = 0.0;
                        }
                        double totoalsalesnhis;
                        try {
                            totoalsalesnhis = Double.parseDouble(df.format(sb.getTotalSalesNHIS(selectedRecord.getSalesId())));
                        } catch (Exception ex) {
                            totoalsalesnhis = 0.0;
                        }
                        double totalsales = totoalsalesnonhis + totoalsalesnhis;
                        try {
                            amountpaid = Double.parseDouble(df.format(rec.getTotalPaidbySalesCode(selectedRecord.getSalesId())));
                        } catch (IllegalArgumentException ex) {
                            amountpaid = 0;

                        }
                        childController.totalbill.setText(String.valueOf(amountpaid));
                        double balance = totalsales - amountpaid;
                        childController.pendingbill.setText(df.format(balance));
                        childController.amountpaid.textProperty().addListener(e -> {
                            if (!childController.amountpaid.getText().isEmpty() && !childController.amountpaid.getText().equals("0")) {
                                try {
                                    childController.save.setDisable(false);
                                    double newbal = balance - Double.parseDouble(childController.amountpaid.getText());
                                    childController.pendingbill.setText(df.format(newbal));
                                } catch (Exception ex) {
                                    childController.pendingbill.setText(df.format(balance));

                                }
                                double penbill = Double.parseDouble(childController.pendingbill.getText());

//                                if (penbill < 0) {
//                                    childController.save.setDisable(true);
//                                } else {
//                                    childController.save.setDisable(false);
//                                }
                            } else {
                                childController.save.setDisable(true);
                                childController.pendingbill.setText(df.format(balance));
                            }

                        });
                        childController.deletereceipt.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                            try {
                                ReceiptTableModel selecteditemval = childController.paymenttable.getSelectionModel().getSelectedItem();
                                System.out.println(selecteditemval.getReceiptId());
                                Stage stagedel = new Stage();
                                FXMLLoader fxmlLoaderdel = new FXMLLoader(getClass().getResource("Delete.fxml"));
                                Parent parentdel = (Parent) fxmlLoaderdel.load();
                                DeleteController childControllerdel = fxmlLoaderdel.getController();
                                childControllerdel.delete.addEventHandler(MouseEvent.MOUSE_CLICKED, s -> {
                                    childControllerdel.displayinfo.setText("PROCESSING PLS WAIT.....");
                                    Task<Void> task = new Task<Void>() {
                                        @Override
                                        protected Void call() throws Exception {
                                            childControllerdel.spinner.setVisible(true);
                                            updateMessage("Processing......");
                                            Thread.sleep(1000);
                                            return null;
                                        }
                                    };
                                    task.setOnSucceeded(f -> {
                                        int result = new ReceiptBL().removeData(selecteditemval.getReceiptId());
                                        switch (result) {
                                            case 1:
                                                childControllerdel.displayinfo.setText("SUCCESSFULLY DELETED");
                                                childControllerdel.spinner.setVisible(false);
                                                childControllerdel.check.setVisible(true);
                                                childController.paymentTableData(selecteditemval.getSalesId());
                                                Date dd = new Date(System.currentTimeMillis());
                                                SalesTableData(dd, dd);
//                                                double totoalsalesnonhis;
//                                                try {
//                                                    totoalsalesnonhis = Double.parseDouble(df.format(sb.getTotalSalesNoNHIS(selectedRecord.getSalesId())));
//                                                } catch (Exception ex) {
//                                                    totoalsalesnonhis = 0.0;
//                                                }
//                                                double totoalsalesnhis;
//                                                try {
//                                                    totoalsalesnhis = Double.parseDouble(df.format(sb.getTotalSalesNHIS(selectedRecord.getSalesId())));
//                                                } catch (Exception ex) {
//                                                    totoalsalesnhis = 0.0;
//                                                }
//                                                double totalsales = totoalsalesnonhis + totoalsalesnhis;
//                                                double bal = totalsales - selectedRecord.getAmountPaid();
//                                                totalbill.setText(String.valueOf(bal));
////                                         double pend = selectedRecord.getAmountPaid() - bal;
////                                          pendingbill.setText(String.valueOf(pend));

                                                break;
                                            default:
                                                childControllerdel.displayinfo.setText("NOTICE! AN ERROR OCCURED");
                                                childControllerdel.spinner.setVisible(false);
                                                childControllerdel.check.setVisible(false);
                                                break;

                                        }
                                    });
                                    Thread d = new Thread(task);
                                    d.setDaemon(true);
                                    d.start();

                                });
                                Scene scenedel = new Scene(parentdel);
                                stagedel.initModality(Modality.APPLICATION_MODAL);
                                stagedel.initOwner(parentdel.getScene().getWindow());
                                stagedel.setScene(scenedel);
                                stagedel.initStyle(StageStyle.UNDECORATED);
                                stagedel.resizableProperty().setValue(false);
                                stagedel.showAndWait();

                            } catch (IOException ex) {
                                Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                updateMessage("Processing...");
                                Thread.sleep(1000);
                                return null;
                            }
                        };
                        task.setOnSucceeded(f -> {
                            Receipt rt = new Receipt();
                            childController.save.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                                List<Integer> recCount = rec.getReceiptCount();
                                if (recCount.isEmpty()) {
                                    int recl = 1;
                                    rt.setReceiptId(recl);
                                } else {
                                    int recl = recCount.get(0);
                                    rt.setReceiptId(++recl);
                                }

                                rt.setSalesId(new Sales(selectedRecord.getSalesId()));
                                rt.setAmountPaid(Double.parseDouble(childController.amountpaid.getText()));
                                rt.setDateR(new Date(System.currentTimeMillis()));
                                rt.setUsers(new Users(LoginController.u.getUserid()));
                                rt.setEntryLog(new Date(System.currentTimeMillis()));
                                int result = new InsertUpdateBL().insertData(rt);
                                switch (result) {
                                    case 1:
                                        stage.close();
                                        filterdate();
                                        double balval = Double.parseDouble(selectedRecord.getBalance());
                                        double val = selectedRecord.getTotalSales() - balval;

                                        break;
                                    default:
                                        System.out.print("Error");
                                        break;
                                }

                            });

                        });
                        Thread d = new Thread(task);
                        d.setDaemon(true);
                        d.start();

                        Scene scene = new Scene(parent);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(parent.getScene().getWindow());
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.resizableProperty().setValue(false);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
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

    public class AddPersonCellPreview extends TableCell<SalesTableModel, Boolean> {

        //Image img = new Image(getClass().getResourceAsStream("edit.png"));
        Image preview = new Image(getClass().getResourceAsStream("/resources/eye.png"));

        // a button for adding a new person.
//        JFXButton addButton = new JFXButton();
        // pads and centers the add button in the cell.
        HBox paddedButton = new HBox();

        JFXButton btn = new JFXButton();
        // records the y pos of the last button press so that the add person dialog can be shown next to the cell.
        final DoubleProperty buttonY = new SimpleDoubleProperty();

        /**
         * AddPersonCell constructor
         *
         * @param stage the stage in which the table is placed.
         * @param table the table to which a new person can be added.
         */
        AddPersonCellPreview() {
            paddedButton.setStyle("-fx-alignment: CENTER;");
            paddedButton.getChildren().add(btn);
//            delButton.setGraphic(new ImageView(img2));
//            delButton.setRipplerFill(Paint.valueOf("#D8E1DC"));
            btn.setRipplerFill(Paint.valueOf("#6699ff"));
            btn.setGraphic(new ImageView(preview));
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override

                public void handle(ActionEvent event) {
                    int selectdIndex = getTableRow().getIndex();
                    //Create a new table show details of the selected item
                    SalesTableModel selectedRecord = (SalesTableModel) salestable.getItems().get(selectdIndex);
                    try {
                        new PrintReport().showReceiptReport(selectedRecord.getSalesId());
                    } catch (JRException ex) {
                        Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
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

    public class AddPersonCellSales extends TableCell<SalesTableModel, Boolean> {

        Image img = new Image(getClass().getResourceAsStream("edit.png"));
        Image preview = new Image(getClass().getResourceAsStream("delete.png"));

        // a button for adding a new person.
        // JFXButton addButton = new JFXButton();
        // pads and centers the add button in the cell.
        HBox paddedButton = new HBox();

        JFXButton btn = new JFXButton();
        JFXButton btn1 = new JFXButton();
        // records the y pos of the last button press so that the add person dialog can be shown next to the cell.
        final DoubleProperty buttonY = new SimpleDoubleProperty();

        /**
         * AddPersonCell constructor
         *
         * @param stage the stage in which the table is placed.
         * @param table the table to which a new person can be added.
         */
        AddPersonCellSales() {
            paddedButton.setStyle("-fx-alignment: CENTER;");
            paddedButton.getChildren().add(btn1);
            paddedButton.getChildren().add(btn);
//            delButton.setGraphic(new ImageView(img2));
//            delButton.setRipplerFill(Paint.valueOf("#D8E1DC"));
            btn.setRipplerFill(Paint.valueOf("#6699ff"));
            btn.setGraphic(new ImageView(preview));
            btn1.setRipplerFill(Paint.valueOf("#6699ff"));
            btn1.setGraphic(new ImageView(img));
            //EDIT SALES
            btn1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    seleteditemtableview.getItems().clear();
                    int selectdIndex = getTableRow().getIndex();
                    //Create a new table show details of the selected item
                    SalesTableModel selectedRecord = (SalesTableModel) salestable.getItems().get(selectdIndex);
                    List<SalesDetails> sl = new SalesBL().getSalesDetailsbySalesId(selectedRecord.getSalesId());

                    sl.forEach(e -> {
                        double totalpriceval = e.getQuantity() * e.getSalesPrice();
//                      String NHISVAL;
                        List<Stockin> stk = new StockinBL().getStockinListbyBatchNo(e.getBatchNo());
                        String NHISVAL;
                        if (e.getNhisCondition() == 1) {
                            NHISVAL = "ACTIVE";
                        } else {
                            NHISVAL = "INACTIVE";
                        }
//                        String cus = new CustomerBL().getCustomerNamebyId(e.getSaleId().getCustomer().getCustomerId());
//                        customerlabel.setText(e.getSales().getCustomer().getCustomerId() + " : " + cus);
                        sellbtn.setText("UPDATE");
                        data.add(new SelectItemSaleTableModel(stk.get(0).getItems().getItemDesc(), e.getBatchNo(), e.getQuantity(), e.getCostPrice(), e.getSalesPrice(), totalpriceval, e.getNhisPrice(), NHISVAL, e.getDiscount()));
                        itemname.setCellValueFactory(cell -> cell.getValue().getItemNameProperty());
                        batchcode.setCellValueFactory(cell -> cell.getValue().getBatchCodeProperty());
                        qnt.setCellValueFactory(cell -> cell.getValue().getQuantityProperty());
                        price.setCellValueFactory(cell -> cell.getValue().getPriceProperty());
                        total.setCellValueFactory(cell -> cell.getValue().getTotalProperty());
                        discountval.setCellValueFactory(cell -> cell.getValue().getDiscountValueProperty());
                        nhisvalprice.setCellValueFactory(cell -> cell.getValue().getNHISPriceProperty());
                        nhis.setCellValueFactory(cell -> cell.getValue().getNHISProperty());
                        action.setSortable(false);
                        action.setMaxWidth(480);
                        discount.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SelectItemSaleTableModel, Boolean>, ObservableValue<Boolean>>() {
                            @Override
                            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SelectItemSaleTableModel, Boolean> features) {
                                return new SimpleBooleanProperty(features.getValue() != null);
                            }
                        });

                        discount.setCellFactory(new Callback<TableColumn<SelectItemSaleTableModel, Boolean>, TableCell<SelectItemSaleTableModel, Boolean>>() {
                            @Override
                            public TableCell<SelectItemSaleTableModel, Boolean> call(TableColumn<SelectItemSaleTableModel, Boolean> personBooleanTableColumn) {
                                return new AddPersonDiscountCell();
                            }
                        });

                        action.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SelectItemSaleTableModel, Boolean>, ObservableValue<Boolean>>() {
                            @Override
                            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SelectItemSaleTableModel, Boolean> features) {
                                return new SimpleBooleanProperty(features.getValue() != null);
                            }
                        });

                        action.setCellFactory(new Callback<TableColumn<SelectItemSaleTableModel, Boolean>, TableCell<SelectItemSaleTableModel, Boolean>>() {
                            @Override
                            public TableCell<SelectItemSaleTableModel, Boolean> call(TableColumn<SelectItemSaleTableModel, Boolean> personBooleanTableColumn) {
                                return new AddPersonCell();
                            }
                        });

                        seleteditemtableview.setItems(data);
                        seleteditemtableview.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                        totalp = Float.parseFloat(df.format(Utilities.sumList(getPrice())));
                        totalprice.setText(df.format(totalp));
//                        System.out.println(totalp);

                    });

                    sellbtn.setOnAction(new EventHandler<ActionEvent>() {

                        ObservableList<SelectItemSaleTableModel> list = seleteditemtableview.getItems();

                        @Override
                        public void handle(ActionEvent event) {
                            if (sellbtn.getText().equals("UPDATE")) {
                                try {
                                    Stage stage = new Stage();
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SalesSlip.fxml"));
                                    Parent parent = (Parent) fxmlLoader.load();
                                    SalesSlipController childController = fxmlLoader.getController();
                                    childController.totalitemprice.setText(df.format(totalp));
                                    childController.confirmheader.setText("CONFIRM EDIT");
                                    childController.confirm.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            childController.confirm.setDisable(true);
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

//                                            childController.displayinfo.textProperty().bind(task.messageProperty());
                                            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                                @Override
                                                public void handle(WorkerStateEvent event) {
                                                    childController.confirm.setDisable(true);
                                                    childController.displayinfo.textProperty().unbind();
                                                    List salescode = new ReceiptBL().getSalesbyReceipt(selectedRecord.getSalesId());
                                                    if (!salescode.isEmpty()) {
                                                        childController.displayinfo.setText("UNABLE TO EDIT RECORD");
                                                        childController.spinner.setVisible(false);
                                                        childController.check.setVisible(false);
                                                        seleteditemtableview.getItems().clear();
                                                        totalprice.setText(null);
                                                        sellbtn.setText("SALES");
                                                    } else {
                                                        sl.forEach(e -> {
                                                            int result = new SalesDetailsBL().removeData(e.getSalesDetailsId());
                                                        });
                                                        list.forEach(v -> {
                                                            SalesDetails sd = new SalesDetails();
                                                            sd.setBatchNo(v.getBatchCode());
                                                            sd.setSaleId(sl.get(0).getSaleId());
                                                            sd.setQuantity(v.getQuantity());
                                                            sd.setCostPrice(v.getCost());
                                                            sd.setSalesPrice(v.getPrice());
                                                            sd.setNhisPrice(v.getNHISPrice());
                                                            sd.setDiscount(v.getDiscountValue());
                                                            sd.setUsers(new Users(LoginController.u.getUserid()));
                                                            sd.setEntryDate(new Date(System.currentTimeMillis()));
                                                            if (v.getNHIS().equals("ACTIVE")) {
                                                                sd.setNhisCondition(1);
                                                            } else {
                                                                sd.setNhisCondition(0);
                                                            }
                                                            int result = new InsertUpdateBL().updateData(sd);
                                                            if (result == 1) {

                                                                getStockingItemList();
                                                                Date dd = new Date(System.currentTimeMillis());
                                                                SalesTableData(dd, dd);
                                                                totalprice.setText(null);
                                                                childController.displayinfo.setText("EDITED SUCCESSFULLY");
                                                                childController.spinner.setVisible(false);
                                                                childController.check.setVisible(true);
                                                                salesdetailstable.getItems().clear();
                                                                sellbtn.setText("SALES");

                                                            } else {
                                                                System.out.println("Unable to save");
                                                            }

                                                        });
                                                        if (!childController.displayinfo.getText().equals("UNABLE TO EDIT RECORD")) {
                                                            childController.spinner.setVisible(false);
                                                            seleteditemtableview.getItems().clear();
                                                            sellbtn.setText("SALES");
                                                        }
                                                    }
                                                }
                                            });

                                            Thread d = new Thread(task);
                                            d.setDaemon(true);
                                            d.start();

                                        }
                                    });
                                    Scene scene = new Scene(parent);
                                    stage.initModality(Modality.APPLICATION_MODAL);
                                    stage.initOwner(parent.getScene().getWindow());
                                    stage.setScene(scene);
                                    stage.initStyle(StageStyle.UNDECORATED);
                                    stage.resizableProperty().setValue(false);
                                    stage.showAndWait();
                                } catch (IOException ex) {
                                    Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                try {
                                    makeSalesAction();
                                } catch (IOException ex) {
                                    Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        }

                    });

                }
            });
            //DELETE SALES
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override

                public void handle(ActionEvent event) {
                    try {

                        int selectdIndex = getTableRow().getIndex();
                        //Create a new table show details of the selected item
                        SalesTableModel selectedRecord = (SalesTableModel) salestable.getItems().get(selectdIndex);
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
                                    updateMessage("PROCESSING PLS WAIT.....");
                                    Thread.sleep(1000);
                                    return null;
                                }
                            };
                            task.setOnSucceeded(f -> {
                                List salescode = new ReceiptBL().getSalesbyReceipt(selectedRecord.getSalesId());
                                if (!salescode.isEmpty()) {
                                    childController.displayinfo.setText("UNABLE TO DELETE RECORD");
                                    childController.spinner.setVisible(false);
                                    childController.check.setVisible(false);
                                } else {
                                    int result = sb.removeData(selectedRecord.getSalesId());
                                    switch (result) {
                                        case 1:
                                            childController.displayinfo.setText("SUCCESSFULLY DELETED");
                                            childController.spinner.setVisible(false);
                                            childController.check.setVisible(true);
                                            salesdetailstable.getItems().clear();
                                            totalsalesprice.setText(null);
                                            getTodaysDate();
                                            stage.close();
                                            break;
                                        default:
                                            childController.displayinfo.setText("NOTICE! AN ERROR OCCURED");
                                            childController.spinner.setVisible(false);
                                            childController.check.setVisible(false);
                                            break;

                                    }
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
                        Logger.getLogger(AddCategoryController.class.getName()).log(Level.SEVERE, null, ex);
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
    public void filterdate() {
        SalesTableData(Utilities.convertToDateViaSqlDate(startdate.getValue()), Utilities.convertToDateViaSqlDate(enddate.getValue()));
    }

    public Number getNHISTotal() {
        Number nhisqyt = 0;
        Number nhisval = 0;
        Number total = 0;
        for (SelectItemSaleTableModel item : seleteditemtableview.getItems()) {
            nhisqyt = qnt.getCellObservableValue(item).getValue();
            nhisval = nhisvalprice.getCellObservableValue(item).getValue();
            total = nhisqyt.intValue() * nhisval.doubleValue();

        }
        return total;
    }

    @FXML
    public void removeItemsFromTable() {
        SelectItemSaleTableModel selectedItem = seleteditemtableview.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            System.out.println("Select Item you want to remove");
        } else {
            seleteditemtableview.getItems().remove(selectedItem);
            totalp = Float.parseFloat(df.format(Utilities.sumList(getPrice())));
            totalprice.setText(df.format(totalp));
        }
    }

    @FXML
    public void CustomerPopup() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Customer.fxml"));
        Parent parent = (Parent) fxmlLoader.load();
        Scene scene = new Scene(parent);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parent.getScene().getWindow());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();
    }

}
