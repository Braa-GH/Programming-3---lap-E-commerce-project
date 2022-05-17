package Controllers;


import Main.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;


public class DashboardController implements Initializable {

    @FXML
    private ToolBar toolbar;
    @FXML
    private Button logoutBtn;
    @FXML
    private VBox leftNav;
    @FXML
    private Button addItemBtn;
    @FXML
    private Button showBtn;
    @FXML
    private FlowPane items;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField searchField;
    @FXML
    private Button cardBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private Button showAllBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private ComboBox<String> searchCombo;

    @FXML
    void actionShowAll(ActionEvent event) throws SQLException, IOException {
        show("select * from item");
    }
    @FXML
    void actionCard(ActionEvent event) throws SQLException, IOException {
       show("select i.* from item i INNER JOIN card c ON i.id = c.itemId where c.UserId = " + User.getInstance().getUserId());
    }

    @FXML
    void actionSearch(ActionEvent event) throws SQLException, IOException {
        String value = searchField.getText();
        String key = "name";
        if (searchCombo.getSelectionModel().getSelectedIndex() == 0){
            key = "seller";
        } else if (searchCombo.getSelectionModel().getSelectedIndex() == 1) {
            key = "name";
        }
        show("select * from item where " + key + " = '" + value + "'");

    }


    @FXML
    void actionShow(ActionEvent event) throws SQLException, IOException {
        show("SELECT * FROM item where seller = '" + User.getInstance().getUsername() + "'");
    }

    @FXML
    void actionAddItem(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/addItem.fxml"));
       loader.setRoot(new VBox());
       VBox box = loader.load();
       items.getChildren().setAll(box);
    }

    @FXML
    void actionLogout(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText("Log out");
        alert.setContentText("Are you sure?");

        ButtonType logout = new ButtonType("logout", ButtonBar.ButtonData.FINISH);
        ButtonType back = new ButtonType("back", ButtonBar.ButtonData.BACK_PREVIOUS);
        alert.getButtonTypes().setAll(logout, back);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == logout){
            ((Stage)root.getScene().getWindow()).close();
            new ESystem().start(new Stage());

        } else if (result.get() == back){
            alert.close();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            show("select * from item");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        searchCombo.getItems().addAll("seller name", "item name");
        searchCombo.getSelectionModel().select(1);
    }

    public void show(String sql) throws SQLException, IOException {
        Statement statement = DBConnection.statement;
        ResultSet resultSet = statement.executeQuery(sql);
        items.getChildren().clear();

        while (resultSet.next()){
            String name = resultSet.getString("name");
            int id = resultSet.getInt("id");
            String seller = resultSet.getString("seller");
            int quantity = resultSet.getInt("quantity");
            double price = resultSet.getDouble("price");
            items.getChildren().add(new Item(name, id, seller, quantity, price));
        }
        Logger.getInstance().log("show items from item table");
    }
    @FXML
    void actionExit(ActionEvent event) throws IOException {
        Logger.getInstance().log("Session finished");
        System.exit(0);
    }
}
