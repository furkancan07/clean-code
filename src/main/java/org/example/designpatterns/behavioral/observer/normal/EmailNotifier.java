package org.example.designpatterns.behavioral.observer.normal;

public class EmailNotifier implements Observer{
    @Override
    public void update(String observer) {
        System.out.println("Email gönderildi: " + observer);
    }
}
