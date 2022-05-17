package Controllers;

import Main.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
    Statement statement;
    ResultSet resultSet;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statement = DBConnection.statement;
    }

    @FXML
    private BorderPane root;
    @FXML
    private PasswordField passField;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignup;
    @FXML
    private TextField txtFieldUsername;
    @FXML
    private Label warningLabel;
    @FXML
    void actionLogin(ActionEvent event) throws Exception {
        String username = txtFieldUsername.getText();
        String checkNameQuery = "SELECT username FROM User WHERE username = '" + username + "'";
        resultSet = statement.executeQuery(checkNameQuery);
        Logger.getInstance().log("check username: " + username);
        boolean complete = resultSet.next();
        if (!complete){
            warningLabel.setText("User is not exist!");
            Logger.getInstance().log("username " + username + " is not exist");
        }else {
            String password = passField.getText();
            String checkPassQuery = "SELECT password FROM User WHERE username = '" + username + "'";
            Logger.getInstance().log("check password for username: " + username);
            resultSet = statement.executeQuery(checkPassQuery);

            boolean decrypted = false;
            if (resultSet.next()){ // check using md5 for new users
                if (MD5.getInstance().encrypt(password).equals(resultSet.getString("password"))){
                    ((Stage)root.getScene().getWindow()).close();
                    Dashboard dashboard = new Dashboard();
                    User user1 = User.getInstance();
                    user1.setUsername(username);
                    dashboard.start(new Stage());
                    decrypted = true;
                }
               if (!decrypted){
                   // check using BEncryption for old users
                   if (!BEncryption.BDecrypt(resultSet.getString("password")).equals(password)){
                       warningLabel.setText("Invalid password");
                       Logger.getInstance().log("Incorrect password");
                   }else {
                       warningLabel.setText("");
                       ((Stage)root.getScene().getWindow()).close();
                       Dashboard dashboard = new Dashboard();
                       User user1 = User.getInstance();
                       user1.setUsername(username);
                       dashboard.start(new Stage());
                   }
               }
            }


        }

    }
    @FXML
    void actionSignup(ActionEvent event) throws IOException {
        FXMLLoader signupLoader = new FXMLLoader(getClass().getResource("../fxml/signup.fxml"));
        signupLoader.setRoot(new BorderPane());
        ((Stage)root.getScene().getWindow()).setScene(new Scene(signupLoader.load()));
    }


}
