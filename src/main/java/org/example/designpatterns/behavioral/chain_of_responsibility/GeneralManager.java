package org.example.designpatterns.behavioral.chain_of_responsibility;

public class GeneralManager extends Approver{
    @Override
    public void approve(double amount) {
        System.out.println("Genel Müdür onayladı: " + amount);
    }
}
