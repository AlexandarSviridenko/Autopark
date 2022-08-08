package com.Autopark.infrastructure.core.impl;

import com.Autopark.infrastructure.core.Cache;

import java.util.HashMap;
import java.util.Map;

public class CacheImpl implements Cache {

    private Map<String, Object> cache;

    public CacheImpl() {
        cache = new HashMap<>();
    }

    @Override
    public boolean contains(Class<?> clazz) {
        String name = clazz.getName();
        for (Map.Entry<String, Object> elem : cache.entrySet()) {
            if (elem.getKey().equals(name)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public <T> T get(Class<?> clazz) {
        return (T) cache.get(clazz.getName());
    }

    @Override
    public <T> void put(Class<T> target, T value) {
        cache.put(target.getName(), value);
    }
}
