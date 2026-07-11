package org.example.cleancode.functions;

import java.util.*;
import java.util.function.*;

/**
 * java.util.function paketindeki temel 4 functional interface özeti.
 *
 * <table border="1">
 *   <caption>Temel Functional Interface'ler</caption>
 *   <tr>
 *     <th>Interface</th>
 *     <th>Parametre</th>
 *     <th>Dönüş</th>
 *     <th>Abstract Metot</th>
 *   </tr>
 *   <tr>
 *     <td>{@link java.util.function.Supplier}&lt;T&gt;</td>
 *     <td>—</td>
 *     <td>T</td>
 *     <td>{@code get()}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link java.util.function.Consumer}&lt;T&gt;</td>
 *     <td>T</td>
 *     <td>—</td>
 *     <td>{@code accept()}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link java.util.function.Function}&lt;T, R&gt;</td>
 *     <td>T</td>
 *     <td>R</td>
 *     <td>{@code apply()}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link java.util.function.Predicate}&lt;T&gt;</td>
 *     <td>T</td>
 *     <td>boolean</td>
 *     <td>{@code test()}</td>
 *   </tr>
 * </table>
 *
 * <p><b>Kısa özet:</b></p>
 * <ul>
 *   <li>Supplier  -&gt; parametre almaz,  bir şey  üretir   (get)</li>
 *   <li>Consumer  -&gt; bir şey  alır,    hiçbir şey döndürmez (accept)</li>
 *   <li>Function  -&gt; bir şey  alır,    başka bir şey döndürür (apply)</li>
 *   <li>Predicate -&gt; bir şey  alır,    true/false döndürür (test)</li>
 * </ul>
 */

public class FunctionalInterfaceExample {
    /*
    * java 8 ile geldi 4 başlıkta
    * 1-) consumer : bir şey döndürmez paramtre alır ve işlem yapar
    * tri consumea kadar java destekler daha fazla parametre için
    *  kendi fucntional interfaceni yazabilirsim
    *
    * 2-) Supplier : paramtre almaz bir şey üretir döndürür
    * 3-) Function<T,R> t yi paramtre alır R yi döndürür
    * 4->Predicate<Integer> bir şey alır true false döndrürü
    * filter tam olarak burasıdır
    *
    * */


    public static void main(String[] args) {
        /**Consumer Örnekleri**/
        Consumer<String> consumer=(String s) -> {
            System.out.println("consumer is " + s);
        };
        consumer.accept("hello");

        Consumer<String> logla = s -> System.out.println("[LOG] " + s);
        Consumer<String> veritabaninaKaydet = s -> System.out.println("[DB] kaydedildi: " + s);

        Consumer<String> loglaVeKaydet = logla.andThen(veritabaninaKaydet);
        loglaVeKaydet.accept("yeni sipariş");


        BiConsumer<String,Integer> stockUpdate=(urunAdı,adet)->{
            System.out.println("Stok güncellendi: " + urunAdı + " - " + adet);
        };
        stockUpdate.accept("yeni", 1);
        /********************************************************************************/

        /**Suplier Örnekleri*/
        Supplier<String> selamla = () -> "Selam";
        System.out.println(selamla.get());

        Supplier<List<String>> generateList = ArrayList::new;
        List<String> list = generateList.get();

        /**************************************************************************************/

        /** Function Örnekleri**/

        Function<String,Integer> uzunluk = String::length;
        System.out.println(uzunluk.apply("hello"));

        List<String> stiller = List.of("STY001", "STY002", "STY003");
        List<Integer> uzunluklar=stiller.stream().map(uzunluk).toList();

        Function<Integer,Integer> carp = x->x*2;
        Function<Integer,Integer> ekle = x->x+1;

        Function<Integer,Integer> carpveekle=carp.andThen(ekle); // once çarpar sonra ekler
        Function<Integer,Integer> eklevecarp=carp.compose(ekle); // once ekler sonra çarpar
        System.out.println(carpveekle.apply(5));
        System.out.println(eklevecarp.apply(5));

        // aynı consumer gibi tri ye kadar destekler sonrasını sen yazabilirsin
        BiFunction<String,String,String> biFunctionExample =(a,b)->a+"_"+b;
        System.out.println(biFunctionExample.apply("hello","world"));

        /***********************************************************************************/

        /**Predicate Örnekleri*/
        Predicate<Integer> ciftMi=x->x%2==0;
        System.out.println(ciftMi.test(5));
        // çok da örneğe gerek yok anlaşılır

        // BUNLARID DIŞINDA PROJEDEKİ SENATYOYA GÖRE KENDİ İNTERFACELERİNİZİ YAZABİLİRSİN


    }

}
