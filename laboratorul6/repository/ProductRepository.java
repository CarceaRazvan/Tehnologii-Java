package com.example.lab6.repository;

import com.example.lab6.interceptor.LogExecutionTimeInterceptor;
import com.example.lab6.model.Product;
import com.example.lab6.model.User;
import jakarta.ejb.Stateless;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
@Interceptors(LogExecutionTimeInterceptor.class)
public class ProductRepository extends DataRepository<Product, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    public ProductRepository() {
        super(Product.class);
    }

    public Integer getQuantityByProductId(Long productId) {
        try {
            return entityManager.createNamedQuery("Product.findStockQuantityById", Integer.class)
                    .setParameter("productId", productId)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Product getProductByName(String productName) {
        try {
            return entityManager.createNamedQuery("Product.findByName", Product.class)
                    .setParameter("productName", productName)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Or handle it based on your application's error handling strategy
        }
    }

}
