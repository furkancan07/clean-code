package org.example.spring.aop.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.spring.aop.anottations.CacheAspect;
import org.example.spring.aop.repository.CacheRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Log4j2
public class CacheAspectHandler {
    private final CacheRepository cacheRepository;

    public CacheAspectHandler(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    /** babuş bu around method çalışnmadn önce ve sonra çalışır.
     * yani methodun öncesinde ve sonrasında çalışır
     * bu yüzden proceed methodunu çağırmamız gerekiyor. proceed methodu methodun kendisini çağırır
     * ProceedingJoinPoint joinPoint parametresi ise methodun kendisini runtimede alıp değiştirebilir joinpointten en önemli farkı budur
     *
     *
     * peki aşağıdaki mettota ne yaptık
     * ilk başta anatosyonu reflection ile aldık
     * sonra anatosyonun key ve duration değerlerini aldık
     * şimdi atıyorum findById ile çağırabilir bu yüzden keyi hashcode göre almak için
     * methoddaki parametreleri alıp hashcode göre key oluşturduk
     * sonra cacheRepositoryden keyi alıp cachede var mı yok mu kontrol ettik
     * eğer cachede varsa cacheden aldık ve return ettik
     * eğer cachede yoksa proceed ile methodu çalıştırdık ve sonucu cacheledik ve return ettik
     *
     */

    @Around("@annotation(org.example.spring.aop.anottations.CacheAspect)")
    public Object getFromCache(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method=methodSignature.getMethod();
        CacheAspect cacheAspect=method.getAnnotation(CacheAspect.class);

        String hashKey=getHashKey(joinPoint.getArgs());
        String key=cacheAspect.key()+hashKey;
        Object value=cacheRepository.get(key);

        if(value!=null){
            log.info("Cache hit for key: {}", key);
            return value;
        }
        Object result=joinPoint.proceed();
        cacheRepository.save(key,result,cacheAspect.duration());
        return result;
    }

    private String getHashKey(Object[] args) {
        StringBuilder keyBuilder = new StringBuilder();
        for (Object arg : args) {
            keyBuilder.append(":").append(arg.hashCode());
        }
        return keyBuilder.toString();
    }
}
