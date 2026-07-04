package org.example.cleancode.solid.lsp;

import java.util.*;

public class BadExample {
    static class Bird {
        public void fly() {
            System.out.println("Kuş uçuyor");
        }
    }

    static class Sparrow extends Bird {
        // serçe uçabilir sorun yok
    }

    static class Penguin extends Bird {
        @Override
        public void fly() {
            throw new UnsupportedOperationException("Penguen uçamaz!");
        }
    }

    public static void main(String[] args) {
        List<Bird> birds = new ArrayList<>();
        birds.add(new Sparrow());
        birds.add(new Penguin());

        for (Bird bird : birds) {
            bird.fly(); // burada patlar 65 pengune uçamaz gelecek
        }
    }
}
