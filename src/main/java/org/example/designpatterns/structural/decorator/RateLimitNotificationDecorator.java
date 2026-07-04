package org.example.designpatterns.structural.decorator;

import java.util.HashMap;
import java.util.Map;

public class RateLimitNotificationDecorator implements NotificationService {
    private final NotificationService wrapped;
    // bunu rate limit farz edelim
    public static Map<String,Integer> rateLimitMap=new HashMap<>();

    public RateLimitNotificationDecorator(NotificationService wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void send(String userId, String message) {
        int count=rateLimitMap.getOrDefault(userId,0);
        if(count>3){
            System.out.println("Rate limit aşıldı - > : " + userId);
        }
        rateLimitMap.put(userId,count+1);
        wrapped.send(userId, message);
    }
}
