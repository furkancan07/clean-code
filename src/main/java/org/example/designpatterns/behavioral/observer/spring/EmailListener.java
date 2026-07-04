package org.example.designpatterns.behavioral.observer.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    // eğer asnekron çalışsın istiyorsan @Async anayasyonu kullan
    @EventListener
    public String onUserRegisteredEvent(UserRegisteredEvent event) {
        return "Sending email to " + event.getName() + " at " + event.getEmail();
    }
}
