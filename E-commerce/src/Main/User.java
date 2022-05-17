package Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    public String username;
    public static User instance = null;
    private User(){}

    public static User getInstance() {
        if (instance == null){
            instance = new User();
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() throws SQLException {
        int id = -1;
        Statement statement = DBConnection.statement;
        ResultSet resultSet = statement.executeQuery("select id from user where username = '" + username + "'");
        if (resultSet.next()){
             id = resultSet.getInt("id");
        }
        return id;
    }
}
