package Main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static int userId;
    public static int getUserId(){
        return userId;
    }
    public static void setId(int id){
        userId = id;
    }
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ecommerce?serverTimezone=UTC","root","");
            Logger.getInstance().log("connect to database");
            statement = connection.createStatement();

        } catch (ClassNotFoundException | IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Statement statement;

}
