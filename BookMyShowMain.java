package book_my_show_pkg;

import java.sql.Connection;
import java.util.Scanner;

public class BookMyShowMain {
    public static void main(String[] args) {
        // Test the database connection
        Connection testConnection = DBConnection.getConnection();
        if (testConnection == null) {
            System.out.println("Database connection failed. Exiting application...");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Welcome to BookMyShow =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Browse Movies/Plays");
            System.out.println("4. Cancel a Booking");
            System.out.println("5. Logout");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    User.registerUser();
                    break;

                case 2:
                    User.loginUser();
                    break;

                case 3:
                    if (User.loggedInUser == null) {
                        System.out.println("Please login first!");
                    } else {
                        System.out.print("Enter city (Mumbai, Delhi, Pune): ");
                        String city = sc.nextLine();
                        System.out.print("Do you want to see Movies or Plays? ");
                        String type = sc.nextLine();
                        Moviesplays mp = new Moviesplays();
                        mp.displayCity(city, type);
                    }
                    break;

                case 4:
                    if (User.loggedInUser == null) {
                        System.out.println("Please login first!");
                    } else {
                        Book_seat.cancelBooking();
                    }
                    break;

                case 5:
                    if (User.loggedInUser != null) {
                        User.logoutUser();
                    } else {
                        System.out.println("No user is logged in.");
                    }
                    break;

                case 6:
                    System.out.println("Thank you for using BookMyShow! Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        sc.close();
    }
}
