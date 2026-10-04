package org.example.cleancode.functions.fp;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
* aga bunun olması için 2 şeyin en az biri olması lazım
* 1. parametre olarak fonksiyon alabilmeli
 * 2. sonuç olarak fonksiyon döndürebilmeli
* */
public class HigherOrderFunctions {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        List<String> strings=List.of("  hello  ", "  world  ", "  java  ");
        // parametre olarak fonksiyon alan örnek
        List<Integer> squaredNumbers = transform(numbers, number -> number * number);
        System.out.println(squaredNumbers); // [1, 4, 9, 16, 25]

        List<String> upperStrings = upperAndTrimAll(strings, upperAndTrim());
        System.out.println(upperStrings); // [HELLO, WORLD, JAVA]

        // fonksiyon döndüren örnek
        Predicate<Integer> isEven = isGreaterThan();
        System.out.println(isEven.test(4)); // true
        System.out.println(isEven.test(5)); // false
    }
    // fonksiyon alan örnek
    static List<Integer> transform(List<Integer> list, Function<Integer,Integer> function){
        return list.stream().map(function).toList();
    }
    // fonksiyon döndüren örnek
    static Predicate<Integer> isGreaterThan(){
        return number -> number % 2 == 0;
    }

    /**
     * bir örnek daha yapalım
     * List<String> upperAll(List<String> list) {
     *     List<String> r = new ArrayList<>();
     *     for (String s : list) r.add(s.toUpperCase());
     *     return r;
     * }
     *
     * List<String> trimAll(List<String> list) {
     *     List<String> r = new ArrayList<>();
     *     for (String s : list) r.add(s.trim());
     *     return r;
     *
     *     bunu tek hof a indirelim
     * */

   static List<String> upperAndTrimAll(List<String> list, Function<String,String> function){
        return list.stream().map(function).toList();
    }
    static Function<String,String> upperAndTrim(){
        return s->s.toUpperCase().trim();
    }
}
