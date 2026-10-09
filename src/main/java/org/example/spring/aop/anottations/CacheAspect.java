package org.example.spring.aop.anottations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* Baba amaç burada kendi cache mekanizmanızı oluşturmak ve bunu AOP ile entegre etmektir
 * hibernate arkada nasıl bir yapı kuruyor bunu anlamak için güzel bir örnek
* */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheAspect {
     String key();
     long duration() default 60;

}
