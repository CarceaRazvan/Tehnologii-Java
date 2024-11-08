package com.example.lab3.lab5compulsory.service;

import com.example.lab3.lab5compulsory.model.Product;
import com.example.lab3.lab5compulsory.repository.ProductDAO;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.List;

@Dependent
public class ProductService {

    @Inject
    private ProductDAO productDAO;

    public void addProduct(Product product) {
        productDAO.create(product);
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }



    public Product getProductById(Long id) {


        return productDAO.getProductById(id);
    }

    public void updateProduct(Product dialogProduct) {

        productDAO.updateProduct(dialogProduct);
    }
}