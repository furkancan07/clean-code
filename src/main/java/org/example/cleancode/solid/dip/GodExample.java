package org.example.cleancode.solid.dip;
/**
* Dependency Inversion Principle :  yüksek seviye modüller, düşük seviye modüllere değil, abstractionlara bağımlı olmalı.
 * *  örneğşn spring boota biz jparepositoryleri direk service kullanmauız bir ara katman olarak örneğin user için userrepositoy interfaci
 * yazıp kullanırız aslında her gün ezbere yaptığımız bu prensib buraya dayanıyor BABUŞ
* */
public class GodExample {
    interface Database {
        void save(String data);
    }

    static class MySqlDatabase implements Database {
        public void save(String data) {
            System.out.println("MySQL'e kaydedildi: " + data);
        }
    }

    static class PostgresDatabase implements Database {
        public void save(String data) {
            System.out.println("Postgres'e kaydedildi: " + data);
        }
    }

    static class OrderService {
        private final Database database;

        public OrderService(Database database) {
            this.database = database;
        }

        public void placeOrder(String order) {
            database.save(order);
        }
    }

    public static void main(String[] args) {
        OrderService mysqlService = new OrderService(new MySqlDatabase());
        mysqlService.placeOrder("ORD-1");

        OrderService postgresService = new OrderService(new PostgresDatabase());
        postgresService.placeOrder("ORD-2");
    }
}
