package org.example.cleancode.solid.lsp;

import java.util.*;
/**
 * Liskov Substitution Principle : alt sınıf üst sınıfın yerine sorunsuzca geçebilmeli
 * biz bunu şöyle anlayabiliriz interfacelerimiz isviçre çakısı gibi olmamalı olabildiğince ayır
 * bir nevi bir sonraki prensipte de bu mantıkla ilerlersek yardımcı olmuş olur
 * */
public class GodExample {

    static class Bird {
        public void eat() {
            System.out.println("Kuş besleniyor");
        }
    }

    interface Flyable {
        void fly();
    }

    static class Sparrow extends Bird implements Flyable {
        public void fly() {
            System.out.println("Serçe uçuyor");
        }
    }

    static class Penguin extends Bird {

        public void swim() {
            System.out.println("Penguen yüzüyor");
        }
    }

    public static void main(String[] args) {
        List<Bird> birds = new ArrayList<>();
        birds.add(new Sparrow());
        birds.add(new Penguin());

        for (Bird bird : birds) {
            bird.eat(); // her Bird için güvenle çalışır
        }

        List<Flyable> flyers = new ArrayList<>();
        flyers.add(new Sparrow());
        // flyers.add(new Penguin()); // zaten mümkün değil  Penguin Flyable değil

        for (Flyable flyer : flyers) {
            flyer.fly(); // sadece gerçekten uçabilenler burada
        }
    }
}
