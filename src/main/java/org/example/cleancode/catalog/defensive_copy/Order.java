package org.example.cleancode.catalog.defensive_copy;

import java.util.Collections;
import java.util.List;

/*
* bir sınıfı immutable yaptık diyelim
* ama değişknelerinde mutable bir nesen var
* getter üzerinden muttable nesneyi değiştirebilirsen bu kritik bir bugdur
* bunun önüne geçen tekniğin adı defensive copy
* */
public class Order {
    private List<String> items; // list muttable
    public Order(List<String> items) {
       // this.items = items;  kötü örnek yorum satırına aldım itemsi getter ile alarak
        this.items=List.copyOf(items);
    }
    public List<String> getItems() {
       //  return items; buda kötü
        return Collections.unmodifiableList(items);
    }
}
