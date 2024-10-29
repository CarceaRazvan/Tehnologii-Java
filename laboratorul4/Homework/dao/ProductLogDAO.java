package com.example.lab3.compulsory.dao;

import com.example.lab3.compulsory.config.JndiConnection;
import com.example.lab3.compulsory.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductLogDAO {

    private final DataSource ds = JndiConnection.InitializeDataSource();

    public void insertModificationLog(String username, String description)  {
        String insertSQL = "INSERT INTO products_logs (username, modified_timestamp, description) VALUES (?, ?, ?)";
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, username);
            pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));  // Set current timestamp
            pstmt.setString(3, description);

            pstmt.executeUpdate();
            System.out.println("Modification log inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getLastModificationLog() {
        String selectSQL = "SELECT username, modified_timestamp, description FROM products_logs ORDER BY modified_timestamp DESC LIMIT 1";
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                String username = rs.getString("username");
                Timestamp timestamp = rs.getTimestamp("modified_timestamp");
                String description = rs.getString("description");
                return "Last modified by: " + username + " at " + timestamp + " Modifications: " + description;
            } else {
                return "No modification logs found.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "No modification logs found.";
        }
    }

}
