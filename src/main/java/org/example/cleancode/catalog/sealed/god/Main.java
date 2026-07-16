package org.example.cleancode.catalog.sealed.god;

public class Main {
    // java 21 ile gelen switch expression ile sealedin asıl gücü ortaya çıkar permits olanalradan bir eksik olursa compile time error verir
    static String describe(PaymentMethod p) {
        switch (p){
            case CreditCart c -> {
                return "Kredi kartı";
            }
            case BankTransfer b -> {
                return "Havale";}
            case Crypto cr -> {return "Kripto";}
        }
    }
}
