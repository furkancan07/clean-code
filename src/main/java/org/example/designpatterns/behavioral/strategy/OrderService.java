package org.example.designpatterns.behavioral.strategy;

import org.springframework.stereotype.Service;
/*
* bir farklı yol olarak componentleri spring contextine ekleyip  inject edebililerede
* eğer enumda method görmek istemiyorsak bu yol tercih edilir
*
*
* bizim yaptığımız örnek çok basit olduğu için enum seviyesinde yaptık örneğin şöyle bir senaryo olsaydı öğrenci ödemesinde
* başka sınıflara da ihtiyç duysaydı yeni bir componet açıp discountstrategy ile implement ederdik
*
* effective jave kitabında Joshua reis enumları sabit değişkenlere tercih etmemizi önermiştir type safety den dolayı
* */

@Service
public class OrderService {
    public double calculatePrice(double price, DiscountType discountType) {
        return discountType.apply(price);
    }
}
