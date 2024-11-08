package com.example.lab3.lab5compulsory.edit;


import com.example.lab3.lab5compulsory.model.Product;
import com.example.lab3.lab5compulsory.service.ProductService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
public class ProductBean {

    @Inject
    private ProductService productService;

    // Getters and Setters
    @Setter
    @Getter
    private Product product = new Product();

    @Getter
    private String message;

    public void addProduct() {
        try {
            productService.addProduct(product);
            message = "Product added successfully!";
        } catch (Exception e) {
            message = "Error adding product: " + e.getMessage();
        }
    }

}