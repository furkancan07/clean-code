package org.example.cleancode.functions.fp;

import java.util.Comparator;

/**
 *  javada fonksiyonlar birinci sınıf vatandaş olduğunda şu 3 şeyi yapabilir
 *  1-bir değişkene atanabilir
 *  2-bir fonksiyona parametre olarak verilebilir
 *  3-bir fonksiyondan döndürülebilir
 *
 *  aga bu java 8 den önce interface ile yapılırdı ama java 8 ile lambda expression geldi ve daha kolay oldu
 *  repoda functional interface ler var java.util.function paketinde inceleyebilirsin
 *
 * */
public class FirstClassFunctions {
    // Eski yol
    Comparator<String> byLength = new Comparator<String>() {
        @Override
        public int compare(String a, String b) {
            return Integer.compare(a.length(), b.length());
        }
    };

    Comparator<String> newByLength = (a, b) -> Integer.compare(a.length(), b.length());

    // daha detatlı bakmak için functional interface lerin repodaki örneklerine bakabilirsin
}
