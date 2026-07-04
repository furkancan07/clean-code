package org.example.designpatterns.creational.factory;

public interface AuthProvider {
    AuthResult login(AuthRequest request);
}
