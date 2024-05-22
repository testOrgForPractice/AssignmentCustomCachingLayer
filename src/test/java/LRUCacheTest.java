
import org.junit.jupiter.api.Test;

import com.example.LRUCache;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class LRUCacheTest {

    @Test
    public void testSetAndGet() {
        LRUCache cache = new LRUCache(2, "cache_config_test.ser");
        cache.set("key1", "value1");
        assertEquals("value1", cache.get("key1"));
    }

    @Test
    public void testEviction() {
        LRUCache cache = new LRUCache(2, "cache_config_test.ser");
        cache.set("key1", "value1");
        cache.set("key2", "value2");
        cache.set("key3", "value3");
        assertNull(cache.get("key1"));
        assertEquals("value2", cache.get("key2"));
        assertEquals("value3", cache.get("key3"));
    }

    @Test
    public void testStats() {
        LRUCache cache = new LRUCache(2, "cache_config_test.ser");
        cache.set("key1", "value1");
        cache.get("key1");
        cache.get("key2");
        Map<String, Double> stats = cache.getStats();
        assertEquals(0.5, stats.get("hitRate"), 0.01);
        assertEquals(0.5, stats.get("missRate"), 0.01);
    }
}

