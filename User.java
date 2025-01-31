package book_my_show_pkg;
import java.sql.*;
import java.util.Scanner;

public class User {
    String name, email, password, phone;
    static User loggedInUser = null;

    public User(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public static void registerUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Name: ");
        String name = sc.nextLine();
        System.out.println("Enter Email: ");
        String email = sc.nextLine();
        System.out.println("Enter Password: ");
        String password = sc.nextLine();
        System.out.println("Enter Phone: ");
        String phone = sc.nextLine();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO users (name, email, password, phone) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, phone);
            ps.executeUpdate();
            System.out.println("Registration Successful!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Registration Failed!");
        }
    }

    public static void loginUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Email: ");
        String email = sc.nextLine();
        System.out.println("Enter Password: ");
        String password = sc.nextLine();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                loggedInUser = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("phone"));
                System.out.println("Login Successful! Welcome " + loggedInUser.name);
            } else {
                System.out.println("Invalid Email or Password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Login Failed!");
        }
    }

    public static void logoutUser() {
        loggedInUser = null;
        System.out.println("Logged out successfully!");
    }
}
