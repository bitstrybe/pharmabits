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
public class SalesController implements Initializable {

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
    private TableView<SelectItemSaleTableModel> seleteditemtableview;
    private TableColumn<SelectItemSaleTableModel, String> batchcode;
    private TableColumn<SelectItemSaleTableModel, String> itemname;
    private TableColumn<SelectItemSaleTableModel, Number> qnt;
    private TableColumn<SelectItemSaleTableModel, Number> price;
    private TableColumn<SelectItemSaleTableModel, Number> total;

    private TableColumn<SelectItemSaleTableModel, Number> nhisvalprice;
    private TableColumn<SelectItemSaleTableModel, String> nhis;
    private TableColumn<SelectItemSaleTableModel, Boolean> action;
    private TableColumn<SelectItemSaleTableModel, Boolean> discount;
    private TableColumn<SelectItemSaleTableModel, Number> discountval;
    private JFXButton sellbtn;
    private Label totalprice;
    //Sales Table
    private TableView<SalesTableModel> salestable;
    private TableColumn<SalesTableModel, Number> salecode;
    private TableColumn<SalesTableModel, Number> saleprice;
    private TableColumn<SalesTableModel, String> date;
    private TableColumn<SalesTableModel, Number> paidvalue;
    private TableColumn<SalesTableModel, String> salesbalance;
    private TableColumn<SalesTableModel, Boolean> action1;
    private TableColumn<SalesTableModel, Boolean> action2;
    private TableColumn<SalesTableModel, Boolean> actionsales;
    //Sales Details Table
    private TableView<SalesDetailsTableModel> salesdetailstable;
    private TableColumn<SalesDetailsTableModel, String> batchno;
    private TableColumn<SalesDetailsTableModel, Number> scode;
    private TableColumn<SalesDetailsTableModel, Number> qty;
    private TableColumn<SalesDetailsTableModel, Number> sp;
    private TableColumn<SalesDetailsTableModel, Number> np;
    private TableColumn<SalesDetailsTableModel, String> ns;
    private TableColumn<SalesDetailsTableModel, Number> discountsalesdetails;
    private DatePicker startdate;
    private DatePicker enddate;
    private Label totalsalesprice;

    DecimalFormat df = new DecimalFormat("0.00");
    SalesBL sb = new SalesBL();
    ReceiptBL rec = new ReceiptBL();
    double totalp;

    static int bal = 0;
    static int qntval1 = 0;
    static int stockbal = 0;
    String lsv;

    public void getStockingItemList() {
        StockinBL sk = new StockinBL();
        List<Stockin> list = sk.getAllStockinGroup();
        ObservableList<Stockin> result = FXCollections.observableArrayList(list);
        listviewsales.getItems().clear();
        listviewsales.setOrientation(Orientation.HORIZONTAL);
        result.forEach((man) -> {
            long balance = sk.getStockBalance(man.getBatchNo());
            if (balance != 0) {
                listviewsales.getItems().add(man.getBatchNo() + ": " + man.getItems().getItemDesc());
                System.out.println(man.getBatchNo() + ": " + man.getItems().getItemDesc());
                listviewsales.setCellFactory(value -> new ListCell<String>() {
                    private VBox vb = new VBox();
                    private ImageView imageView = new ImageView();
                    private Label lb = new Label();
                    private Label lb1 = new Label();

                    @Override
                    public void updateItem(String name, boolean empty) {
                        super.updateItem(name, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            try {
                                if (name.equals(man.getBatchNo() + ": " + man.getItems().getItemDesc())) {
                                    imageView.setFitWidth(200);
                                    imageView.setPreserveRatio(true);
                                    imageView.setSmooth(true);
                                    imageView.setCache(true);
                                    vb.setAlignment(Pos.TOP_CENTER);
                                    FileInputStream input;
                                    input = new FileInputStream(man.getItems().getItemImg());
                                    Image image = new Image(input);
                                    imageView.setImage(image);
                                    lb.setText(man.getBatchNo() + ": " + man.getItems().getItemDesc());
                                    lb1.setText(balance + " Remaining");
                                    vb.getChildren().addAll(imageView, lb, lb1);
                                    setGraphic(vb);
                                }

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
//        getTodaysDate();
        getStockingItemList();

        itemsearch.textProperty().addListener(e -> {
            if (itemsearch.getText().length() > 1) {
                SearchStockingItemList(itemsearch.getText());
            } else {
                getStockingItemList();
            }
        });

//        listviewsales.setOnMouseClicked(r -> {
//            if (r.getClickCount() == 2) {
//                try {
//                    addtoitemtable();
//                } catch (IOException ex) {
//                    Logger.getLogger(SalesController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//        seleteditemtableview.setOnMouseClicked(a -> {
//            if (a.getClickCount() == 2) {
//                removeItemsFromTable();
//            }
//        });
    }

}
