# JPA Locking Notları: Pessimistic ve Optimistic

## 1. Temel Fikir

| | Optimistic | Pessimistic |
|---|---|---|
| Mantık | "Çakışma nadir, kilit koymayayım. Kaydederken kimse araya girmiş mi bakarım." | "Çakışma olacak, baştan DB seviyesinde kilitleyeyim." |
| Mekanizma | `@Version` alanı, `UPDATE ... WHERE id=? AND version=?` | `SELECT ... FOR UPDATE / FOR SHARE` |
| Çakışma anı | Kaydet (flush/commit) anında patlar | Okuma anında bekletilir |
| Kaybeden | Exception alır, retry sende | Bekler, güncel veriyi görür |
| Maliyet | Çakışma sıksa boşa iş + retry | Kilit süresi kadar bekleme, deadlock riski |

---

## 2. Pessimistic Locking

Kilit DB'de tutulur, transaction commit/rollback olunca kalkar. **Mutlaka `@Transactional` içinde** kullanılmalı.

### Türler

**PESSIMISTIC_READ (shared lock)**
- SQL: `SELECT ... FOR SHARE` (MySQL: `LOCK IN SHARE MODE`)
- Başkaları okuyabilir ve READ kilidi alabilir, ama kimse güncelleyemez/silemez.
- Ne zaman: okuduğum veri ben işlem yaparken değişmesin, ama başkası da okuyabilsin.

**PESSIMISTIC_WRITE (exclusive lock)**
- SQL: `SELECT ... FOR UPDATE`
- Kilidi tek sen alırsın. Başkası ne READ ne WRITE kilidi alabilir, güncelleyemez, silemez.
- Ne zaman: "okuyacağım, sonra değiştireceğim" (stok, bakiye, sayaç). En çok kullanılan.

**PESSIMISTIC_FORCE_INCREMENT**
- WRITE gibi kilitler, ayrıca `@Version` alanını artırır (veri değişmese bile).
- Nadir kullanılır, pessimistic ve optimistic aynı entity'de karışınca işe yarar.

```java
// PESSIMISTIC_READ: başkası okuyabilir ama güncelleyemez | PESSIMISTIC_WRITE: tam kilit, kimse kilitleyemez/güncelleyemez | FORCE_INCREMENT: WRITE gibi kilitler + @Version'ı artırır.
```

### Kullanım

```java
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000"))
    @Query("select p from Product p where p.id = :id")
    Optional<Product> findByIdForUpdate(@Param("id") Long id);
}
```

```java
@Transactional
public void decreaseStock(Long id, int qty) {
    Product p = repository.findByIdForUpdate(id).orElseThrow();
    p.setStock(p.getStock() - qty);
    // commit'te kilit kalkar
}
```

Ya da: `em.find(Product.class, id, LockModeType.PESSIMISTIC_WRITE);`

### Dikkat edilecekler

