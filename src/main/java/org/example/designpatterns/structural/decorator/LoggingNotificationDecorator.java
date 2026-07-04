package org.example.designpatterns.structural.decorator;

public class LoggingNotificationDecorator implements NotificationService {
    private final NotificationService wrapped;

    public LoggingNotificationDecorator(NotificationService notificationService) {
        wrapped = notificationService;
    }

    @Override
    public void send(String userId, String message) {
        System.out.println("LOG : bildirim gönderiliyor " + userId);
        wrapped.send(userId, message);
        System.out.println("LOG : bildirim gönderildi " + userId);
    }
}
