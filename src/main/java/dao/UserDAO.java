package dao;

import model.User;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Method to insert a new user into the database
    public boolean insertUser(User user) {
        String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
            return false;
        }
    }

    // Method to retrieve a user by username
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("role")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }

        return null; // Return null if no user is found
    }
}
