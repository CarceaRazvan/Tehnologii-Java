package com.example.lab3.compulsory.edit;

import com.example.lab3.compulsory.model.User;
import com.example.lab3.compulsory.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@ViewScoped
public class UserEdit implements Serializable {

    private UserService userService = new UserService();
    private User dialogUser;
    private List<String> genders = Arrays.asList("Male", "Female");
    private List<String> availableRoles = Arrays.asList("Admin", "User", "Guest");

    @PostConstruct
    public void init() {
        dialogUser = new User(); // Inițializează dialogUser
    }

    public User getDialogUser() {
        return dialogUser;
    }

    public void setDialogUser(User dialogUser) {
        this.dialogUser = dialogUser;
    }

    public void openDialog(Long id) {
        dialogUser = userService.findById(id);
        if (dialogUser == null) {
            System.out.println("User not found for ID: " + id);
        } else {
            System.out.println("Editing user: " + dialogUser.getName());
        }
    }

    public void openDialogAddUser() {
        dialogUser = new User();
        dialogUser.setId(-1L);
    }


    public void saveUser() {
        userService.insertUser(dialogUser);
        System.out.println("User saved: " + dialogUser.getName());
    }

    public void updateUser() {
        userService.updateUser(dialogUser);
        System.out.println("User saved: " + dialogUser.getName());
    }

    public List<String> getGenders() {
        return genders;
    }

    public List<String> getAvailableRoles() {
        return availableRoles;
    }
}
