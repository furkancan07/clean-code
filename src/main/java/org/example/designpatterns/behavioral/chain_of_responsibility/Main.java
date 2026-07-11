package org.example.designpatterns.behavioral.chain_of_responsibility;
/*
* Chain Of Responsibility Design Pattern için springte en iyi örnek filter chainler
* buradaki amaç verilen şarta göre en uygun davranışı yapmasıdır kısaca
* biz approver a verdiğimiz fiyat hangi yetkiye uygunsa gidip onu yapmış oldu
*
*
* Bir isteği işleyebilecek birden fazla nesneyi zincir gibi sıralayıp
* isteği zincir boyunca gönderiyorsun
*  her nesne "ben işlerim" ya da "sıradakine devrederim" diyor
* isteği gönderen kimin işleyeceğini bilmek zorunda kalmıyor
* */
public class Main {
    public static void main(String[] args) {
        Approver manager=new Manager();
        Approver director=new Director();
        Approver generalManager=new GeneralManager();

        manager.setNext(director);
        director.setNext(generalManager);

        manager.approve(500);
        manager.approve(5000);
        manager.approve(50000);
    }
}
