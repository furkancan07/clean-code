package org.example.cleancode.functions.fp;

/*
 * aga bu 2 şeyi sağlamalı
 * 1-deterministic olmalı yani aynı input verildiğinde aynı output vermeli
 * 2- side effect olmamalı yani dışarıya bir etkisi olmamalı
 *
 * */
public class PureFunctions {
    static int counter = 0;

    // PURE
    static int add(int a, int b) {
        return a + b;
    }

    // PURE
    static double applyDiscount(double price, double rate) {
        return price * (1 - rate);
    }

    // IMPURE her çağrıldığında counter değişiyor ve dışarıya etkisi oluyor
    static int incrementCounter() {
        return ++counter;
    }
    /*
    * Java Impure örnekleri
    * LocalDate.now()
     * System.currentTimeMillis()
     * new Random().nextInt()
     * UUID.randomUUID()
    * */
}
