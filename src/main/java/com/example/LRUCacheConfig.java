package com.example;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LRUCacheConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private int capacity;
    private int size;
    private int hitCount;
    private int missCount;
    private int evictionCount;
    private Map<String, Node> cache;

    public LRUCacheConfig() {
        this.cache = new HashMap<>();
    }

    // Getters and Setters for the fields
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public int getMissCount() {
        return missCount;
    }

    public void setMissCount(int missCount) {
        this.missCount = missCount;
    }

    public int getEvictionCount() {
        return evictionCount;
    }

    public void setEvictionCount(int evictionCount) {
        this.evictionCount = evictionCount;
    }

    public Map<String, Node> getCache() {
        return cache;
    }

    public void setCache(Map<String, Node> cache) {
        this.cache = cache;
    }

    @Override
    public String toString() {
        return "LRUCacheConfig{" +
                "capacity=" + capacity +
                ", size=" + size +
                ", hitCount=" + hitCount +
                ", missCount=" + missCount +
                ", evictionCount=" + evictionCount +
                ", cache=" + cache +
                '}';
    }
}

