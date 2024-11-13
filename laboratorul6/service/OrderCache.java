package com.example.lab6.service;


import com.example.lab6.interceptor.LogExecutionTimeInterceptor;
import com.example.lab6.model.Order;
import com.example.lab6.model.OrderItem;
import com.example.lab6.repository.OrderRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.interceptor.Interceptors;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Startup
@Data
@Interceptors(LogExecutionTimeInterceptor.class)
public class OrderCache {

    @EJB
    private OrderRepository orderRepository;

    private final Map<Long, Order> orderMap = new ConcurrentHashMap<>();

    public void addOrder(Long clientId, Order order) {
        orderMap.put(clientId, order);
    }

    public Order getOrder(Long clientId) {
        return orderMap.get(clientId);
    }

    public Map<Long, Order> getAllOrders() {
        return orderMap;
    }

    public void clearOrders() {
        orderMap.clear();
    }

    public List<OrderItem> retrieveOrderItems(Long orderId) {
        return orderRepository.getOrderItemsByOrderId(orderId);
    }
}
