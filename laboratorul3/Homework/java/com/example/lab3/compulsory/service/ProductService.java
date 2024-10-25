package com.example.lab3.compulsory.service;

import com.example.lab3.compulsory.dao.ProductDAO;
import com.example.lab3.compulsory.model.Product;

import java.util.List;

public class ProductService {

    private ProductDAO productDAO = new ProductDAO();

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public void addProduct(Product product) {
        productDAO.save(product);
    }


    public Product getProductById(Long id) {
        return productDAO.getProductById(id);
    }
}