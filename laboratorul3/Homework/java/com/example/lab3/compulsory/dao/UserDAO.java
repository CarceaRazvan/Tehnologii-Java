package com.example.lab3.compulsory.dao;

import com.example.lab3.compulsory.config.DatabaseConnection;
import com.example.lab3.compulsory.model.Product;
import com.example.lab3.compulsory.model.User;
import org.primefaces.model.DualListModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users"; // Ensure the users table has the correct columns
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setBirthDate(rs.getDate("birth_date"));
                user.setGender(rs.getString("gender"));
                user.setRoles(rs.getString("roles").split(","));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User findById(Long id) {
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setBirthDate(rs.getDate("birth_date"));
                    user.setGender(rs.getString("gender"));
                    user.setRoles(rs.getString("roles").split(","));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user; // Returns null if user is not found
    }

    public void insertUser(User user) {
        String query = "INSERT INTO users (name, email, birth_date, gender, roles) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setDate(3, new java.sql.Date(user.getBirthDate().getTime())); // Convert java.util.Date to java.sql.Date
            stmt.setString(4, user.getGender());
            stmt.setString(5, String.join(",", user.getRoles())); // Assuming roles are stored as a comma-separated string
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {

        if (user.getId() == -1L) {

            String query = "INSERT INTO users (name, email, birth_date, gender, roles) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setDate(3, new java.sql.Date(user.getBirthDate().getTime())); // Convert java.util.Date to java.sql.Date
                stmt.setString(4, user.getGender());
                stmt.setString(5, String.join(",", user.getRoles())); // Assuming roles are stored as a comma-separated string
                stmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {


            String query = "UPDATE users SET name = ?, email = ?, birth_date = ?, gender = ?, roles = ? WHERE id = ?";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());

                stmt.setDate(3, new java.sql.Date(user.getBirthDate().getTime()));
                stmt.setString(4, user.getGender());
                stmt.setString(5, String.join(",", user.getRoles()));
                stmt.setLong(6, user.getId());
                stmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void delete(Long userId) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, userId);
            stmt.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Product> findProductsByUserId(Long userId) {

        List<Product> products = new ArrayList<>();


        String query = "SELECT p.id, p.name, p.description, p.price, p.quantity, p.category " +
                "FROM products p " +
                "JOIN user_products up ON p.id = up.product_id " +
                "WHERE up.user_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();

             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    products.add(product);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;

    }


    public void updateUserProducts(Long userId, List<Product> productsUser) {
        String deleteQuery = "DELETE FROM user_products WHERE user_id = ?";
        String insertQuery = "INSERT INTO user_products (user_id, product_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {

            // 1. Șterge toate asociările existente pentru utilizator
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setLong(1, userId);
                deleteStmt.executeUpdate();
            }

            // 2. Inserează noile produse pentru utilizator
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                for (Product product : productsUser) {
                    insertStmt.setLong(1, userId);
                    insertStmt.setLong(2, product.getId());
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}