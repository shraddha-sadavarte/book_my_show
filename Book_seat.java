package book_my_show_pkg;
import java.sql.*;
import java.util.Scanner;

public class Book_seat {

    public static void bookSeat(String selectedMovieOrPlay, int price) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of tickets you want to book:");
        int ticketCount = sc.nextInt();
        int totalAmount = price * ticketCount;
        System.out.println("Total amount to be paid: ₹" + totalAmount);

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO bookings (user_email, movie_play_name, num_tickets, total_amount) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, User.loggedInUser.email);
            ps.setString(2, selectedMovieOrPlay);
            ps.setInt(3, ticketCount);
            ps.setInt(4, totalAmount);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int bookingId = 0;
            if (rs.next()) {
                bookingId = rs.getInt(1);
            }
            System.out.println("Booking successful! Booking ID: " + bookingId);
            Payment.processPayment(bookingId, totalAmount);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Booking Failed!");
        }
    }

    public static void cancelBooking() {
        if (User.loggedInUser == null) {
            System.out.println("You must be logged in to cancel a booking.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Booking ID to cancel: ");
        int bookingId = sc.nextInt();

        try (Connection con = DBConnection.getConnection()) {
            // ** Step 1: Delete payment first **
            try (PreparedStatement ps1 = con.prepareStatement("DELETE FROM payments WHERE booking_id = ?")) {
                ps1.setInt(1, bookingId);
                ps1.executeUpdate();
            }

            // ** Step 2: Delete booking after payment is removed **
            try (PreparedStatement ps2 = con.prepareStatement("DELETE FROM bookings WHERE id = ? AND user_email = ?")) {
                ps2.setInt(1, bookingId);
                ps2.setString(2, User.loggedInUser.email);
                int rowsAffected = ps2.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Booking ID " + bookingId + " has been successfully canceled.");
                } else {
                    System.out.println("No booking found with ID " + bookingId + " for this user.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: Could not cancel booking.");
        }
    }
}
