package com.example.lab6.view;

import com.example.lab6.model.User;
import com.example.lab6.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class UserView extends DataView<User, Long> {

    @EJB
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        // Set repository to UserRepository and initialize data
        this.repository = userRepository;
        super.init();  // Calls loadItems() to populate items list
    }

    // Additional methods specific to User can be added here if needed
}
