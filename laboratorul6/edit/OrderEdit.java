package com.example.lab6.edit;

import com.example.lab6.model.OrderItem;
import com.example.lab6.model.Product;
import com.example.lab6.model.User;
import com.example.lab6.service.OrderService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class OrderEdit implements Serializable {

    @Getter
    @Setter
    private List<OrderItem> orderItems;
    @Getter
    @Setter
    private List<Product> productList;
    @Getter
    @Setter
    private String selectedProduct;
    @Getter
    @Setter
    private List<User> userList;
    @Getter
    @Setter
    private String selectedUser;
    @Getter
    @Setter
    private int quantity;

    @EJB
    private OrderService orderService;

    @PostConstruct
    public void init() {
        orderItems = new ArrayList<>();
        userList = orderService.findAllUsers();
        productList = orderService.findAllProducts();
        quantity = 0;
    }

    public void addItemToOrder() {

        if (selectedProduct != null && quantity > 0) {
            try {
                orderService.addItem(selectedProduct, quantity);
                orderItems = orderService.getOrderItems();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

    }

    public void deleteItem(OrderItem item) {
        orderItems.remove(item);
    }

    public void clearOrder() {
        orderService.clearOrder();
    }

    public void saveOrder() {
        if (selectedUser != null) {
            try {
                orderService.registerOrder(selectedUser);
                orderService.save();
                FacesContext.getCurrentInstance().getExternalContext().redirect("OrderEdit.xhtml");
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Order registered successfully");
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);

            } catch (Exception e) {
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "There was an error processing your order: stock for a product insufficient");
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            }
        }

    }
}

