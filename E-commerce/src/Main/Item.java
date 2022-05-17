package Main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class Item extends VBox {
    String name;
    int id;
    String seller;
    int quantity;
    double price;

    public Item(String name, int id, String seller, int quantity, double price) throws SQLException {
        this.id = id;
        this.name = name;
        this.seller = seller;
        this.quantity = quantity;
        this.price = price;
        Label itemName = new Label(this.name);
        itemName.setStyle("-fx-font-family: 'Bookman Old Style';-fx-font-size: 29 ; -fx-text-fill: black; -fx-font-weight: bold");

        Label itemId = new Label("Id: " + this.id);
        itemId.setStyle("-fx-font-family: 'Bookman Old Style';-fx-font-size: 19 ; -fx-text-fill: black; -fx-font-weight: bold");

        Label itemSeller = new Label(this.seller);
        itemSeller.setStyle("-fx-font-size: 20 ; -fx-text-fill: gray; -fx-font-weight: bold");

        Label itemQuantity = new Label("Available: " + this.quantity);
        itemSeller.setStyle("-fx-font-family: 'Arial Black';-fx-font-size: 17 ; -fx-text-fill: black;");

        Label itemPrice = new Label(price + "$");
        itemPrice.setStyle("-fx-font-size: 25 ; -fx-text-fill: red; -fx-font-weight: bold");

        // Buttons
        Button editBtn = new Button("edit");
        editBtn.getStyleClass().add("editBtn");
        Button deleteBtn = new Button("delete");
        deleteBtn.getStyleClass().add("deleteBtn");
        ToggleButton addToCard = new ToggleButton("add to card");
        addToCard.getStyleClass().add("addToCardBtn");
        HBox buttons = new HBox();

        if (seller.equals(User.getInstance().getUsername())){
            buttons.getChildren().setAll(editBtn, deleteBtn);
        }else {
            buttons.getChildren().setAll(addToCard);
        }


        addToCard.setOnAction(event -> {
            Statement statement = DBConnection.statement;
            if (addToCard.isSelected()){
                try {
                    statement.executeUpdate("insert into Card(itemId , userId) VALUES (" + id + ", " + User.getInstance().getUserId() + " )");
                    Logger.getInstance().log("Insert item " + name + " into user " + User.getInstance().getUsername() + " card");
                    statement.executeUpdate("update item set quantity = quantity - 1  WHERE id = " + id);
                    Logger.getInstance().log("quantity decreased by 1 for item " + name);
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
                addToCard.setText("✔");
                addToCard.getStyleClass().add("green");
            }else {
                try {
                    statement.executeUpdate("delete from Card where itemId = " + id + " and userId =  " + User.getInstance().getUserId());
                    Logger.getInstance().log("delete item " + name + " from user " + User.getInstance().getUsername() + " card");
                    statement.executeUpdate("update item set quantity = quantity + 1  WHERE id = " + id);
                    Logger.getInstance().log("quantity increased by 1 for item " + name);
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
                addToCard.setText("add to card");
                addToCard.getStyleClass().remove("green");
            }
        });

        buttons.setAlignment(Pos.CENTER_LEFT);
        buttons.setSpacing(15);
        buttons.setPadding(new Insets(10));
        buttons.setPrefWidth(300);
        buttons.setPrefHeight(75);

        getStyleClass().add("item");

        getStylesheets().add("style/style.css");
        getChildren().addAll(itemName, itemId, itemSeller, itemQuantity, itemPrice, buttons);

        deleteBtn.setOnAction(event -> {
            // dialog
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Delete item!");
            alert.setContentText("Are you sure, you want to delete this item?");
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(yes,no);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yes){
                // delete
                Statement statement = DBConnection.statement;
                int iid = Integer.parseInt(itemId.getText().split(" ")[1]);
//                System.out.println(iid);
                int i = 0;
                try {
                    i = statement.executeUpdate("Delete from Item where id = " + id);
                    Logger.getInstance().log("delete item " + name);
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }

                if (i == 1){
                    Alert msg = new Alert(Alert.AlertType.INFORMATION);
                    msg.setTitle("Success operation");
                    msg.setHeaderText("Item " + name + " deleted successfully!");
                    msg.setContentText("Item " + name + " deleted successfully!");
                    msg.showAndWait();
                }else {
                    Alert msg = new Alert(Alert.AlertType.WARNING);
                    msg.setHeaderText("Item " + name + " didn't deleted!");
                    msg.setContentText(null);
                    msg.showAndWait();
                }

            } else if (result.get() == no){
                alert.close();
            }
        });

        editBtn.setOnAction(event -> {
            try {
                edit();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });


    }

    private void edit() throws SQLException, IOException {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Edit item " + name);
        dialog.setHeaderText("Edit Item");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField iName = new TextField();
        iName.setPromptText("Item name: ");
        TextField iQuantity = new TextField();
        iQuantity.setPromptText("Item quantity");
        TextField iPrice = new TextField();
        iPrice.setPromptText("Item price");

        grid.add(new Label("Item name:"), 0, 0);
        grid.add(iName, 1, 0);
        grid.add(new Label("Quantity:"), 0, 1);
        grid.add(iQuantity, 1, 1);
        grid.add(new Label("Price:"), 0, 2);
        grid.add(iPrice, 1, 2);

        iName.setText(name);
        iQuantity.setText(String.valueOf(quantity));
        iPrice.setText(String.valueOf(price));

        dialog.getDialogPane().setContent(grid);

        ButtonType save = new ButtonType("save", ButtonBar.ButtonData.APPLY);
        ButtonType cancel = new ButtonType("cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getButtonTypes().setAll(save, cancel);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == save){
            String newName = iName.getText();
            int newQuantity = Integer.parseInt(iQuantity.getText());
            Double newPrice = Double.parseDouble(iPrice.getText());
            Statement statement = DBConnection.statement;
            int value = statement.executeUpdate("update item SET name = '" + newName + "' , quantity = " + newQuantity + ", price = " + newPrice + "where id = " + id);
            if (value == 1){
                Logger.getInstance().log("update item " + name);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Operation success");
                alert.setHeaderText("Item edited successfully!");
                alert.setContentText(null);
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Operation Field");
                alert.setHeaderText("Item field to edit!");
                alert.setContentText(null);
                alert.showAndWait();
            }
        }

    }

}
















