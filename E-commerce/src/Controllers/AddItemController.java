package Controllers;

import Main.DBConnection;
import Main.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class AddItemController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    private Button btnCancel;

    @FXML
    private TextField priceField;

    @FXML
    private Button btnAdd;

    @FXML
    private VBox addItemBox;

    @FXML
    private TextField itemNameField;

    @FXML
    private TextField quantityField;

    @FXML
    void actionAdd(ActionEvent event) throws SQLException {
        String name = itemNameField.getText();
        String seller = User.getInstance().getUsername();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());
        String sql = "insert into item (name, seller, quantity, price) values ('" + name + "', '" + seller + "', " + quantity + ", " + price + ")";
        Statement statement = DBConnection.statement;
        int i = statement.executeUpdate(sql);
        if (i == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adding Item");
            alert.setHeaderText("Item itemName Added successfully");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Adding Item");
            alert.setHeaderText("Item itemName Failed to add!");
            alert.showAndWait();
        }
    }

    @FXML
    void actionCancel(ActionEvent event) {
        addItemBox.getChildren().clear();
    }
}
