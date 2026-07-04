package org.example.cleancode.solid.ocp;
// daha iyi rnekler için desigm pattern kısmına bakabilirsin
public class BadExample {
    static class DiscountCalculator {
        public double calculate(String customerType, double total) {
            if (customerType.equals("REGULAR")) {
                return total;
            } else if (customerType.equals("PREMIUM")) {
                return total * 0.9;
            } else if (customerType.equals("VIP")) {
                return total * 0.8;
            }
            return total;
        }
    }

    public static void main(String[] args) {
        DiscountCalculator calculator = new DiscountCalculator();
        System.out.println(calculator.calculate("PREMIUM", 100));
        System.out.println(calculator.calculate("VIP", 100));
    }
}
