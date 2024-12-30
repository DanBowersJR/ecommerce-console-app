package app;

import dao.ProductDAO;
import dao.UserDAO;
import model.Product;
import model.User;

public class Main {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        // Step 1: Create a new product
        Product newProduct = new Product(0, "Java Programming Book", 29.99, 100, 1);

        // Step 2: Insert the product into the database
        if (productDAO.insertProduct(newProduct)) {
            System.out.println("Product inserted successfully!");
        } else {
            System.out.println("Failed to insert product.");
        }

        // Step 3: Retrieve the product by its ID
        Product retrievedProduct = productDAO.getProductById(1);
        if (retrievedProduct != null) {
            System.out.println("Retrieved Product: " + retrievedProduct);
        } else {
            System.out.println("Product not found.");
        }
    }
}
