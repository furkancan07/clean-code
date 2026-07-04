package org.example.designpatterns.creational.builder;
/*
* builderin amacı çok kısaca anlatıyım
* baba normalde biz constructolarımızı senaryomuza göre yazıyoruz örneğin
* 2 değişkeniniz olsun ve bazen 2 sini bazeb ise sadece 1 ini alacak olalım
* toplamda 3 constuctor ile bu işi hallederiz değişken sayımızı arttıralım örneğin
* 10 tane ve bizim bazen bir tane bazen birden fazla alacağımız varsayalım bu durumda bir sürü constrcutor yazmamız
* gerek hepsiniz yazmamız imkansım 2^10 -1 tane constructor ediyor tüm kombinasyonlar
*
*
* zaten spring  boota lombok sayesinde builder ile hemen kullanabiliyoruz
*
*
* kısaca hemen mantığuınu yazmak adına 3 değişken olan bir sınıfla yazacam
* */
public class Product {
    public String name;
    public String description;
    public String category;

    public Product(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.category = builder.category;
    }

    public static class Builder {
        public String name;
        public String description;
        public String category;

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder category(String category) {
            this.category = category;
            return this;
        }
        public Product build() {
            return new Product(this);
        }
    }
}
