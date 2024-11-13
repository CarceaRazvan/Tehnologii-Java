package com.example.lab6.service;

import com.example.lab6.interceptor.LogExecutionTimeInterceptor;
import com.example.lab6.model.Order;
import com.example.lab6.model.OrderItem;
import com.example.lab6.model.Product;
import com.example.lab6.model.User;
import com.example.lab6.repository.InitialStockRepository;
import com.example.lab6.repository.OrderRepository;
import com.example.lab6.repository.ProductRepository;
import com.example.lab6.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.*;
import jakarta.interceptor.Interceptors;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Stateful
@Interceptors(LogExecutionTimeInterceptor.class)
public class OrderService{

    @Getter
    private List<OrderItem> orderItems;
    private Order order;

    @EJB
    private InitialStockRepository initialStockRepository;

    @EJB
    private ProductRepository productRepository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private OrderRepository orderRepository;

    @EJB
    private OrderCache orderCache;

    @PostConstruct
    public void init() {
        orderItems = new ArrayList<>();
        order = new Order();
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void addItem(String productName, int quantity) {
        Product product = productRepository.getProductByName(productName);

        Integer availableStock = productRepository.getQuantityByProductId(product.getId());

//        if (availableStock != null && availableStock >= quantity) {
            OrderItem item = new OrderItem(null, product, quantity);
            orderItems.add(item); // Add to the local list
//        } else {
//            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
//        }
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void registerOrder(String selectedUser) throws IllegalArgumentException{
        // First, check if the stock is sufficient for all items in the order
        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            Integer availableStock = productRepository.getQuantityByProductId(product.getId());

            if (availableStock == null || availableStock < item.getQuantity()) {
                String errorMessage = "Insufficient stock for product: " + product.getName();
                throw new IllegalArgumentException(errorMessage);
            }
        }

        order.setItems(orderItems);
        User user = userRepository.findByEmail(selectedUser);
        order.setUser(user);

        orderRepository.persist(order);

        for (OrderItem item : orderItems) {
            initialStockRepository.updateStock(item.getProduct().getId(), -item.getQuantity());
        }

        orderCache.addOrder(order.getId(), order);
    }

    public void clearOrder() {
        orderItems.clear();
    }

    @Remove
    public void save() {
        System.out.println("cleared order");
        orderItems.clear();
        order = null;
    }


}
