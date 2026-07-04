package org.example.designpatterns.structural.decorator;

import org.springframework.stereotype.Service;

@Service
public class AlertService {


    private final NotificationService notificationService;

    public AlertService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void alert(String userId, String message) {
        notificationService.send(userId, message);
    }
}
