package lxe.pharmabitretail.ui;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lxe.pharmabitretail.bl.LoginBL;
import lxe.pharmabitretail.entity.Userlogs;
import lxe.pharmabitretail.entity.Users;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author jexshizzle
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    static Users u;
    static Userlogs log;
    LoginBL loginbl = new LoginBL();

    @FXML
    private Button login;
    Image icon = new Image(getClass().getResourceAsStream("/resources/meds_logo.png"));
    @FXML
    private Hyperlink hyperlink;

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    @FXML
    public void dolog() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Processing please wait......");
                login.setDisable(true);
                Thread.sleep(1000);
                return null;
            }
        };
        login.textProperty().bind(task.messageProperty());
        task.setOnSucceeded(e -> {
            login.textProperty().unbind();
            Users user = new Users();
            user.setUsername(username.getText());
            user.setPwd(password.getText());
            u = new LoginBL().validateUser(user);
            if (u.getUserid() != null) {
                if (u.getStatus() == 0) {
                    getChangePassword();
                } else {
                    getLogin();
                }
////                try {
////                    Stage st = (Stage) login.getScene().getWindow();
////                    Parent root = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
////                    Stage stage = new Stage();
////                    stage.initStyle(StageStyle.TRANSPARENT);
////                    stage.resizableProperty().setValue(false);
////                    stage.setTitle("Drug Dispensery Management System");
////                    stage.setMaximized(true);
////                    stage.setScene(new Scene(root));
////                    stage.show();
////                    st.hide();
////
////                try {
////                    login.setText("Sucessfully Logged");
////                    userLog();
////                    Stage st = (Stage) login.getScene().getWindow();
////                    Parent root = FXMLLoader.load(getClass().getResource("MainApp.fxml"));
////                    Stage stage = new Stage();
////                    stage.initStyle(StageStyle.TRANSPARENT);
////                    stage.resizableProperty().setValue(false);
////                    stage.setTitle("Drug Dispensery Management System");
////                    stage.setMaximized(true);
////                    stage.setScene(new Scene(root));
////                    stage.show();
////                    st.hide();
////
////                } catch (IOException ex) {
////                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
////                }
            } else {
                login.setText("Login Failed, Try Again");
                login.setDisable(false);
            }

        });
        Thread d = new Thread(task);
        d.setDaemon(true);
        d.start();
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

    public int userLog() {
        log = new Userlogs();
        log.setUsername(new Users(u.getUserid()));
        log.setLoginDatetime(new Date(System.currentTimeMillis()));
        int result = loginbl.insertData(log);
        if (result == 1) {
            System.out.println("Logged Successfully");
        } else {
            System.out.println("Unable to log");
        }
        return log.getLogsid();
    }

    public void visitUrl(){
        hyperlink.setOnAction(e->{
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.google.com"));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        });
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new LoginBL();
        visitUrl();
    }

    public void getLogin() {
        try {
            login.setText("Sucessfully Logged");
            userLog();
            Stage st = (Stage) login.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("MainApp.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.resizableProperty().setValue(false);
            stage.getIcons().add(icon);
            stage.setTitle("Drug Dispensery Management System");
            stage.setMaximized(true);
            stage.setScene(new Scene(root));
            stage.show();
            st.hide();
//
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getChangePassword() {
        try {
            Stage st = (Stage) login.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChangePassword.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            ChangePasswordController childController = fxmlLoader.getController();
//            System.out.println("Reached");
            childController.login.addEventHandler(MouseEvent.MOUSE_CLICKED, v -> {
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
//                        childController.spinner.setVisible(true);
                        updateMessage("Data Processing...");
                        Thread.sleep(1000);
                        return null;
                    }
                };
                childController.login.textProperty().bind(task.messageProperty());
                task.setOnSucceeded(s -> {
                    childController.login.textProperty().unbind();
                    if (!childController.repassword.getText().equals(childController.password.getText())) {
                        System.out.println("Password does match");
                    } else {
                        Users user = new Users();
                        user.setUserid(u.getUserid());
                        user.setTitle(u.getTitle());
                        user.setFname(u.getFname());
                        user.setLname(u.getLname());
                        user.setMobile(u.getMobile());
                        user.setEmail(childController.emailaddress.getText());
                        user.setUsername(u.getUsername());
                        user.setPwd(DigestUtils.md5Hex(new StringBuilder().append(childController.password.getText()).append("LXES3KURITICHECKSALT").toString()));
                        user.setRoles(u.getRoles());
                        user.setDateCreated(u.getDateCreated());
                        user.setModifiedDate(u.getModifiedDate());
                        user.setStatus(1);
                        int result = loginbl.updateData(user);
                        if (result == 1) {
                            try {
                                childController.login.setText("Sucessfully Changed");
                                userLog();
                                Stage sta = (Stage) childController.login.getScene().getWindow();
                                Parent root = FXMLLoader.load(getClass().getResource("MainApp.fxml"));
                                Stage stage1 = new Stage();
                                stage1.initStyle(StageStyle.TRANSPARENT);
                                stage1.resizableProperty().setValue(false);
                                stage1.getIcons().add(icon);
                                stage1.setTitle("Drug Dispensery Management System");
                                stage1.setMaximized(true);
                                stage1.setScene(new Scene(root));
                                stage1.show();
                                sta.hide();
                            } catch (IOException ex) {
                                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            System.out.println("Unable to save");
                        }
//                        System.out.println("match");
                        childController.login.setText("Password Changed");
                    }

                });
                Thread d = new Thread(task);
                d.setDaemon(true);
                d.start();

            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.resizableProperty().setValue(false);
            stage.setScene(new Scene(parent));
            stage.show();
            st.hide();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
