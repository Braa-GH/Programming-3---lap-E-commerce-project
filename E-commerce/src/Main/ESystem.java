package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;

public class ESystem extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Statement statement = DBConnection.statement;
        ResultSet resultSet = statement.executeQuery("SELECT  * FROM `User`");
        Logger.getInstance().log("select all data from user");
        Parent root;
        if (resultSet.next()){
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("../fxml/login.fxml"));
            root = loginLoader.load();
            stage.setTitle("Login page");
        }else {
            FXMLLoader signupLoader = new FXMLLoader(getClass().getResource("../fxml/signup.fxml"));
            signupLoader.setRoot(new BorderPane());
            root = signupLoader.load();
            stage.setTitle("Signup page");
        }
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/dashboard.fxml"));
//        loader.setRoot(new AnchorPane());
//        root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
