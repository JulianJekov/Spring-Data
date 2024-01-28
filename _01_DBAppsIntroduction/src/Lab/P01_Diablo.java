package Lab;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class P01_Diablo {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter user default (root): ");
        String user = scanner.nextLine();
        user = user.trim().isEmpty() ? "root" : user;

        System.out.println();

        System.out.print("Enter password default (empty): ");
        String password = scanner.nextLine();

        System.out.println();

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", properties);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        PreparedStatement query = connection.prepareStatement("SELECT user_name, first_name, last_name, \n" +
                "(\n" +
                "SELECT COUNT(*) \n" +
                "FROM users_games AS ug\n" +
                "WHERE ug.user_id = u.id\n" +
                ") AS games_count\n" +
                "FROM users AS u\n" +
                "WHERE user_name = ?");
        query.setString(1, username);

        ResultSet result = query.executeQuery();


        if (result.next()) {

            String userName = result.getString("user_name");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            int gamesCount = result.getInt("games_count");

            System.out.printf("User: %s%n%s %s has played %d games", userName, firstName, lastName, gamesCount);
        } else {
            System.out.println("No such user exists");
        }
    }
}