package com.example.lab6.init;

import com.example.lab6.model.InitialStock;
import com.example.lab6.model.Product;
import com.example.lab6.model.User;
import com.example.lab6.repository.InitialStockRepository;
import com.example.lab6.repository.ProductRepository;
import com.example.lab6.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Singleton
@Startup
public class DataInitializer {

    @EJB
    private InitialStockRepository initialStockRepository;

    @EJB
    private ProductRepository productRepository;

    @EJB
    private UserRepository userRepository;

    @PostConstruct
    @Transactional
    public void initializeData() {

        User user1 = userRepository.findByEmail("user1@example.com");
        if (user1 == null) {
            user1 = new User();
            user1.setName("User One");
            user1.setEmail("user1@example.com");
            user1.setGender("Male");
            userRepository.persist(user1);
        }

        User user2 = userRepository.findByEmail("user2@example.com");
        if (user2 == null) {
            user2 = new User();
            user2.setName("User Two");
            user2.setEmail("user2@example.com");
            user2.setGender("Female");
            userRepository.persist(user2);
        }


        // Add initial products if they don't already exist
        Product product1 = productRepository.findById(1L);
        if (product1 == null) {
            product1 = new Product();
            product1.setName("Product A");
            product1.setDescription("Description for Product A");
            product1.setPrice(29.99);
            product1.setCategory("Category A");
            productRepository.persist(product1);
        }

        Product product2 = productRepository.findById(2L);
        if (product2 == null) {
            product2 = new Product();
            product2.setName("Product B");
            product2.setDescription("Description for Product B");
            product2.setPrice(49.99);
            product2.setCategory("Category B");
            productRepository.persist(product2);
        }

        Product product3 = productRepository.findById(3L);
        if (product3 == null) {
            product3 = new Product();
            product3.setName("Product C");
            product3.setDescription("Description for Product C");
            product3.setPrice(19.99);
            product3.setCategory("Category C");
            productRepository.persist(product3);
        }

        // Initialize data for InitialStock table
        createInitialStock(product1, 100);
        createInitialStock(product2, 200);
        createInitialStock(product3, 150);

        System.out.println("Initial products and stock data initialized.");
    }

    private void createInitialStock(Product product, int quantity) {
        if (product != null) {
            InitialStock stock = initialStockRepository.newInstance();
            stock.setProduct(product);
            stock.setQuantity(quantity);
            initialStockRepository.persist(stock);
        }
    }
}