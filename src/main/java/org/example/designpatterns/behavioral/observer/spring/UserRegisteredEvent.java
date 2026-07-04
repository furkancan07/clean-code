package org.example.designpatterns.behavioral.observer.spring;

import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent {
    private final String name;
    private final String email;
    public UserRegisteredEvent(Object source, String name,String email) {
        super(source);
        this.name = name;
        this.email = email;
    }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
