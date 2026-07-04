package org.example.designpatterns.behavioral.observer.spring;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/*
* Observer design patterb anacı bir olay olduğunda onu etkikleyen diğer yerlerinde işlemlerini yapmasını sağlamaktır.
* Bu sayede bir olay olduğunda diğer yerlerdeki işlemler tetiklenir.
*
* */

@Service
public class UserService {
    private final ApplicationEventPublisher eventPublisher;

    public UserService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void register(String name,String email) {
        // kayıt yaptığını farz et

        eventPublisher.publishEvent(new UserRegisteredEvent(this,name,email));
    }
}
