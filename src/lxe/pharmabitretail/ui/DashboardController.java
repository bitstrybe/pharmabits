package lxe.pharmabitretail.ui;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lxe.pharmabitretail.bl.ReceiptBL;
import lxe.pharmabitretail.bl.StockinBL;
import lxe.pharmabitretail.entity.Stockin;
import lxe.pharmabitretail.utils.Utilities;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

/**
 * FXML Controller class
 *
 * @author JScare
 */
public class DashboardController implements Initializable {

    @FXML
    private Text dailysales;

    ReceiptBL rc = new ReceiptBL();
    @FXML
    private Text weeklysales;
    @FXML
    private Text monthlysales;
    @FXML
    private Text quaterlysales;
    DecimalFormat df = new DecimalFormat("#,###.00");
    @FXML
    private ListView<String> expirylist;

    StockinBL sb = new StockinBL();
    @FXML
    private LineChart<String, Number> linechart;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private AnchorPane dashpane;
    @FXML
    public Region opaqueLayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        dashboard.setStyle("-fx-background-color:  #f3f3f3");
//        stocks.setStyle("-fx-background-color: transparent");
//        sales.setStyle("-fx-background-color: transparent");
        getDailySales();
        getWeeklySales();
        getMonthlySales();
        getAnnualSales();
        try {
            getExiryList();
        } catch (Exception ex) {
            expirylist.getItems();
        }

