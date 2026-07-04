package org.example.designpatterns.creational.factory;

import org.springframework.stereotype.Component;

@Component("oauth2Provider")
public class Oauth2AuthProvider implements AuthProvider {
    @Override
    public AuthResult login(AuthRequest request) {
        // oath2 işlemler bla bla
        return new AuthResult(request.getEmail());
    }
}
