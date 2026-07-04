package org.example.designpatterns.behavioral.observer.normal;

import java.util.ArrayList;
import java.util.List;

public class OrderPublisher implements Subject {
    private List<Observer> observers = new ArrayList<>();
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer observer : observers) {
            observer.update(event);
        }
    }

    public void createOrder(String orderId){
        System.out.println("Sipariş oluşturuldu: " + orderId);
        notifyObservers(orderId);
    }
}
