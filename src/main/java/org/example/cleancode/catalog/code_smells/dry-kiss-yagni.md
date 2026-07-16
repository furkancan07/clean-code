# DRY / KISS / YAGNI

Bu üç prensip pattern değil, birer *felsefe*. Aralarında zaman zaman gerilim vardır: DRY seni soyutlamaya iter, KISS ve YAGNI seni geride tutar. İyi mühendislik, bu üçü arasında doğru dengeyi kurabilmektir.

---

## DRY — Don't Repeat Yourself

**Tanım:** Bir bilgi/kural sistemde yalnızca **bir yerde** tanımlı olmalı.

Önemli nokta: DRY, "kod tekrarı" değil, "**bilgi tekrarı**" demektir. İki kod parçası birbirine benziyor diye otomatik olarak birleştirilmemeli. Eğer bu iki parça **farklı sebeplerle** değişiyorsa (yani farklı iş kurallarını temsil ediyorlarsa), onları birleştirmek DRY değil, **yanlış soyutlamadır** — ve bu genelde over-engineering'e yol açar.

### Kötü uygulama — bilgi tekrarı
```java
// Aynı vergi hesaplama mantığı iki yerde kopyalanmış
class InvoiceService {
    double calculateTotal(double price) {
        return price + (price * 0.20); // KDV oranı burada
    }
}

class ReportService {
    double calculateTax(double price) {
        return price * 0.20; // Aynı oran burada da tekrar ediyor
    }
}
```
Vergi oranı değiştiğinde (örn. %20 → %18), iki yeri de bulup güncellemen gerekir. Birini unutursan sessiz bug.

### İyi uygulama — tek kaynak
```java
class TaxPolicy {
    private static final double VAT_RATE = 0.20;

    double applyVat(double price) {
        return price * VAT_RATE;
    }
}
```
Artık oran tek yerde. `InvoiceService` ve `ReportService` bu sınıfı kullanır.

### DRY'ın yanlış uygulanması — dikkat edilmesi gereken tuzak
```java
// bad — "benziyor" diye 2 farklı iş kuralı yanlışlıkla birleştirildi
double calculateDiscount(Customer c, Order o) {
    if (c.isVip() || o.getTotal() > 1000) return 0.1;
    return 0;
}
```
Burada VIP indirimi ile toplu sipariş indirimi **farklı iş kurallarıdır**, sadece yüzdeleri aynı olduğu için birleştirilmiş. Yarın VIP indirimi %15 olursa, toplu sipariş indirimi de yanlışlıkla değişir.

```java
// good — farklı kurallar ayrı kalmalı
double vipDiscount(Customer c) {
    return c.isVip() ? 0.1 : 0;
}

double bulkOrderDiscount(Order o) {
    return o.getTotal() > 1000 ? 0.1 : 0;
}
```

**Kural:** Kod benzerliğine değil, **değişim sebebine** bak. Aynı sebeple değişiyorsa DRY uygula; farklı sebeplerle değişiyorsa ayrı tut.

---

## KISS — Keep It Simple, Stupid

**Tanım:** Bir problemi çözen en basit yaklaşım, genelde en iyisidir. Gereksiz karmaşıklık ekleme.

### Kötü uygulama — gereksiz karmaşıklık
```java
// bad — 3 sabit durum için Strategy pattern kurulmuş
interface ShippingStrategy {
    double calculate(Order o);
}

class StandardShipping implements ShippingStrategy {
    public double calculate(Order o) { return 5.0; }
}

class ExpressShipping implements ShippingStrategy {
    public double calculate(Order o) { return 15.0; }
}

class ShippingCalculator {
    private final ShippingStrategy strategy;
    ShippingCalculator(ShippingStrategy strategy) { this.strategy = strategy; }
    double calculate(Order o) { return strategy.calculate(o); }
}
```

### İyi uygulama — basit ve yeterli
```java
// good — 3 sabit değer için switch fazlasıyla yeterli
double calculateShipping(ShippingType type) {
    return switch (type) {
        case STANDARD -> 5.0;
        case EXPRESS -> 15.0;
        case OVERNIGHT -> 25.0;
    };
}
```

Strategy pattern kötü değil — ama burada gereksiz. Kural ileride gerçekten dinamikleşirse (örn. runtime'da eklenen taşıyıcı firmalar), o zaman Strategy'ye geçmek mantıklı olur.

---

## YAGNI — You Aren't Gonna Need It

**Tanım:** "İleride lazım olur" diye şimdiden esnek/generic yapı kurma. İhtiyaç gerçekten doğduğunda ekle.

### Kötü uygulama — olmayan ihtiyaca hazırlık
```java
// bad — şu an tek bir bildirim kanalı (email) varken
// "ileride SMS, push vs. eklenir" diye generic sistem kurulmuş
interface NotificationChannel {
    void send(String message);
}

interface NotificationChannelFactory {
    NotificationChannel create(ChannelType type);
}

class NotificationDispatcher {
    private final Map<ChannelType, NotificationChannel> channels;
    // ... 40 satır daha, tek kullanılan kanal email
}
```

### İyi uygulama — bugünkü ihtiyaca göre
```java
// good — sadece email lazımsa, sadece onu yaz
class EmailNotifier {
    void send(String to, String message) {
        // email gönderme mantığı
    }
}
```
SMS gerçekten gerektiğinde, o zaman `NotificationChannel` soyutlamasına geçilir. O gün gelmeden bu soyutlamayı kurmak, hem gereksiz kod hem de yanlış tahmin edilmiş bir arayüz riski taşır (ihtiyaç geldiğinde arayüzün yanlış çıktığı çok sık görülür).

---

## Üçü Bir Arada — Gerilim Noktası

| Prensip | İter | Riski |
|---|---|---|
| DRY | Soyutlamaya, tekilleştirmeye | Yanlış soyutlama (farklı kuralları birleştirmek) |
| KISS | Basit çözüme | Gerekli karmaşıklıktan kaçınmak (örn. gerçekten dinamik olması gereken yeri switch ile çözmek) |
| YAGNI | Bugünün ihtiyacına | Refactor maliyetini görmezden gelmek |

**Pratik sıralama:** Önce KISS + YAGNI ile en basit haliyle yaz. Aynı bilgi/kural 2-3 kez tekrar etmeye başladığında (genelde "rule of three" denir) DRY'ı uygula — ama birleştirdiğin şeyin gerçekten aynı sebeple değiştiğinden emin ol.

