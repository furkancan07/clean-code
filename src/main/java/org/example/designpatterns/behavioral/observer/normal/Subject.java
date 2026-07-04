package org.example.designpatterns.behavioral.observer.normal;

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String event);
}
