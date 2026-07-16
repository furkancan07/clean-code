package org.example.cleancode.catalog.sealed.bad;

public class Main {
    public static void main(String[] args) {

    }
    // KRİPTO GELİNCE EKSİK KALIR COMPİLE UYARMAZ BU İNTERFACİ KİM İMPLEMENT EDER HİÇBİR SINIR YOK
    // DİYELİMKİ ARADANA ZAMAN GEÇTİ BİRİ ÇIKIP YENİ BİR PAYMENT TÜRÜ YAZABİLİR COMOPİLE SIKINTI ÇIKARMAZ
    // BUNUN SINIRLARINI BELİRLEMEK İÇİN SEALED KULLANILIR
    static String describe(PaymentMethod p) {
        if (p instanceof CreditCart) return "Kredi kartı";
        if (p instanceof BankTransfer) return "Havale";
        return "Bilinmiyor";
    }
}
