import controller.command.RegistrationCommand;
import model.dao.impl.ConnectionPoolHolder;
import model.entity.User;
import model.service.UserService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    private static final org.apache.logging.log4j.Logger log
            = org.apache.logging.log4j.LogManager.getLogger(App.class);
    public static void main(String[] args) throws SQLException {
//        System.out.println(new UserService().getRoleByUser("user", "j"));
//        new UserService().saveUser(new User("username","password"));
//        System.out.println(new UserService().getRoleByUser("username", "password"));
//        Connection con =
//                ConnectionPoolHolder.getDataSource().
//                        getConnection();
//
//        Statement query = con.createStatement();
//        ResultSet rs = query.executeQuery("SELECT * FROM users");
//        while (rs.next()) {
//            System.out.println(rs.getString("username"));
//        }


//
//        System.out.println("Hello!");
//        Connection con =
//                DriverManager.
//                        getConnection("jdbc:postgresql://localhost:5432/basic_user",
//                                "postgres",
//                                "123454321");
//
//        Statement query = con.createStatement();
//        ResultSet rs = query.executeQuery("SELECT * FROM users");
//        while (rs.next()) {
//            System.out.println(rs.getString("name"));
//        }

    }
}
