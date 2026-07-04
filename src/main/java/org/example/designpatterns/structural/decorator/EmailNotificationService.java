package org.example.designpatterns.structural.decorator;

public class EmailNotificationService implements NotificationService {
    @Override
    public void send(String userId,String message) {
        System.out.println("email gönderildi " + userId + ": " + message);
    }
}
