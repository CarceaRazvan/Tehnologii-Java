package com.taip.FillTheVoid.notification;

import com.taip.FillTheVoid.user.UserRepository;
import com.taip.FillTheVoid.user.UserService;

public abstract class BaseNotifierDecorator implements INotifier{

    private final INotifier wrapped;

    protected final UserService userService;
    private UserRepository userRepository;

    protected BaseNotifierDecorator(INotifier wrapped) {

        this.wrapped = wrapped;
        this.userService = new UserService(userRepository);
    }

    @Override
    public void send(String message) {
        wrapped.send(message);
    }

    @Override
    public String getEmail() {
        return wrapped.getEmail();
    }
}
