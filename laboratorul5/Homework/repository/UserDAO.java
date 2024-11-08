package com.example.lab3.lab5compulsory.repository;

import com.example.lab3.lab5compulsory.model.Product;
import com.example.lab3.lab5compulsory.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.*;
import javax.ejb.Stateless;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

@Dependent
public class UserDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyWebApplicationPU");

    public void create(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); // Start transaction
            em.persist(user);
            em.getTransaction().commit(); // Commit transaction
        } catch (Exception e) {
            em.getTransaction().rollback(); // Rollback on error
            throw e;
        } finally {
            em.close(); // Ensure the EntityManager is closed
        }
    }

    public User findById(Long id) {
        EntityManager em = emf.createEntityManager(); // Create EntityManager from the EntityManagerFactory
        try {
            return em.createNamedQuery("User.findById", User.class) // Use the named query to fetch the user by ID
                    .setParameter("id", id) // Set the ID parameter for the query
                    .getSingleResult(); // Get the single result
        } catch (NoResultException e) {
            return null; // Return null if no user is found with the provided ID
        } catch (Exception e) {
            throw new RuntimeException("Error finding user by ID", e); // Throw runtime exception if there's an error
        } finally {
            em.close(); // Ensure the EntityManager is closed
        }
    }

    // Find all users using the named query
    public List<User> findAllUsers() {
        EntityManager em = emf.createEntityManager(); // Create EntityManager from the EntityManagerFactory
        try {
            return em.createNamedQuery("User.findAll", User.class) // Use the named query to fetch all users
                    .getResultList(); // Get the result list
        } catch (Exception e) {
            throw new RuntimeException("Error finding all users", e); // Throw runtime exception in case of error
        } finally {
            em.close(); // Ensure the EntityManager is closed
        }
    }

    public void updateUserProducts(Long userId, List<Product> productsUser) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            // Fetch the User entity
            User user = em.find(User.class, userId);

            // Clear existing products if needed
            user.getProducts().clear();

            // Add new products to the user
            for (Product product : productsUser) {
                user.getProducts().add(product);
                product.getUsers().add(user);  // Make sure the product also references the user
            }

            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;  // Handle exception as appropriate
        } finally {
            em.close();
        }
    }

    public List<Product> findProductsByUserId(Long userId) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createNamedQuery("User.findProductsByUserId", Product.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
