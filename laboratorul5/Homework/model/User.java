package com.example.lab3.lab5compulsory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "user_app")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(
                name = "User.findProductsByUserId",
                query = "SELECT p FROM Product p JOIN p.users u WHERE u.id = :userId"
        )

})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String gender;

    @ManyToMany(mappedBy = "users")  // references the "users" field in the Product entity
    private Set<Product> products = new HashSet<>();
}
