package org.example.designpatterns.behavioral.chain_of_responsibility;

public class Manager extends Approver{
    @Override
    public void approve(double amount) {
        if(amount <= 100){
            System.out.println("Müdür onayladı: "+amount);
        } else if (next!=null) {
            next.approve(amount);
        }
    }
}
