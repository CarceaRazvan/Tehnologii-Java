package com.example.lab3.lab5compulsory.model;


import com.example.lab3.lab5compulsory.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Table(name = "product")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
        @NamedQuery(
                name = "Product.getProductById",
                query = "SELECT p FROM Product p WHERE p.id = :productId"
        )
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private int quantity;
    private String category;

    @ManyToMany
    @JoinTable(
            name = "user_product",  // Name of the join table
            joinColumns = @JoinColumn(name = "product_id"),  // Foreign key column for Product
            inverseJoinColumns = @JoinColumn(name = "user_id")  // Foreign key column for User
    )
    private Set<User> users = new HashSet<>(); // Set to avoid duplicates

}
