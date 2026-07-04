package org.example.designpatterns.structural.proxy;
// aymı şekilde config ekleyeceksin işte bu proxy eklenince userıd erişim kontrolü başka bir yerde fiyat kontrolü olur böyle uzar gider
// kodlama mantığı dacorator aynı gerçek hayyata örneği isetransactional aop diyebiliriz
public class PaymentServiceProxy implements PaymentService{
    private final PaymentService real;

    public PaymentServiceProxy(PaymentService wrapped) {
        this.real = wrapped;
    }

    @Override
    public void pay(String userId, long amount) {
        // erişim kontrolü
        if(userId == null) throw new IllegalArgumentException("userId should not be null");

        long startTime = System.currentTimeMillis();
        real.pay(userId, amount);
        long endTime = System.currentTimeMillis();

        System.out.println("PaymentServiceProxy: Payment processed in " + (endTime - startTime) + " ms");

    }
}
