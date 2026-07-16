package org.example.cleancode.catalog.sealed.bad;

public class CreditCart implements PaymentMethod {
    @Override
    public void apply(double price) {
        // kredi kartına özel işlemler
    }

}


