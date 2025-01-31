package book_my_show_pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/book_my_show";
    private static final String USER = "root"; 
    private static final String PASSWORD = "shraddha@24/2003";

    // Method to establish a connection
    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database connection failed!");
        }
        return con;
    }
}
