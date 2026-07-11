package org.example.designpatterns.behavioral.chain_of_responsibility;

public abstract class Approver {
    protected Approver next;

    public void setNext(Approver next) {
        this.next = next;
    }

    public abstract void approve(double amount);
}
