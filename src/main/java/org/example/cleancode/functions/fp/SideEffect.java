package org.example.cleancode.functions.fp;

import java.math.BigDecimal;
import java.util.List;

/**
 * hacı bu şu demek bir fonksiyonun dış dünyandan etkilenerek return döndürmesi
 * örneklerle daha iyi anlaşılır ama şunu bilelim side effect kötü değil bilinçsiz olarak kullanılması sıkıntı yaratır.
 */

public class SideEffect {
    /**
    * burada ne yaptı hem totalPrice set etti hem de return döndürdü
     * işte bu side effecttir. fonksiyonel programlamada side effectten kaçınmak gerekir.
     * zarari ise şu : Bu metodu sadece "hesaplama yapsın" diye çağıran biri, order nesnesinin değiştiğini fark etmez.
     * Bug'lar genelde böyle çıkar: sıra değişince, iki kere çağrılınca, farklı thread'den çağrılınca sonuç değişir.
    * */
    public double calculateTotal(Order order) {
       double total=0;
       for(Item item: order.items){
           total+=item.price.doubleValue();
       }
       order.setTotalPrice(BigDecimal.valueOf(total));
       return total;
    }
    /**
     * Çözüm ise hespalama ve set etme metodunu ayırmak. Bu sayede hesaplama yapan metodun tek sorumluluğu olur ve yan etkisi olmaz.
     * */
    public static class Order {
      List<Item> items;
      BigDecimal totalPrice;

      public void setTotalPrice(BigDecimal totalPrice) {
          this.totalPrice = totalPrice;
      }

    }
    public static class Item {
      String name;
      BigDecimal price;
    }
}
