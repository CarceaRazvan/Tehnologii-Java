package com.taip.FillTheVoid.notification;

public interface INotifier {

    void send(String message);
    String getEmail();
}
