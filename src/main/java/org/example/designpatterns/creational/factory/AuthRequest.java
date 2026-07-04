package org.example.designpatterns.creational.factory;

public class AuthRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthRequest() {

    }
}
