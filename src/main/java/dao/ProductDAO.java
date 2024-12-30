package dao;

import model.Product;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {

    // Method to insert a new product
    public boolean insertProduct(Product product) {
        String sql = "INSERT INTO products (name, price, quantity, seller_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setInt(4, product.getSellerId());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            System.out.println("Error inserting product: " + e.getMessage());
            return false;
        }
    }

    // Method to retrieve a product by ID
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("quantity"),
                            resultSet.getInt("seller_id")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving product: " + e.getMessage());
        }

        return null; // Return null if no product is found
    }
}
