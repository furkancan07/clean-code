package org.example.cleancode.catalog.sealed.god;

public final class Crypto implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " with crypto.");
    }
}
