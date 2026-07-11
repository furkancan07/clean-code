package org.example.cleancode.nullsafety;

public class User {
    private String name;

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    private Address address;
}
