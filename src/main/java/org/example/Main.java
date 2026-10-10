package org.example;

import org.example.designpatterns.behavioral.strategy.DiscountType;
import org.example.designpatterns.behavioral.strategy.OrderService;
import org.example.designpatterns.creational.builder.Product;
import org.example.designpatterns.creational.factory.AuthRequest;
import org.example.designpatterns.creational.factory.AuthResult;
import org.example.designpatterns.creational.factory.AuthService;
import org.example.designpatterns.structural.decorator.AlertService;
import org.example.spring.aop.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.WebApplicationType;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private final OrderService orderService;
    private final AuthService authService;
    private final AlertService alertService;
    private final ProductService productService;


    public Main(OrderService orderService,AuthService authService,AlertService alertService,ProductService productService) {
        this.orderService = orderService;
        this.authService = authService;
        this.alertService = alertService;
        this.productService=productService;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class)
               // .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(String... args) {

        org.example.spring.aop.model.Product product=new org.example.spring.aop.model.Product(1L,"Laptop",1000);
        org.example.spring.aop.model.Product product2=new org.example.spring.aop.model.Product(2L,"Mouse",50);
        org.example.spring.aop.model.Product product3=new org.example.spring.aop.model.Product(3L,"Keyboard",80);
        productService.save(product);
        productService.save(product2);
        productService.save(product3);
        productService.findAll();
        productService.findAll();
        productService.findById(1L);
        productService.findById(1L);
        productService.delete(product);


        productService.findAll();

        // observarın kodlarını incele yeter spring için açıklayıcı diğeri için ise dosyalarda maini var
        // strategy
     double price =  orderService.calculatePrice(100.0, DiscountType.NO_DISCOUNT);
      double price2 =  orderService.calculatePrice(100.0, DiscountType.STUDENT_DISCOUNT);
      double price3= orderService.calculatePrice(100.0, DiscountType.VIP_DISCOUNT);

      // factory
        AuthResult authResult = authService.authenticate("oauth2Provider", new AuthRequest("email@email.com", "password"));
        AuthResult authResult2 = authService.authenticate("usernamePasswordProvider", new AuthRequest("email@email.com", "password"));


        // builder
        Product ürün= new Product.Builder()
                .name("Pc")
                .category("Technology")
                .description("Güzel pc")
                .build();



        // dacorator
        alertService.alert("5","dacorator design pattern çalıştı");



    }
}