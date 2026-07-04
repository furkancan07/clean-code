package org.example.designpatterns.structural.proxy;

public class PaymentServiceImpl implements PaymentService {
    @Override
    public void pay(String userId, long amount) {
        System.out.println("Payment of " + amount + " made by user " + userId);
    }
}
