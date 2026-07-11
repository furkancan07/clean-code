package org.example.cleancode.functions;

import java.lang.invoke.VarHandle;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;


/**
 * Java Stream API — Genel Özet
 *
 * <p>Stream bir veri yapısı DEĞİLDİR, bir işlem hattıdır (pipeline).
 * Veri saklamaz, kaynak üzerinden akan veriyi işler. Ara işlemler
 * "lazy"dir; terminal işlem çağrılana kadar hiçbir şey çalışmaz.
 * Bir stream sadece BİR KEZ tüketilebilir.</p>
 *
 * <table border="1">
 *   <caption>Stream Pipeline Aşamaları</caption>
 *   <tr><th>Aşama</th><th>Açıklama</th><th>Örnek Metotlar</th></tr>
 *   <tr>
 *     <td>1. Kaynak (Source)</td>
 *     <td>Stream'in üretildiği yer</td>
 *     <td>{@code collection.stream()}, {@code Stream.of()}, {@code IntStream.range()}</td>
 *   </tr>
 *   <tr>
 *     <td>2. Ara İşlemler (Intermediate)</td>
 *     <td>Lazy çalışır, yeni bir Stream döner, zincirlenebilir</td>
 *     <td>{@code filter}, {@code map}, {@code sorted}, {@code distinct},
 *         {@code limit}, {@code skip}, {@code peek}, {@code flatMap},
 *         {@code takeWhile}, {@code dropWhile}</td>
 *   </tr>
 *   <tr>
 *     <td>3. Son İşlem (Terminal)</td>
 *     <td>Pipeline'ı tetikler, Stream'i tüketir, tekrar kullanılamaz</td>
 *     <td>{@code collect}, {@code forEach}, {@code reduce}, {@code count},
 *         {@code anyMatch}, {@code allMatch}, {@code noneMatch},
 *         {@code findFirst}, {@code findAny}, {@code toArray}, {@code sum}</td>
 *   </tr>
 * </table>
 *
 * <table border="1">
 *   <caption>Stream Türleri</caption>
 *   <tr><th>Tür</th><th>Kullanım Amacı</th></tr>
 *   <tr><td>{@code Stream<T>}</td><td>Object referansları için genel stream</td></tr>
 *   <tr><td>{@code IntStream}</td><td>Primitive int için, boxing'den kaçınır</td></tr>
 *   <tr><td>{@code LongStream}</td><td>Primitive long için</td></tr>
 *   <tr><td>{@code DoubleStream}</td><td>Primitive double için</td></tr>
 * </table>
 *
 * <p><b>Kritik kural:</b> Ara işlemler her elemanı hemen işlemez,
 * terminal işlem çağrılana kadar tarif olarak biriktirilir (lazy evaluation).
 * Bir kez terminal işlem çalıştıktan sonra stream "tüketilmiş" sayılır
 * ve tekrar kullanılamaz ({@code IllegalStateException} fırlatır).</p>
 *
 *
 * Sık Kullanılan Stream Metotları — Hızlı Referans
 *
 * <table border="1">
 *   <tr><th>Metot</th><th>Tip</th><th>İşlevi</th></tr>
 *   <tr><td>filter</td><td>ara</td><td>Koşula uyanları seçer (Predicate)</td></tr>
 *   <tr><td>map</td><td>ara</td><td>Her elemanı dönüştürür (Function)</td></tr>
 *   <tr><td>flatMap</td><td>ara</td><td>İç içe koleksiyonları düzleştirir</td></tr>
 *   <tr><td>sorted</td><td>ara</td><td>Sıralar (doğal ya da Comparator)</td></tr>
 *   <tr><td>distinct</td><td>ara</td><td>Tekrarları kaldırır</td></tr>
 *   <tr><td>limit / skip</td><td>ara</td><td>İlk N'i alır / atlar</td></tr>
 *   <tr><td>takeWhile / dropWhile</td><td>ara</td><td>Koşul doğruyken al/atla (Java 9+)</td></tr>
 *   <tr><td>peek</td><td>ara</td><td>Debug amaçlı gözlemleme</td></tr>
 *   <tr><td>collect</td><td>terminal</td><td>Sonucu topla (List, Map, String...)</td></tr>
 *   <tr><td>forEach</td><td>terminal</td><td>Her eleman için işlem yap</td></tr>
 *   <tr><td>reduce</td><td>terminal</td><td>Tüm elemanları tek değere indirger</td></tr>
 *   <tr><td>count</td><td>terminal</td><td>Eleman sayısı</td></tr>
 *   <tr><td>anyMatch/allMatch/noneMatch</td><td>terminal</td><td>Koşul kontrolü (boolean)</td></tr>
 *   <tr><td>findFirst/findAny</td><td>terminal</td><td>Optional&lt;T&gt; döner</td></tr>
 *   <tr><td>toArray</td><td>terminal</td><td>Array'e çevirir</td></tr>
 * </table>
 */
public class StreamExample {
    /*
     * ilk olarak functiona interface de
     * map : suplier
     * filter : predicate
     * foreach : consumera denk gelir
     * */
    public static void main(String[] args) {

        record Barkod(String kod, String kanal, int stok) {
        }

        List<Barkod> barkodlar = List.of(
                new Barkod("BC001", "Shopify", 45),
                new Barkod("BC002", "SAP", 0),
                new Barkod("BC003", "Shopify", 12),
                new Barkod("BC004", "Hybris", 30),
                new Barkod("BC005", "Shopify", 0)
        );

        // filter
        List<Barkod> stoktaOlanlar = barkodlar.stream()
                .filter(b -> b.stok() > 0)
                .toList();
        System.out.println("filter: " + stoktaOlanlar.size());

        // map
        List<String> kodlar = barkodlar.stream()
                .map(Barkod::kod)
                .toList();
        System.out.println("map: " + kodlar);

        // sorted -> doğal sıralama
        List<Barkod> stogaGoreSirali = barkodlar.stream()
                .sorted(Comparator.comparingInt(Barkod::stok))
                .toList();
        System.out.println("sorted ilk eleman: " + stogaGoreSirali.get(0).kod());

        // sorted büyükye göre sıralama
        List<Barkod> sirali=barkodlar.stream().sorted((a,b)->b.stok-a.stok).toList();
        System.out.println("sorted: " + sirali.get(0));

        // distcint tekrar edenleri kaldırma
        List<String> kanallar = barkodlar.stream().distinct().map(Barkod::kanal).toList();
        System.out.println("distinct: " + kanallar);

        // limit ilk n elaman alma
        List<Barkod> ilk2=barkodlar.stream().limit(2).toList();
        System.out.println("limit: " + ilk2.size());

        // skip ilk n elaman atla
        List<Barkod> ilk2Haric=barkodlar.stream().skip(2).toList();
        System.out.println("skip: " + ilk2Haric.size());

        // peek ara işlemdeki elemanları görmek için kullanılır debug amaçlı
        List<String> peekOrnegi = barkodlar.stream()
                .peek(b -> System.out.println("işleniyor: " + b.kod()))
                .map(Barkod::kod)
                .toList();

        // flat map iç iöe koleksiyonlari düz yapar
        List<List<String>> listeListesi = List.of(
                List.of("BC001", "BC002"),
                List.of("BC003"),
                List.of("BC004", "BC005")
        );
        List<String> duzListe = listeListesi.stream()
                .flatMap(List::stream)
                .toList();
        System.out.println("flatMap: " + duzListe);



    }

}
