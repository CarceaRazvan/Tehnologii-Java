package com.example.lab6.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity
@Table(name = "initial_stock")
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "InitialStock.findByProductId",
                query = "SELECT s FROM InitialStock s WHERE s.product.id = :productId"
        )
})
public class InitialStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", unique = true, nullable = false)
    private Product product;

    private int quantity;

    @Override
    public String toString() {
        return product.getName();
    }

    @Override
    public int hashCode() {
        // Generate a unique hash based on non-recursive fields only
        return Objects.hash(id, quantity); // Example fields
    }
}
