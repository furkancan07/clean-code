package org.example.cleancode.solid.ocp;
// daha iyi örnekler için design pattenn kısmına bak strategy design pattern
/** open closed prensipe bir sınıf değişime kapalı gelişime açık olmalı */
public class GodExample {
    interface DiscountPolicy {
        double apply(double total);
    }

    static class RegularDiscount implements DiscountPolicy {
        public double apply(double total) {
            return total;
        }
    }

    static class PremiumDiscount implements DiscountPolicy {
        public double apply(double total) {
            return total * 0.9;
        }
    }

    static class VipDiscount implements DiscountPolicy {
        public double apply(double total) {
            return total * 0.8;
        }
    }

    static class DiscountCalculator {
        public double calculate(DiscountPolicy policy, double total) {
            return policy.apply(total);
        }
    }

    public static void main(String[] args) {
        DiscountCalculator calculator = new DiscountCalculator();
        System.out.println(calculator.calculate(new PremiumDiscount(), 100));
        System.out.println(calculator.calculate(new VipDiscount(), 100));

        // yeni tip eklemek istersen -> yeni sınıf yaz eskiye dokunma
        System.out.println(calculator.calculate(new StudentDiscount(), 100));
    }

    static class StudentDiscount implements DiscountPolicy {
        public double apply(double total) {
            return total * 0.85;
        }
    }
}
