package com.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class LRUCache {
    private final int capacity;
    private int size;
    private int hitCount;
    private int missCount;
    private int evictionCount;
    private final Map<String, Node> cache;
    private final Node head, tail;
    private final ReentrantLock lock;
    private final String configFilePath;

    public LRUCache(int capacity, String configFilePath) {
        this.capacity = capacity;
        this.size = 0;
        this.hitCount = 0;
        this.missCount = 0;
        this.evictionCount = 0;
        this.cache = new HashMap<>();
        this.head = new Node();
        this.tail = new Node();
        head.next = tail;
        tail.prev = head;
        this.lock = new ReentrantLock();
        this.configFilePath = configFilePath;

        loadConfig();
    }

    public void set(String key, String value) {
        lock.lock();
        try {
            Node node = cache.get(key);
            if (node == null) {
                Node newNode = new Node(key, value);
                cache.put(key, newNode);
                addNode(newNode);
                size++;
                if (size > capacity) {
                    Node tail = popTail();
                    cache.remove(tail.key);
                    size--;
                    evictionCount++;
                }
            } else {
                node.value = value;
                moveToHead(node);
            }
            saveConfig();
        } finally {
            lock.unlock();
        }
    }

    public String get(String key) {
        lock.lock();
        try {
            Node node = cache.get(key);
            if (node == null) {
                missCount++;
                return null;
            }
            moveToHead(node);
            hitCount++;
            return node.value;
        } finally {
            lock.unlock();
        }
    }

    public void delete(String key) {
        lock.lock();
        try {
            Node node = cache.get(key);
            if (node != null) {
                removeNode(node);
                cache.remove(key);
                size--;
                saveConfig();
            }
        } finally {
            lock.unlock();
        }
    }

    public Map<String, Double> getStats() {
        lock.lock();
        try {
            double totalRequests = hitCount + missCount;
            double hitRate = totalRequests == 0 ? 0 : (double) hitCount / totalRequests;
            double missRate = totalRequests == 0 ? 0 : (double) missCount / totalRequests;
            Map<String, Double> stats = new HashMap<>();
            stats.put("hitRate", hitRate);
            stats.put("missRate", missRate);
            stats.put("evictionCount", (double) evictionCount);
            return stats;
        } finally {
            lock.unlock();
        }
    }

    private void loadConfig() {
        File configFile = new File(configFilePath);
        if (configFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(configFile))) {
                LRUCacheConfig config = (LRUCacheConfig) ois.readObject();
                this.size = config.getSize();
                this.hitCount = config.getHitCount();
                this.missCount = config.getMissCount();
                this.evictionCount = config.getEvictionCount();
                this.cache.putAll(config.getCache());
                for (Node node : cache.values()) {
                    addNode(node);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveConfig() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(configFilePath))) {
            LRUCacheConfig config = new LRUCacheConfig();
            config.setCapacity(this.capacity);
            config.setSize(this.size);
            config.setHitCount(this.hitCount);
            config.setMissCount(this.missCount);
            config.setEvictionCount(this.evictionCount);
            config.setCache(new HashMap<>(this.cache));
            oos.writeObject(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNode(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addNode(node);
    }

    private Node popTail() {
        Node res = tail.prev;
        removeNode(res);
        return res;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(10000, "cache_config.ser");
        cache.set("key1", "value1");
        System.out.println(cache.get("key1"));
        cache.delete("key1");
        System.out.println(cache.get("key1"));
        System.out.println(cache.getStats());
    }
}
