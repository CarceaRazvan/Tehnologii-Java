package com.example.lab3.lab5compulsory.service;
import com.example.lab3.lab5compulsory.model.Product;
import com.example.lab3.lab5compulsory.repository.UserDAO;
import com.example.lab3.lab5compulsory.model.User;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.List;

@Dependent
public class UserService {

    @Inject
    private UserDAO userDAO;

    public void insertUser(User user) {
        userDAO.create(user);
    }

    public User findById(Long id) {
        return userDAO.findById(id);
    }

    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }

    public void updateProductsUser(Long userId, List<Product> productsUser) {

        userDAO.updateUserProducts(userId, productsUser);
    }


    public List<Product> getProductsByUser(Long id) {

        return userDAO.findProductsByUserId(id);
    }
}
