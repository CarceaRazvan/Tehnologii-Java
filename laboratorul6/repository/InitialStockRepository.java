package com.example.lab6.repository;

import com.example.lab6.model.InitialStock;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Stateless
public class InitialStockRepository extends DataRepository<InitialStock, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    public InitialStockRepository() {
        super(InitialStock.class);
    }

    public void updateStock(Long productId, int quantityChange) {
        try {
            InitialStock initialStock = entityManager.createNamedQuery("InitialStock.findByProductId", InitialStock.class)
                    .setParameter("productId", productId)
                    .getSingleResult();

            if (initialStock != null) {
                initialStock.setQuantity(initialStock.getQuantity() + quantityChange);
                entityManager.merge(initialStock);
            }
        } catch (NoResultException e) {
            System.err.println("No InitialStock found for product ID: " + productId);
        }
    }
}
