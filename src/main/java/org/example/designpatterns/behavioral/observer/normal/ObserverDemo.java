package org.example.designpatterns.behavioral.observer.normal;

public class ObserverDemo {
    public static void main(String[] args) {
        OrderPublisher orderPublisher = new OrderPublisher();
        orderPublisher.addObserver(new EmailNotifier());
        orderPublisher.addObserver(new SmsNotifier());

        orderPublisher.createOrder("Order 1");
    }
}
