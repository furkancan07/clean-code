package org.example.designpatterns.structural.decorator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
* Asıl iş burada decorator pattern ile
* notification service'i extend edebilmek için configuration class'ı oluşturuyoruz.
* Bu sayede farklı notification service'leri birbirine bağlayabiliriz.
*
* decorator un amacaı bir sınıfın kodunu değiştirmeden üstüne sararak yeni özellik eleyebilme yeteneğidir
*
* email gönderince hem loglandı hem rate limit özelliği eklemiş oldk yeni özellik ekleyince kodu bozmadan tekrar ekleyebilceğiz
*
* proxydan en önemlş farkı proxyin yetki tabanlı olmasıdır
* */
@Configuration
public class NotificationConfig {

    @Bean
    public NotificationService notificationService() {
        NotificationService base=new EmailNotificationService();
        base=new LoggingNotificationDecorator(base);
        base=new RateLimitNotificationDecorator(base);
        return base;
    }
}
