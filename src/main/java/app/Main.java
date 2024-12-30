package app;

import util.DatabaseUtil;

public class Main {
    public static void main(String[] args) {
        // Test the database connection
        if (DatabaseUtil.getConnection() != null) {
            System.out.println("Database connected successfully!");
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}
