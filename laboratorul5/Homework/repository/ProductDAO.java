package com.example.lab3.lab5compulsory.repository;


import com.example.lab3.lab5compulsory.model.Product;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

@Dependent
public class ProductDAO {


    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyWebApplicationPU");

    public void create(Product product) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Product> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createNamedQuery("Product.findAll", Product.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Product getProductById(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createNamedQuery("Product.getProductById", Product.class)
                    .setParameter("productId", id)
                    .getSingleResult();
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        } finally {
            em.close();
        }
    }

    public void updateProduct(Product dialogProduct) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(dialogProduct);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void deleteProduct(Product product) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();  // Start transaction

            Product productToDelete = em.find(Product.class, product.getId());
            if (productToDelete != null) {
                em.remove(productToDelete);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}