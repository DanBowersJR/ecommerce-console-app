package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    // Database credentials
    private static final String URL = "jdbc:postgresql://localhost:5432/ecommerce_db";
    private static final String USER = "postgres"; // Default PostgreSQL username
    private static final String PASSWORD = "1234"; // Your password

    // Method to establish a connection
    public static Connection getConnection() {
        try {
            // Load PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            // Return the database connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            // Print the error and return null
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }
}
