package org.example.designpatterns.behavioral.strategy;
/*
* if-else zinciri yerine, davranışı interface arkasına koy ve runtime'da değiştirilebilir yap.
* Ne zaman kullan:
* Aynı işin birden fazla yapılış şekli var
* Hangi yöntemin kullanılacağı runtime'da belli olacak
* Yeni bir yöntem eklendiğinde mevcut koda dokunmak istemiyorsun
*
* ve olabildiğince static final değişkenler yerine enum kullan
* */

public enum DiscountType implements DiscountStrategy {
    NO_DISCOUNT{
        public double apply(double price) {
            return price;
        }
    },
    STUDENT_DISCOUNT{
        public double apply(double price) {
            price *= 0.5;
            return price;
        }
    },
    VIP_DISCOUNT{
        public double apply(double price) {
            price *= 2;
            return price;
        }
    };


}
