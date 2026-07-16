package org.example.cleancode.catalog.minimizemutability;
/*
* effective java da item 17 deye denk geliyor bu konu baba konu basit diyor ki
* Mutable bir sınıf demek: nesne yaratıldıktan sonra state'i değişebilir demek
* Bu özellikle çok thread'li ortamlarda ve büyük kod tabanlarında ciddi risk taşır
* bir referansı kim tuttuğunu bilemezsin o referans üzerinden state'i kim ne zaman değiştirdi takip edemezsin.
*
* bir sınıfı imuttable yapmak için şu 5 ini yap der
* 1-) setter yok
* 2-) extend edilmesini engelle sınıfı final yap
* 3-) tüm fieldları final yap
* 4-) tüm fieldları private yap
* 5-) defensive copy yap bunu repoda bulabilirsin zaten
*
* tabi bunun dezavantajı da var Her değişiklik" yeni bir nesne yarattığı için
* çok sık değişen büyük nesnelerde (örn. büyük bir listeye binlerce eleman tek tek eklemek) performans maliyeti olabilir
* Bu yüzden Java'da StringBuilder (mutable) yanında String (immutable) ikisi bir arada var
*  Yani immutability her zaman en iyisi değil
* varsayılan tercih olmalı ama performans-kritik mutasyon senaryolarında bilinçli olarak mutable bir yapı seçilebilir.
 *
* */
public class GodExample {
    public  final class Money { // 2) final — extend edilemez
        private final double amount;   // 3) final field
        private final String currency; // 4) private field

        public Money(double amount, String currency) {
            this.amount = amount;
            this.currency = currency;
        }

        public double getAmount() { return amount; }
        public String getCurrency() { return currency; }

        // 1) Setter yok Değişiklik her zaman yeni bir nesne döner
        public Money withDiscount(double rate) {
            return new Money(this.amount * (1 - rate), this.currency);
        }

        public Money add(Money other) {
            if (!this.currency.equals(other.currency)) {
                throw new IllegalArgumentException("Farklı para birimleri toplanamaz");
            }
            return new Money(this.amount + other.amount, this.currency);
        }
    }
}
