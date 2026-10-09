package org.example.spring.aop.repository;

import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/*
* bura sanki redisin kayıt alma gibi düşün şimdi üşendim redisi implemenmt etmekle
* */
@Repository
public class CacheRepository {
    private final Map<String, CacheEntry> store = new ConcurrentHashMap<>();

    private record CacheEntry(Object value, Instant expiresAt) {}

    public void save(String key, Object value, long durationSeconds) {
        store.put(key, new CacheEntry(value, Instant.now().plusSeconds(durationSeconds)));
    }

    public Object get(String key) {
        CacheEntry entry = store.get(key);
        if (entry == null) {
            return null;
        }
        if (Instant.now().isAfter(entry.expiresAt())) {
            store.remove(key);
            return null;
        }
        return entry.value();
    }

    public void delete(String key) {
        store.remove(key);
    }

    public void deleteByPrefix(String prefix) {
        store.keySet().removeIf(k -> k.startsWith(prefix));
    }

}
