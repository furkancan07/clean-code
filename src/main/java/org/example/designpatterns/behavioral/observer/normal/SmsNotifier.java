package org.example.designpatterns.behavioral.observer.normal;

public class SmsNotifier implements Observer{
    @Override
    public void update(String observer) {
        System.out.println("SMS gönderildi: " + observer);
    }
}
