package lxe.pharmabitretail.ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lxe.pharmabitretail.bl.LoginBL;
import lxe.pharmabitretail.entity.Logs;
import lxe.pharmabitretail.utils.PrintReport;
import net.sf.jasperreports.engine.JRException;

/**
 * FXML Controller class
 *
 * @author scarface
 */
public class MainAppController implements Initializable {

    @FXML
    private MenuButton mainmenu;
    @FXML
    private Label user;
    @FXML
    public BorderPane boarderpane;
    @FXML
    private HBox sales;
    @FXML
    private HBox stocks;
    @FXML
    private HBox dashboard;
    @FXML
    private Menu admintopmenu;
    @FXML
    private Label user_role;
    @FXML
    private HBox backup;
    @FXML
    private VBox sidemenu;
    @FXML
    private MenuItem reorderlevel;
    @FXML
    private FontAwesomeIcon dashawesome;
    @FXML
    private Text dashtext;
    @FXML
    private FontAwesomeIcon stockawesome;
    @FXML
    private Text stocktext;
    @FXML
    private FontAwesomeIcon catalogawesome;
    @FXML
    private Text catalogtext;

    public void setScene(String scenechange) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(scenechange));
        root.getStylesheets().addAll(getClass().getResource("style.css").toExternalForm());
        boarderpane.setCenter(root);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        mainmenu.setText("Welcome, : " + LoginController.u.getUsername());
        user.setText(LoginController.u.getFname().toUpperCase() + " " + LoginController.u.getLname().toUpperCase());
        user_role.setText(LoginController.u.getRoles().toString());
        if (LoginController.u.getRoles().equals("Administrator") || LoginController.u.getRoles().equals("Sales Supervisor")) {
            admintopmenu.setVisible(true);
        } else {
            admintopmenu.setVisible(false);
        }

        try {
            setScene("Dashboard.fxml");
            dashboard.setStyle("-fx-background-color:#dddee0");
            dashawesome.setFill(Color.BLACK);
            dashtext.setFill(Color.BLACK);
            stocks.setStyle("-fx-background-color: transparent");
            sales.setStyle("-fx-background-color: transparent");
        } catch (IOException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        }

        dashboard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                setScene("Dashboard.fxml");
                dashboard.setStyle("-fx-background-color:#dddee0");
                dashawesome.setFill(Color.BLACK);
                dashtext.setFill(Color.BLACK);
                stockawesome.setFill(Color.valueOf("#dddee0"));
                stocktext.setFill(Color.valueOf("#dddee0"));
                catalogawesome.setFill(Color.valueOf("#dddee0"));
                catalogtext.setFill(Color.valueOf("#dddee0"));
                stocks.setStyle("-fx-background-color: transparent");
                sales.setStyle("-fx-background-color: transparent");
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        stocks.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                setScene("Stock.fxml");
                stocks.setStyle("-fx-background-color:#dddee0");
                stockawesome.setFill(Color.BLACK);
                stocktext.setFill(Color.BLACK);
                dashawesome.setFill(Color.valueOf("#dddee0"));
                dashtext.setFill(Color.valueOf("#dddee0"));
                catalogawesome.setFill(Color.valueOf("#dddee0"));
                catalogtext.setFill(Color.valueOf("#dddee0"));
                
                sales.setStyle("-fx-background-color:  transparent");
                dashboard.setStyle("-fx-background-color: transparent");
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        sales.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                setScene("Sales.fxml");
                sales.setStyle("-fx-background-color:#dddee0");
                catalogawesome.setFill(Color.BLACK);
                catalogtext.setFill(Color.BLACK);
                dashawesome.setFill(Color.valueOf("#dddee0"));
                dashtext.setFill(Color.valueOf("#dddee0"));
                stockawesome.setFill(Color.valueOf("#dddee0"));
                stocktext.setFill(Color.valueOf("#dddee0"));
                stocks.setStyle("-fx-background-color:  transparent");
                dashboard.setStyle("-fx-background-color: transparent");
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        backup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Backup.fxml"));
                Parent parent = (Parent) fxmlLoader.load();
                Scene scene = new Scene(parent);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(parent.getScene().getWindow());
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.resizableProperty().setValue(false);
                stage.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    public void signOut() {
        LoginBL mtd = new LoginBL();
        Logs l = mtd.getLogById(LoginController.log.getLogsid());
        l.setLogoutDatetime(new Date(System.currentTimeMillis()));
        //System.out.println(new Date(System.currentTimeMillis()));
        int result = mtd.updateData(l);
        if (result == 1) {
            System.out.println("Successfully Logged Out");
        } else {
            System.out.println("Unable to log out");
        }
        try {
            Stage st = (Stage) mainmenu.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            Image icon = new Image(getClass().getResourceAsStream("/resources/meds_logo.png"));
            stage.getIcons().add(icon);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            st.hide();
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addcategory(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddCategory.fxml"));
        Parent parent = (Parent) fxmlLoader.load();
        Scene scene = new Scene(parent);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parent.getScene().getWindow());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();
    }

    @FXML
    private void addmanufacturer(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddManufacturer.fxml"));
        Parent parent = (Parent) fxmlLoader.load();
        Scene scene = new Scene(parent);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parent.getScene().getWindow());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();

    }

    @FXML
    private void additems(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddItems.fxml"));
        Parent parent = (Parent) fxmlLoader.load();
        Scene scene = new Scene(parent);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parent.getScene().getWindow());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();
    }

    @FXML
    private void stockreorderlevel(ActionEvent event) {
        try {
            new PrintReport().showStockReorderReport();
        } catch (JRException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void minimize(ActionEvent event) {
        Stage stage = (Stage) boarderpane.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void stocksreport(ActionEvent event) {
        try {
            new PrintReport().showStocksReport();
        } catch (JRException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void itemlistreport(ActionEvent event) {
        try {
            new PrintReport().showItemListReport();
        } catch (JRException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainAppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