- **Timeout koy.** Yoksa kilit bırakılmazsa çok uzun beklersin. Süre dolunca `PessimisticLockException` / `LockTimeoutException` fırlar.
- **Transaction'ı kısa tut.** Kilit varken HTTP çağrısı, uzun hesap yapma.
- **Deadlock riski.** Birden fazla kayıt kilitleyeceksen hep aynı sırada (örn. id'ye göre) kilitle.
- **DB desteği değişir.** H2 gibi bazılarında READ, WRITE'a düşebilir. Prod DB'nde test et.
- **Satır bazlı kilit** gelir ama WHERE koşulu indeks kullanmıyorsa DB daha fazla satırı kilitleyebilir.

---

## 3. Optimistic Locking

### @Version nasıl çalışır

```java
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int stock;

    @Version
    private Long version;   // elle set etme, Hibernate yönetir
}
```

Güncellemede Hibernate şunu atar:

```sql
UPDATE product SET stock = ?, version = 6 WHERE id = ? AND version = 5
```

- Etkilenen satır **1** → başarılı, version 5'ten 6'ya çıkar.
- Etkilenen satır **0** → biri senden önce güncellemiş, `OptimisticLockException` (Spring'de `ObjectOptimisticLockingFailureException`) fırlar.

Version tipi: `Long` / `Integer` / `Short`. `Timestamp` / `Instant` hassasiyet sorunu yüzünden önerilmez.

Version sadece entity **gerçekten değiştiyse** (dirty) artar.

### Lock mode'lar

**NONE (varsayılan)**
- Ekstra kilit yok, `@Version` sadece entity güncellenirken kontrol edilir.

```java
/* NONE (varsayılan): ekstra kilit yok, @Version sadece entity güncellenirken kontrol edilir, çakışma varsa OptimisticLockException fırlatır. */
```

**OPTIMISTIC (eski adı READ)**
- Kaydı sadece okusan bile commit anında "version hâlâ aynı mı" diye kontrol eder.
- Ne zaman: kaydı okuyup ona göre başka bir şey yazıyorsun ama o kaydı değiştirmiyorsun.

```java
/* OPTIMISTIC: kaydı sadece okusan bile commit anında @Version değişmiş mi kontrol eder, değiştiyse OptimisticLockException fırlatır. */
```

```java
@Transactional
public void calculate(Long categoryId) {
    Category c = em.find(Category.class, categoryId, LockModeType.OPTIMISTIC);
    // c'ye göre iş yap, commit'te c'nin version'ı kontrol edilir
}
```

**OPTIMISTIC_FORCE_INCREMENT (eski adı WRITE)**
- Entity'yi değiştirmesen bile commit'te version'ı artırır.
- Ne zaman: child değişince parent'ı da "değişti" saymak istediğinde (Order ve OrderItem).

```java
/* OPTIMISTIC_FORCE_INCREMENT: entity'yi değiştirmesen bile commit'te @Version'ı artırır, böylece child değişiklikleri parent üzerinde çakışma olarak yakalanır. */
```

```java
Order o = em.find(Order.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
o.getItems().add(newItem);
```

Repository'de: `@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)`

### Exception'ı yönetmek

**1. Retry:** Retry, `@Transactional` metodunun **dışında** olmalı.

```java
for (int i = 0; i < 3; i++) {
    try {
        service.decreaseStock(id, qty);   // @Transactional olan bu
        return;
    } catch (ObjectOptimisticLockingFailureException e) {
        // son denemede hata fırlat
    }
}
```

Retry'da iş kuralı (örn. stok kontrolü) tekrar çalışmalı.

**2. Kullanıcıya hata dön (HTTP 409):**

```java
@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
public ResponseEntity<String> handleConflict() {
    return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("Kayıt başkası tarafından değiştirildi, yenileyip tekrar dene.");
}
```

### Dikkat edilecekler

- **REST'te version'ı DTO ile taşı.** Kullanıcı kaydı açarken version'ı alsın, kaydederken geri göndersin. Göndermezse çakışma yakalanmaz. (Alternatif: `ETag` / `If-Match`.)
- **Toplu JPQL update** (`@Modifying @Query`) version'ı otomatik artırmaz. Gerekirse `set p.version = p.version + 1` yaz.
- **Version alanını elle değiştirme.** Lombok `@Data` setter üretir, dokunma.
- **Çakışma sık oluyorsa** (sürekli retry) optimistic yanlış seçim, `PESSIMISTIC_WRITE`'a geç.

---

## 4. Senaryo: İki Kişi Aynı Ürünü Düzenliyor (Debug Gibi)

Başlangıç: `Product` id=1, stock=10, version=5.

| Adım | Olay | DB (stock/version) | Ahmet RAM | Ayşe RAM |
|---|---|---|---|---|
| t0 | Başlangıç | 10 / 5 | - | - |
| t1 | Ahmet okur | 10 / 5 | 10 / 5 | - |
| t2 | Ayşe okur | 10 / 5 | 10 / 5 | 10 / 5 |
| t3 | Ahmet `setStock(8)` (SQL yok, nesne dirty) | 10 / 5 | 8 / 5 | 10 / 5 |
| t4 | Ahmet commit: `UPDATE ... SET stock=8, version=6 WHERE id=1 AND version=5` → 1 satır | **8 / 6** | 8 / 6 | 10 / 5 (bayat) |
| t5 | Ayşe `setStock(7)` | 8 / 6 | 8 / 6 | 7 / 5 |
| t6 | Ayşe commit: `... WHERE id=1 AND version=5` → **0 satır**, exception, rollback | 8 / 6 | 8 / 6 | atıldı |
| t7 | Ayşe retry: okur (8/6), değiştirir, `UPDATE ... SET stock=7, version=7 WHERE version=6` → 1 satır | **7 / 7** | | |

Canlı görmek için:

```properties
spring.jpa.show-sql=true
logging.level.org.hibernate.orm.jdbc.bind=TRACE
```

Test (test metodunu `@Transactional` yapma, yoksa `a` ve `b` aynı nesne olur):

```java
@Test
void optimisticLockConflict() {
    Product a = repository.findById(1L).orElseThrow();   // version=5
    Product b = repository.findById(1L).orElseThrow();   // version=5 (ayrı instance)

    a.setStock(8);
    repository.saveAndFlush(a);                           // version=6

    b.setStock(7);
    assertThrows(ObjectOptimisticLockingFailureException.class,
            () -> repository.saveAndFlush(b));            // version=5 eski, patlar
}
```

---

## 5. Gerçek E-Ticaret Örneği: Stok 2, Ahmet 2 Adet, Ayşe 1 Adet

Toplam talep 3, stok 2. Biri reddedilmek zorunda. Soru: bu kontrollü mü olacak, stok eksiye mi düşecek?

### Kilit yoksa (lost update)

| Adım | DB stock | Not |
|---|---|---|
| Başlangıç | 2 | |
| Ahmet okur: 2, Ayşe okur: 2 | 2 | İkisi de "yeter" der |
| Ahmet kaydeder: `2-2` | 0 | |
| Ayşe kaydeder: `2-1` | **1** | Ahmet'in düşüşü ezildi, 3 satıldı ama stok 1 |

### Optimistic ile

| Zaman | Olay | DB (stock/version) |
|---|---|---|
| t1 | Ahmet okur (2/5), Ayşe okur (2/5) | 2 / 5 |
| t2 | Ahmet onaylar: `SET stock=0, version=6 WHERE version=5` → 1 satır | 0 / 6 |
| t3 | Ayşe onaylar: `WHERE version=5` → 0 satır | 0 / 6 |
| t4 | `ObjectOptimisticLockingFailureException`, rollback | 0 / 6 |
| t5 | Ayşe retry: stok=0 okur, iş kuralı reddeder | 0 / 6 |

Sonuç: "Stok tükendi." Retry'da stok kontrolünün tekrar yapılması şart:

```java
@Transactional
public void placeOrder(Long productId, int qty) {
    Product p = repository.findById(productId).orElseThrow();
    if (p.getStock() < qty) {
        throw new OutOfStockException();
    }
    p.setStock(p.getStock() - qty);
}
```

### Pessimistic ile

| Zaman | Olay | DB |
|---|---|---|
| t1 | Ahmet `FOR UPDATE` ile okur, satırı kilitler (stock=2) | 2 |
| t2 | Ayşe aynı satırı okumak ister, **bekler** | 2 |
| t3 | Ahmet stock=0 yapıp commit eder, kilit kalkar | 0 |
| t4 | Ayşe devam eder, stock=0 okur | 0 |
| t5 | İş kuralı reddeder → `OutOfStockException` | 0 |

Exception ve retry yok, Ayşe sıraya girip güncel değeri görüyor.

### Hangisi?

| Durum | Seçim |
|---|---|
| Normal ürün, çakışma nadir | Optimistic (`@Version` + retry) |
| Popüler ürün, kampanya, flash sale | `PESSIMISTIC_WRITE` |

### En pragmatik çözüm: tek atomik UPDATE

Kontrol ve düşürme tek SQL'de, DB kendi içinde atomik yapar. Version, retry, ayrı kilit gerekmez.

```java
@Modifying
@Query("update Product p set p.stock = p.stock - :qty " +
       "where p.id = :id and p.stock >= :qty")
int decreaseStock(@Param("id") Long id, @Param("qty") int qty);
```

```java
@Transactional
public void placeOrder(Long productId, int qty) {
    int updated = repository.decreaseStock(productId, qty);
    if (updated == 0) {
        throw new OutOfStockException();   // stok yetmedi
    }
}
```

- Ahmet: `stock=2 >= 2` → stock=0, 1 satır, başarılı.
- Ayşe: `stock=0 >= 1` → 0 satır, "stok tükendi".

### Gerçek hayatta not

- "Sepete atmak" ile "ödemeyi onaylamak" ayrı şeyler. Stok genelde **onay/ödeme anında** düşer, sepet rezervasyon sayılmaz.
- Ödeme sağlayıcıya (banka) çağrı yaparken **kilit tutma**. Önce stoğu düşür, ödeme başarısız olursa geri ekle.

---

## 6. Hangisini Ne Zaman Seçmeli?

Karar için 4 soru:

1. Çakışma sık mı?
2. İşlem sırasında kullanıcı düşünüyor mu (form açık, kilit uzun tutulur mu)?
3. Çakışınca işlemi baştan denemek (retry) güvenli mi?
4. Çakışma olursa sonuç ne kadar kötü?

### Optimistic senaryoları

Çakışma nadir, kullanıcı formda uzun vakit geçiriyor, hata çıkarsa "yenile, tekrar dene" yeter.

1. **Admin panelinde ürün bilgisi düzenleme:** 5 dakika süren düzenlemede DB kilidi tutulamaz. Çakışırsa 409.
2. **Kullanıcı profili / adres güncelleme:** İki sekme açıldı, çakışma çok nadir, sonuç zararsız.
3. **Doküman / not / wiki düzenleme:** İkincisi uyarılır, birinin yazdığı sessizce ezilmez.
4. **Parent-child tutarlılığı:** Aynı siparişe iki kişi kalem ekliyor, `OPTIMISTIC_FORCE_INCREMENT`.

### Pessimistic senaryoları

Çakışma sık ya da sonucu ağır, işlem kısa, "önce sıraya gir, sonra çalış" daha güvenli.

1. **Banka havalesi / bakiye düşme:** Bakiye eksiye düşmemeli. Transferde iki satır kilitlenirken hep aynı sırayla (küçük id önce) kilitle, yoksa deadlock olur:
   ```java
   Long first = Math.min(fromId, toId);
   Long second = Math.max(fromId, toId);
   ```
2. **Koltuk / bilet / randevu rezervasyonu:** Aynı koltuğu iki kişi seçti, sadece biri alabilir.
3. **Flash sale stok:** Optimistic'te retry fırtınası çıkar. Pessimistic ya da atomik `UPDATE ... WHERE stock >= :qty`.
4. **Sıralı numara üretme:** Fatura no, sipariş no. Sayaç satırı kilitlenir.
5. **Worker'ların aynı kuyruktan iş çekmesi:** Aynı kaydı iki worker işlemesin. SKIP LOCKED ile kilitli olanlar atlanır:
   ```java
   @Lock(LockModeType.PESSIMISTIC_WRITE)
   @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "-2")) // SKIP_LOCKED
   List<Order> findTop10ByStatus(OrderStatus status);
   ```

### Pratik karar tablosu

| Soru | Cevap | Seçim |
|---|---|---|
| Kullanıcı formda uzun süre düşünüyor mu? | Evet | Optimistic (kilit tutulamaz) |
| Çakışma nadir, sonuç zararsız mı? | Evet | Optimistic |
| Aynı satıra sürekli yazılıyor mu? | Evet | Pessimistic (veya atomik UPDATE) |
| Çakışma = para/stok/koltuk hatası mı? | Evet | Pessimistic |
| İşi iki kez yapmak sorun mu? | Evet | Pessimistic (veya SKIP LOCKED) |

### Özet kural

- **Kullanıcıyı bekleten uzun işlemlerde** → optimistic.
- **Kısa ve kritik yazma işlemlerinde** → pessimistic (kısa transaction + timeout).
- **Sadece stok/sayaç düşürmede** → mümkünse tek atomik `UPDATE ... WHERE`.
- Her entity'ye `@Version` koymak çoğu iş için yeterli başlangıçtır, pessimistic'i gerçek çakışma sorunu görünce ekle.