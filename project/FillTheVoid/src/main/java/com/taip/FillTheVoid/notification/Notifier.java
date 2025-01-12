package com.taip.FillTheVoid.notification;

import com.taip.FillTheVoid.badge.Badge;
import com.taip.FillTheVoid.user.User;
import com.taip.FillTheVoid.user.UserRepository;
import com.taip.FillTheVoid.user.UserService;
import lombok.AllArgsConstructor;

public class Notifier implements INotifier{

    private final String email;
    private final UserService userService;
    private UserRepository userRepository;

    public Notifier(String email) {
        this.email = email;
        this.userService = new UserService(userRepository);
    }

    @Override
    public void send(String message) {
        User user = userService.getUserByEmail(email);

        System.out.printf("Sending " + message + " by Mail to " + email);
    }

    @Override
    public String getEmail() {
        return email;
    }
}

