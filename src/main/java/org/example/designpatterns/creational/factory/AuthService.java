package org.example.designpatterns.creational.factory;

import org.springframework.stereotype.Service;

import java.util.*;

/*
* Factory ile strategy arasındaki en büyük fark
* factory nesne üretirken strategy davranışı yönetir
* buradada best practise type safetyden dolayı enum olsa da
* başka yolları da deneyelim
*
* */

@Service
public class AuthService {
    private final Map<String , AuthProvider> providers;

    public AuthService(Map<String, AuthProvider> providers) {
        this.providers = providers;
    }

    public AuthResult authenticate(String type, AuthRequest request) {
        System.out.println("AuthService.authenticate() çaluştu tipi :  " + type + " ve request: " + request);
        return providers.getOrDefault(type,providers.get("usernamePasswordProvider")).login(request);

    }
}
