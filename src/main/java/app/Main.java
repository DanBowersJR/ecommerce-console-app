package app;

import dao.ProductDAO;
import dao.UserDAO;
import model.Product;
import model.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final UserDAO userDAO = new UserDAO();
    private static final ProductDAO productDAO = new ProductDAO();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Welcome to the E-Commerce Console App ===");
            System.out.println("1. Register User");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> registerUser(scanner);
                    case 2 -> login(scanner);
                    case 3 -> {
                        running = false;
                        System.out.println("Exiting the application. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        scanner.close();
    }

    private static void registerUser(Scanner scanner) {
        System.out.println("\n--- Register User ---");
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter role (buyer/seller/admin): ");
            String role = scanner.nextLine().toLowerCase();

            if (!role.equals("buyer") && !role.equals("seller") && !role.equals("admin")) {
                System.out.println("Invalid role. Please enter 'buyer', 'seller', or 'admin'.");
                return;
            }

            User user = new User(0, username, password, email, role);
            if (userDAO.insertUser(user)) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Failed to register user. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while registering the user: " + e.getMessage());
        }
    }

    private static void login(Scanner scanner) {
        System.out.println("\n--- Login ---");
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (userDAO.authenticateUser(username, password)) { // Use authentication
                User user = userDAO.getUserByUsername(username);
                System.out.println("Login successful! Welcome, " + user.getUsername() + ".");
                switch (user.getRole().toLowerCase()) {
                    case "buyer" -> buyerMenu(scanner, user);
                    case "seller" -> sellerMenu(scanner, user);
                    case "admin" -> adminMenu(scanner, user);
                    default -> System.out.println("Unknown role. Access denied.");
                }
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
        }
    }

    private static void buyerMenu(Scanner scanner, User user) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Buyer Menu ---");
            System.out.println("1. Browse Products");
            System.out.println("2. Search for Product");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1 -> browseProducts();
                    case 2 -> searchProduct(scanner);
                    case 3 -> {
                        running = false;
                        System.out.println("Logging out. Goodbye, " + user.getUsername() + "!");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private static void sellerMenu(Scanner scanner, User user) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Seller Menu ---");
            System.out.println("1. Add Product");
            System.out.println("2. View My Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1 -> addProduct(scanner, user);
                    case 2 -> viewMyProducts(user);
                    case 3 -> updateProduct(scanner, user);
                    case 4 -> deleteProduct(scanner, user);
                    case 5 -> {
                        running = false;
                        System.out.println("Logging out. Goodbye, " + user.getUsername() + "!");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private static void adminMenu(Scanner scanner, User user) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Users");
            System.out.println("2. Delete User");
            System.out.println("3. View All Products");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1 -> viewAllUsers();
                    case 2 -> deleteUser(scanner);
                    case 3 -> viewAllProducts();
                    case 4 -> {
                        running = false;
                        System.out.println("Logging out. Goodbye, Admin " + user.getUsername() + "!");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private static void browseProducts() {
        System.out.println("\n--- Browsing All Products ---");
        List<Product> products = productDAO.getAllProducts();
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void searchProduct(Scanner scanner) {
        System.out.print("\nEnter product name to search: ");
        String productName = scanner.nextLine();
        List<Product> products = productDAO.searchProductsByName(productName);
        if (products.isEmpty()) {
            System.out.println("No products found with name: " + productName);
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    private static void addProduct(Scanner scanner, User seller) {
        System.out.println("\n--- Add Product ---");
        try {
            System.out.print("Enter product name: ");
            String name = scanner.nextLine();
            System.out.print("Enter product price: ");
            double price = scanner.nextDouble();
            System.out.print("Enter product quantity: ");
            int quantity = scanner.nextInt();

            Product product = new Product(0, name, price, quantity, seller.getId());
            if (productDAO.insertProduct(product)) {
                System.out.println("Product added successfully!");
            } else {
                System.out.println("Failed to add product.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
            scanner.nextLine(); // Clear invalid input
        }
    }

    private static void viewMyProducts(User seller) {
        System.out.println("\n--- My Products ---");
        List<Product> products = productDAO.getProductsBySellerId(seller.getId());
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void updateProduct(Scanner scanner, User seller) {
        System.out.println("\n--- Update Product ---");
        System.out.print("Enter product ID to update: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new quantity: ");
        int quantity = scanner.nextInt();

        Product updatedProduct = new Product(productId, name, price, quantity, seller.getId());
        if (productDAO.updateProduct(updatedProduct)) {
            System.out.println("Product updated successfully!");
        } else {
            System.out.println("Failed to update product.");
        }
    }

    private static void deleteProduct(Scanner scanner, User seller) {
        System.out.print("\nEnter product ID to delete: ");
        int productId = scanner.nextInt();
        if (productDAO.deleteProduct(productId, seller.getId())) {
            System.out.println("Product deleted successfully!");
        } else {
            System.out.println("Failed to delete product.");
        }
    }

    private static void viewAllUsers() {
        System.out.println("\n--- All Users ---");
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }

    private static void deleteUser(Scanner scanner) {
        System.out.print("\nEnter user ID to delete: ");
        int userId = scanner.nextInt();
        if (userDAO.deleteUser(userId)) {
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("Failed to delete user.");
        }
    }

    private static void viewAllProducts() {
        System.out.println("\n--- All Products ---");
        List<Product> products = productDAO.getAllProducts();
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
