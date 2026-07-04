package org.example.designpatterns.creational.factory;

import org.springframework.stereotype.Component;

@Component("usernamePasswordProvider")
public class UsernamePasswordAuthProvider implements AuthProvider {
    @Override
    public AuthResult login(AuthRequest request) {

        // öncesinde login işlemleri bla bla
        AuthResult result = new AuthResult();
        result.setEmail(request.getEmail());
        result.setName("Dummy User");
        return result;
    }
}
