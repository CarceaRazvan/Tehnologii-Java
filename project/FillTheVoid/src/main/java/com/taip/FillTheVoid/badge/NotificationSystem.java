package com.taip.FillTheVoid.badge;

public class NotificationSystem {
    private String message;

    public NotificationSystem() {
    }

    public NotificationSystem(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void displayNotification() {
        if (message != null && !message.isEmpty()) {
            System.out.println("Notification: " + message);
        } else {
            System.out.println("No new notifications.");
        }
    }

    @Override
    public String toString() {
        return "NotificationSystem [message=" + message + "]";
    }
}