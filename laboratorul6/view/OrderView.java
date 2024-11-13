package com.example.lab6.view;

import com.example.lab6.model.Order;
import com.example.lab6.model.OrderItem;
import com.example.lab6.service.OrderCache;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named("cache")
@ApplicationScoped
public class OrderView implements Serializable {

    @EJB
    private OrderCache orderCache;

    Map<Long, Order> allOrdersMap;

    public Map<Long, Order> getOrders() {
        if (allOrdersMap == null) {
            allOrdersMap = orderCache.getAllOrders();
        }
        return allOrdersMap;
    }

    public List<OrderItem> retrieveOrderItems(Long orderId) {
        return orderCache.retrieveOrderItems(orderId);
    }

}
