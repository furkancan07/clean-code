package org.example.designpatterns.behavioral.chain_of_responsibility;

public class Director extends Approver{
    @Override
    public void approve(double amount) {
        if(amount <= 1000){
            System.out.println("Director onaylado: " + amount);
        } else if(next!=null){
           next.approve(amount);
        }
    }
}
