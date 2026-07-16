package org.example.cleancode.catalog.staticfactorymethods;
/*
* effective java item1 de geçer bu konu
* der ki sen statici constructor için kullanabilirsin
* bu sana isimlendirmede ve her çağruda yeni nesne yaratma zorunda olmama avantajı sağlar
*
* */
public class OrderStatus {
    public static final OrderStatus PENDING = new OrderStatus("PENDING");
    public static final OrderStatus COMPLETED = new OrderStatus("COMPLETED");
    private final String code;
    // klasik constructor
    private OrderStatus(String code) {
        this.code = code;
    }
    // GÖRÜLDÜĞÜ GİBİ HER SEFERİNDE YENİ NESNE ÜRETMEYE GEREK KALMAZ HEM DE STATİC SAYESİNDE İSTEDİĞİMİZ ANLAMLI İSMİ VEREBİLİRİZ
    private static OrderStatus of(String code) {
        return switch (code) {
            case "PENDING" -> PENDING;
            case "COMPLETED" -> COMPLETED;
            default -> throw new IllegalArgumentException("Bilinmeyen durum: " + code);
        };
    }

}
