
package lxe.pharmabitretail.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author JScare
 */
public class ItemsDisplayController implements Initializable {

    @FXML
    public ImageView itemsimage;
    @FXML
    public Label medsname;
    @FXML
    public Label uom;
    @FXML
    public Label man;
    @FXML
    public Label exp;
    @FXML
    public Button addtocart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
