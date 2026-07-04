package org.example.cleancode.solid.srp;
/*
Single-responsibility principle : bir sınıfı sadece bir işi yapmalı ve onu iyi yapmalı
bu örneklerde örnepin bir sınıf içinde eksta sınıflar olabilir onu farklı yerlede gibi düşün :D
* */
public class GodExample {
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

    public static class OrderValidator {
        public void validate(Order order) {
            if (order.getTotal() <= 0) {
                throw new IllegalArgumentException("Geçersiz tutar");
            }
        }
    }

    public static class OrderRepository {
        public void save(Order order) {
            System.out.println("Sipariş veritabanına kaydedildi: " + order.getId());
        }
    }

    public static class OrderNotifier {
        public void notifyCustomer(Order order) {
            System.out.println("Mail gönderiliyor -> " + order.getCustomerEmail());
            System.out.println("Konu: Siparişiniz Alındı");
            System.out.println("İçerik: Siparişiniz oluşturuldu: " + order.getId());
        }
    }

    public static class OrderLogger {
        public void log(Order order) {
            System.out.println("LOG: Order placed -> " + order.getId());
        }
    }

    public static class OrderService {
        private final OrderValidator validator;
        private final OrderRepository repository;
        private final OrderNotifier notifier;
        private final OrderLogger logger;

        public OrderService(OrderValidator validator, OrderRepository repository,
                            OrderNotifier notifier, OrderLogger logger) {
            this.validator = validator;
            this.repository = repository;
            this.notifier = notifier;
            this.logger = logger;
        }

        public void placeOrder(Order order) {
            validator.validate(order);
            repository.save(order);
            notifier.notifyCustomer(order);
            logger.log(order);
        }
    }

    public static class Main {
        public static void main(String[] args) {
            Order order = new Order("ORD-1", "furkan@example.com", 250.0);

            OrderService service = new OrderService(
                    new OrderValidator(),
                    new OrderRepository(),
                    new OrderNotifier(),
                    new OrderLogger()
            );

            service.placeOrder(order);
        }
    }
}