        getSaleChart(2020);

    }

    public void getExiryList() {
        DateTime today = DateTime.now();
        DateTime dt = new DateTime().plusMonths(1);
        List<Stockin> list = sb.getTwoWeekToExpiry(today.toDate(), dt.toDate());
        ObservableList<Stockin> result = FXCollections.observableArrayList(list);
        expirylist.getItems().clear();
        result.forEach((man) -> {
            String uom = man.getItems().getVomDef() + "" + man.getItems().getVom();
            expirylist.getItems().add(man.getItems().getItemName() + " " + man.getItems().getCategory().getCategoryName() + " " + uom);
        });
    }

    public void getDailySales() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> {
                    DateTime today = new DateTime(System.currentTimeMillis());
                    double val = rc.getDailySalesReceipt(today.toDate());
                    dailysales.setText(String.valueOf(df.format(val)));
                    dailysales.setFill(Paint.valueOf("#6ba16f"));
                })
        );
        timeline.play();
    }

    public void getWeeklySales() {

        if (LoginController.u.getRoles().equals("Administrator") || LoginController.u.getRoles().equals("Sales Supervisor")) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        DateTime today = new DateTime(System.currentTimeMillis());
                        DateTime date = new DateTime().withDayOfWeek(DateTimeConstants.MONDAY);
                        double val = rc.getDurationSalesReceipt(date.toDate(), today.toDate());
                        weeklysales.setText(String.valueOf(df.format(val)));
                        weeklysales.setFill(Paint.valueOf("#8d4747"));

                    })
            );
            timeline.play();

        } else {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        weeklysales.setText(df.format(0.00));
                        weeklysales.setFill(Paint.valueOf("#8d4747"));

                    })
            );
            timeline.play();

        }

    }

    public void getMonthlySales() {
        if (LoginController.u.getRoles().equals("Administrator")) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1.5), e -> {
                        DateTime today = new DateTime(System.currentTimeMillis());
                        DateTime date = new DateTime().dayOfMonth().withMinimumValue();
                        double val = rc.getDurationSalesReceipt(date.toDate(), today.toDate());
                        monthlysales.setText(String.valueOf(df.format(val)));
                        monthlysales.setFill(Paint.valueOf("#5a4c97"));
                    })
            );
            timeline.play();
        } else {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1.5), e -> {
                        monthlysales.setText(df.format(0.00));
                        monthlysales.setFill(Paint.valueOf("#5a4c97"));
                    })
            );
            timeline.play();

        }

    }

    public void getAnnualSales() {
        if (LoginController.u.getRoles().equals("Administrator")) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(2), e -> {
                        DateTime today = new DateTime(System.currentTimeMillis());
                        double val = rc.getDurationSalesReceipt(getStartOfYear(), today.toDate());
                        quaterlysales.setText(String.valueOf(df.format(val)));
                        quaterlysales.setFill(Paint.valueOf("#a1a187"));
                    })
            );
            timeline.play();
        } else {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(2), e -> {
                        quaterlysales.setText(df.format(0.00));
                        quaterlysales.setFill(Paint.valueOf("#a1a187"));
                    })
            );
            timeline.play();

        }

    }

    public void getSaleChart(int year) {
        xAxis.setLabel("Months");
        yAxis.setLabel("Sales (GH¢)");
        linechart.setTitle(+year + " Monthly Sales Chart");
        XYChart.Series series = new XYChart.Series();
        series.setName("Sales Portfolio");
//        double jan = rc.getDurationSalesReceipt(getFirstDayOftheMonth(0,year), getLastDayOftheMonth(0,year));
//        series.getData().add(new XYChart.Data("Jan", Utilities.roundToTwoDecimalPlace(jan, 2)));
//        double fed = rc.getDurationSalesReceipt(getFirstDayOftheMonth(1,year), getLastDayOftheMonth(1,year));
//        series.getData().add(new XYChart.Data("Feb", Utilities.roundToTwoDecimalPlace(fed, 2)));
//        double mar = rc.getDurationSalesReceipt(getFirstDayOftheMonth(2,year), getLastDayOftheMonth(2,year));
//        series.getData().add(new XYChart.Data("Mar", Utilities.roundToTwoDecimalPlace(mar, 2)));
//        double apr = rc.getDurationSalesReceipt(getFirstDayOftheMonth(3,year), getLastDayOftheMonth(3,year));
//        series.getData().add(new XYChart.Data("Apr", Utilities.roundToTwoDecimalPlace(apr, 2)));
//        double may = rc.getDurationSalesReceipt(getFirstDayOftheMonth(4,year), getLastDayOftheMonth(4,year));
//        series.getData().add(new XYChart.Data("May", Utilities.roundToTwoDecimalPlace(may, 2)));
//        double jun = rc.getDurationSalesReceipt(getFirstDayOftheMonth(5,year), getLastDayOftheMonth(5,year));
//        series.getData().add(new XYChart.Data("Jun", Utilities.roundToTwoDecimalPlace(jun, 2)));
//        double jul = rc.getDurationSalesReceipt(getFirstDayOftheMonth(6,year), getLastDayOftheMonth(6,year));
//        series.getData().add(new XYChart.Data("Jul", Utilities.roundToTwoDecimalPlace(jul, 2)));
//        double aug = rc.getDurationSalesReceipt(getFirstDayOftheMonth(7,year), getLastDayOftheMonth(7,year));
//        series.getData().add(new XYChart.Data("Aug", Utilities.roundToTwoDecimalPlace(aug, 2)));
//        double sep = rc.getDurationSalesReceipt(getFirstDayOftheMonth(8,year), getLastDayOftheMonth(8,year));
//        series.getData().add(new XYChart.Data("Sep", Utilities.roundToTwoDecimalPlace(sep, 2)));
//        double oct = rc.getDurationSalesReceipt(getFirstDayOftheMonth(9,year), getLastDayOftheMonth(9,year));
//        series.getData().add(new XYChart.Data("Oct", Utilities.roundToTwoDecimalPlace(oct, 2)));
//        double nov = rc.getDurationSalesReceipt(getFirstDayOftheMonth(10,year), getLastDayOftheMonth(10,year));
//        series.getData().add(new XYChart.Data("Nov", Utilities.roundToTwoDecimalPlace(nov, 2)));
//        double dec = rc.getDurationSalesReceipt(getFirstDayOftheMonth(11,year), getLastDayOftheMonth(11,year));
//        series.getData().add(new XYChart.Data("Dec", Utilities.roundToTwoDecimalPlace(dec, 2)));
        series.getData().add(new XYChart.Data("Jan", 23));
        series.getData().add(new XYChart.Data("Feb", 14));
        series.getData().add(new XYChart.Data("Mar", 15));
        series.getData().add(new XYChart.Data("Apr", 24));
        series.getData().add(new XYChart.Data("May", 34));
        series.getData().add(new XYChart.Data("Jun", 36));
        series.getData().add(new XYChart.Data("Jul", 22));
        series.getData().add(new XYChart.Data("Aug", 45));
        series.getData().add(new XYChart.Data("Sep", 43));
        series.getData().add(new XYChart.Data("Oct", 17));
        series.getData().add(new XYChart.Data("Nov", 29));
        series.getData().add(new XYChart.Data("Dec", 25));

        linechart.getData().add(series);
        if (LoginController.u.getRoles().equals("Administrator")) {
            for (XYChart.Series<String, Number> s : linechart.getData()) {
                for (XYChart.Data<String, Number> d : s.getData()) {
                    Tooltip.install(d.getNode(), new Tooltip(
                            "Month : " + d.getXValue().toString() + "\n"
                            + "Sales : " + df.format(d.getYValue())));

                    //Adding class on hover
                    d.getNode().setOnMouseEntered(event -> d.getNode().setStyle("-fx-background-color:  #9999ff"));
                    //Removing class on exit
                    d.getNode().setOnMouseExited(event -> d.getNode().setStyle("-fx-background-color: #9999ff, #ffffff;"));
                }
            }
        }

    }

    public Date getStartOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        Date start = cal.getTime();
        return start;
    }

    public Date getFirstDayOftheMonth(int month, int year) {
        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DATE));
        Date firstday = c.getTime();
        return firstday;
    }

    public Date getLastDayOftheMonth(int month, int year) {
        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DATE));
        Date lastday = c.getTime();
        return lastday;
    }
}
