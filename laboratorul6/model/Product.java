package com.example.lab6.model;


import com.example.lab6.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "product")
@Entity
@Data
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Product.findStockQuantityById",
                query = "SELECT s.quantity FROM InitialStock s WHERE s.product.id = :productId"),
        @NamedQuery(name = "Product.findByName",
                query = "SELECT p FROM Product p WHERE p.name = :productName")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private String category;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, optional = true)
    private InitialStock initialStock;

    public Product() {
        name = "InitProduct";
        description = "InitDesc";
        price = 0;
        category = "InitCategory";
        initialStock = null;

    }

    @Override
    public int hashCode() {
        // Avoid calling the hashCode of InitialStock if it's a cyclic reference
        return Objects.hash(id, name); // Example fields
    }

    @Override
    public String toString() {
        return name;
    }
}
