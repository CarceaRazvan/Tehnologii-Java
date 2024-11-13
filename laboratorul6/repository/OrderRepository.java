package com.example.lab6.repository;

import com.example.lab6.model.Order;
import com.example.lab6.model.OrderItem;
import com.example.lab6.model.Product;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class OrderRepository  extends DataRepository<Order, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    public OrderRepository() {
        super(Order.class);
    }

    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        return order != null ? order.getItems() : null;
    }


}
