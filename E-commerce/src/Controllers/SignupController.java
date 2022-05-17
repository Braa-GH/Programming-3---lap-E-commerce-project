package Controllers;

import Main.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    Statement statement;
    ResultSet resultSet;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      statement = DBConnection.statement;
    }
    @FXML
    private PasswordField passField;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtFieldEmail;
    @FXML
    private Button btnSignup;
    @FXML
    private TextField txtFieldUsername;
    @FXML
    private Label warningLabel;
    @FXML
    private BorderPane root;
    @FXML
    void actionSignup(ActionEvent event) throws Exception {
        String email = txtFieldEmail.getText();
        String username = txtFieldUsername.getText();
        if (!email.contains("@")){
            warningLabel.setText("Please enter a valid e-mail!");
        }else {
            String checkEmailQuery = "SELECT * FROM `User` WHERE email = '" + email + "'";
            Logger.getInstance().log("check email: " + email);
            resultSet =  this.statement.executeQuery(checkEmailQuery);
            boolean ok = true;
            while (resultSet.next()){
                warningLabel.setText("This Email is already exist!");
                ok = false;
            }
                if (ok){
                    resultSet = statement.executeQuery("SELECT * FROM `User` WHERE username = '" + username + "'");
                    if (!resultSet.next()){
//                        String password = BEncryption.BEncrypt(passField.getText());
                        String password = MD5.getInstance().encrypt(passField.getText());
                        String registerQuery = "INSERT INTO `User` (username, email, password) VALUES ('" + username + "' , '" + email + "' , '" + password + "')";
                        statement.executeUpdate(registerQuery);
                        Logger.getInstance().log("Insert new User " + username);
                        warningLabel.setText("");
                        ((Stage)root.getScene().getWindow()).close();
                        Dashboard dashboard = new Dashboard();
                        User user1 = User.getInstance();
                        user1.setUsername(username);
                        dashboard.start(new Stage());
                    }else {
                        warningLabel.setText("User is exist!");
                    }
                }


        }
    }
    @FXML
    void actionLogin(ActionEvent event) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("../fxml/login.fxml"));
        ((Stage)root.getScene().getWindow()).setScene(new Scene(loginLoader.load()));
    }
}
