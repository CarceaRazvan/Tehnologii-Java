package com.example.lab3.compulsory.service;

import com.example.lab3.compulsory.dao.UserDAO;
import com.example.lab3.compulsory.model.Product;
import com.example.lab3.compulsory.model.User;
import org.primefaces.model.DualListModel;

import java.util.List;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO(); // Initialize DAO
    }

    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    public User findById(Long id) {
        return userDAO.findById(id);
    }

    public void insertUser(User user) {
        userDAO.insertUser(user);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(Long userId) {
        userDAO.delete(userId);
    }


    public List<Product> getProductsByUser(Long id) {

        return userDAO.findProductsByUserId(id);
    }

    public void updateProductsUser(Long userId, List<Product> productsUser) {

        userDAO.updateUserProducts(userId, productsUser);
    }
}
