package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Dashboard extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/dashboard.fxml"));
        loader.setRoot(new AnchorPane());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("style/style.css");
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.setFullScreen(true);
        stage.show();
    }
















    public VBox itemBlock(String name , String seller, int quantity, double price){
        Label labelItemName = new Label(name);
        labelItemName.getStyleClass().add("labelName");
        Label sellerName = new Label(seller);
        Label itemQuantity = new Label(String.valueOf(quantity));
        Label itemPrice = new Label(price + "$");
        HBox itemDetails = new HBox(itemQuantity , itemPrice);
        itemDetails.setSpacing(10);

        Button deleteItem = new Button("Delete");
        deleteItem.setStyle("-fx-background-color: red; -fx-text-fill: white");
        Button editItem = new Button("edit");
        editItem.setOnAction(event -> {
        });
        Button addToCard = new Button("add to card");
        HBox buttons = new HBox( addToCard, editItem, deleteItem);
        buttons.setSpacing(20);

        VBox item = new VBox(labelItemName, sellerName , itemDetails, buttons);
        item.setAlignment(Pos.TOP_LEFT);
        item.setSpacing(20);
        item.setPadding(new Insets(20));

        item.getStyleClass().add("item");
        return item;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
