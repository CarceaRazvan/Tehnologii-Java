package com.example.lab6.view;


import com.example.lab6.model.Product;
import com.example.lab6.model.User;
import com.example.lab6.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.util.HashMap;
import java.util.Map;

@Named
@SessionScoped
public class ProductView extends DataView<Product, Long> {

    @EJB
    private ProductRepository productRepository;

    private Map<Long, Integer> productStockMap = new HashMap<>();  // Holds stock for each product

    @PostConstruct
    public void init() {
        this.repository = productRepository;
        super.init();  // Calls loadItems() to populate items list

        // Initialize stock for all products on page load
        for (Product product : items) {
            productStockMap.put(product.getId(), productRepository.getQuantityByProductId(product.getId()));
        }
    }

    public Integer fetchStock(Long productId) {
        // Fetch the updated stock value from the repository
        Integer quantity = productRepository.getQuantityByProductId(productId);
        productStockMap.put(productId, quantity);
        return quantity;
    }

//    public Integer getStockForProduct(Long productId) {
//        System.out.println("getStockForProduct");
//
//        return productStockMap.get(productId);
//    }
}