package org.example.cleancode.nullsafety;

import org.example.cleancode.errorhandling.BusinessValidationException;
import org.example.cleancode.errorhandling.BusinessValidationRule;

import java.util.Optional;

/*
* null yönetimini çok katı yapmak istersen
* uberin geliştirmiş olduğu NullAway kütüphanesini kullanılabilir
* compile time de zorunlu kılıyor
* kotline yakın bir deneyim
* ama çoğu projede optional iş görür
*
* */
public class Main {
    public static void main(String[] args) {
        User user = new User();
       // String city = user.getAddress().getCity(); // hiç setlenmediği için null pointer exception verecek

        // 1.yol basit bir çözüm
        String city2= getCitySafe(user);
        // 2.yol Optional ile çözüm
        String city3 = getCitySafe2(user);

        // direk user katmanında işi çözmek istersek
        Address address = Optional.ofNullable(user.getAddress()).orElseThrow(()->new BusinessValidationException(BusinessValidationRule.NOT_FOUND));
        String city4= address.getCity();

    }

    private static String getCitySafe(User user) {
        if(user!=null && user.getAddress()!=null) {
            return user.getAddress().getCity();
        }
        return "Bilinmiyor";
    }

        private static String getCitySafe2(User user) {
        return Optional.ofNullable(user)
                .map(User::getAddress)
                .map(Address::getCity)
                .orElse("Bilinmiyor");
        }


}
