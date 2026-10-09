package org.example.spring.aop.service;

import org.example.spring.aop.anottations.CacheAspect;
import org.example.spring.aop.anottations.CacheDeleteAspect;
import org.example.spring.aop.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * in memory
 * */
@Service
public class ProductService {
    private final Map<Long, Product> db = new ConcurrentHashMap<>();

    @CacheDeleteAspect(key = "product")
    public Product save(Product product) {
        db.computeIfAbsent(product.id(), id -> product);
        return product;
    }

    @CacheAspect(key = "product", duration = 100)
    public List<Product> findAll() {
        return db.values().stream().toList();
    }

    @CacheDeleteAspect(key = "product")
    public void delete(Product product) {
        db.remove(product.id());
    }

    @CacheAspect(key = "product", duration = 100)
    public Product findById(Long id) {
        return db.get(id);
    }

}
