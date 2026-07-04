package org.example.cleancode.solid.srp;
// bu static classlar içe koydum ki bir sınıfta örneği anlayalım diye byunu ayrı düşün hacı
public class BadExample {

    public static class Order {
        private String id;
        private String customerEmail;
        private double total;

        public Order(String id, String customerEmail, double total) {
            this.id = id;
            this.customerEmail = customerEmail;
            this.total = total;
        }

        public String getId() { return id; }
        public String getCustomerEmail() { return customerEmail; }
        public double getTotal() { return total; }
    }

    public static class OrderService {

        public void placeOrder(Order order) {
            // 1) validasyon
            if (order.getTotal() <= 0) {
                throw new IllegalArgumentException("Geçersiz tutar");
            }

            // 2) kayıt
            System.out.println("Sipariş veritabanına kaydedildi: " + order.getId());

            // 3) mail gönderme
            System.out.println("Mail gönderiliyor -> " + order.getCustomerEmail());
            System.out.println("Konu: Siparişiniz Alındı");
            System.out.println("İçerik: Siparişiniz oluşturuldu: " + order.getId());

            // 4) loglama
            System.out.println("LOG: Order placed -> " + order.getId());
        }
    }

    public static class Main {
        public static void main(String[] args) {
            Order order = new Order("ORD-1", "furkan@example.com", 250.0);
            OrderService service = new OrderService();
            service.placeOrder(order);
        }
    }

}
