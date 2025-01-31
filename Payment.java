package book_my_show_pkg;

import java.sql.*;
import java.util.Scanner;

public class Payment {
    
    public static void processPayment(int bookingId, int amount) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Proceed with payment of ₹" + amount + "? (yes/no): ");
        String proceed = sc.next();

        if (!proceed.equalsIgnoreCase("yes")) {
            System.out.println("Payment cancelled.");
            return;
        }

        System.out.println("Select payment method: ");
        System.out.println("1. GPay");
        System.out.println("2. Debit Card");
        System.out.println("3. Credit Card");
        System.out.println("4. UPI");
        int method = sc.nextInt();
        
        String paymentMethod;
        switch (method) {
            case 1:
                paymentMethod = "GPay";
                break;
            case 2:
                paymentMethod = "Debit Card";
                break;
            case 3:
                paymentMethod = "Credit Card";
                break;
            case 4:
                paymentMethod = "UPI";
                break;
            default:
                System.out.println("Invalid payment method. Try again.");
                return;
        }

        System.out.println("Processing payment via " + paymentMethod + "...");
        try {
            Thread.sleep(2000); // Simulate processing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Payment of ₹" + amount + " successful via " + paymentMethod + "!");

        // Store payment details in database
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO payments (booking_id, amount, payment_method, status) VALUES (?, ?, ?, ?)")) {
            
            ps.setInt(1, bookingId);
            ps.setInt(2, amount);
            ps.setString(3, paymentMethod);
            ps.setString(4, "Success");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
