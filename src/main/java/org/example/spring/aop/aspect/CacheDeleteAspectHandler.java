package org.example.spring.aop.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.spring.aop.anottations.CacheDeleteAspect;
import org.example.spring.aop.repository.CacheRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect
@Log4j2
public class CacheDeleteAspectHandler {
    private final CacheRepository cacheRepository;

    public CacheDeleteAspectHandler(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    /**
     * kral burada ise o key ile başlayan tüm cacheler sildik reflectiom ile method ve anatasyonu aldık
     * */
    @After("@annotation(org.example.spring.aop.anottations.CacheDeleteAspect)")
    public void after(JoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature=(MethodSignature) joinPoint.getSignature();
        Method method=methodSignature.getMethod();
        CacheDeleteAspect cacheDeleteAspect=method.getAnnotation(CacheDeleteAspect.class);
        String[] key=cacheDeleteAspect.key();
        log.info("CacheDeleteAspectHandler: key={} için cache siliniyor", Arrays.stream(key).toArray());
        for(String k:key){
            cacheRepository.delete(k);
            cacheRepository.deleteByPrefix(k + ":");
        }
        log.info("CacheDeleteAspectHandler: key={} için cacheler silindi", Arrays.stream(key).toArray());
    }
}
