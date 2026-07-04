package org.example.cleancode.solid.dip;

public class BadExample {
    static class MySqlDatabase {
        public void save(String data) {
            System.out.println("MySQL'e kaydedildi: " + data);
        }
    }

    static class OrderService {
        private final MySqlDatabase database = new MySqlDatabase(); // somut sınıfa sıkı bağımlılık

        public void placeOrder(String order) {
            database.save(order);
        }
    }

    public static void main(String[] args) {
        OrderService service = new OrderService();
        service.placeOrder("ORD-1");
    }
}
