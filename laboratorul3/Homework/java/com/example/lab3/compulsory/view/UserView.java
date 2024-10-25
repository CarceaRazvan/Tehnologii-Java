package com.example.lab3.compulsory.view;


import com.example.lab3.compulsory.model.User;
import com.example.lab3.compulsory.service.UserService;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.view.ViewScoped;

import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "userView")
@ViewScoped
public class UserView implements Serializable  {

    private UserService userService = new UserService();
    private List<User> users;

    public List<User> getUsers() {
        if (users == null) {
            users = userService.findAllUsers();
        }
        return users;
    }


    public String editUsers() {
        return "editUsers"; // outcome to navigate to users.xhtml
    }

}
