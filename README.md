# E-Commerce Console Application

This is a console-based E-Commerce application built using Java, Maven, and PostgreSQL. The project simulates an online marketplace where users can register and perform role-based actions such as browsing products, managing listings, and handling administrative tasks.

---

## Features

### User Roles
1. **Buyer**:
   - Browse all available products.
   - Search for products by name.
   
2. **Seller**:
   - Add new products with details (name, price, quantity).
   - Update existing product information.
   - Delete products.
   - View their listed products.
   
3. **Admin**:
   - View all users and products in the system.
   - Delete users as necessary.

---

### Secure Authentication
Passwords are securely hashed and stored using BCrypt for enhanced security.

---

### Database Integration
PostgreSQL is used to store data:
- Users Table: Stores user information, including username, password, role, and email.
- Products Table: Stores product details, such as name, price, quantity, and seller ID.
Full CRUD operations are supported: Create, Read, Update, and Delete.

---

### Console-Based Interface
The application uses a simple Command-Line Interface (CLI) for user interaction. Role-based menus are provided for buyers, sellers, and admins.

---

## Technology Stack

- Programming Language: Java
- Build Tool: Maven
- Database: PostgreSQL
- Version Control: Git/GitHub

---

## Setup Instructions

### Prerequisites
- Java 11 or higher.
- PostgreSQL installed, with a database created.
- Maven installed.

### Steps to Set Up

1. Clone the repository:
   ```bash
   git clone https://github.com/DanBowersJR/ecommerce-console-app.git
   cd ecommerce-console-app
