package book_my_show_pkg;

import java.sql.*;
import java.util.Scanner;

public class Moviesplays {

    public void displayCity(String selectedCity, String choice) {
        try (Connection con = DBConnection.getConnection()) {
            String query;
            if (choice.equalsIgnoreCase("Movies")) {
                query = "SELECT name, price FROM movies WHERE city=?";
            } else if (choice.equalsIgnoreCase("Plays")) {
                query = "SELECT name, price FROM plays WHERE city=?";
            } else {
                System.out.println("Invalid choice! Please choose either 'Movies' or 'Plays'.");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                ps.setString(1, selectedCity);
                ResultSet rs = ps.executeQuery();

                if (!rs.isBeforeFirst()) { // Check if the result set is empty
                    System.out.println("No " + choice.toLowerCase() + " available in " + selectedCity);
                    return;
                }

                System.out.println(choice + " available in " + selectedCity + ":");
                int index = 1;
                while (rs.next()) {
                    System.out.println(index + ". " + rs.getString("name") + " - Rs." + rs.getInt("price"));
                    index++;
                }

                Scanner sc = new Scanner(System.in);
                System.out.println("Select by entering the number:");
                int selectedOption = sc.nextInt();

                // Reset ResultSet cursor for selection
                rs.beforeFirst();
                int count = 1;

                while (rs.next()) {
                    if (count == selectedOption) {
                        String selectedMovieOrPlay = rs.getString("name");
                        int price = rs.getInt("price");
                        Book_seat.bookSeat(selectedMovieOrPlay, price);
                        break;
                    }
                    count++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error: Could not fetch movie/play list.");
        }
    }
}
