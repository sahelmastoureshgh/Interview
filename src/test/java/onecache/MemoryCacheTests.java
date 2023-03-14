package onecache;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MemoryCacheTests {

    private void assertNotInCache(MemoryCache cache, String key) {
        CacheResult result = cache.get(key);
        assertEquals(false, result.cached, String.format("Expected %s to not be in cache but was", key));
        assertEquals(null, result.value);
    }

    private void assertCachedEquals(MemoryCache cache, String key, Object expected) {
        CacheResult result = cache.get(key);
        assertEquals(true, result.cached, String.format("Expected %s to be in cache but wasn't", key));
        assertEquals(expected, result.value);
    }

    /**
     * Without worrying about capacity or expiry, we should be able to get, set, and clear.
     */
    @Test
    void basic() {
        MemoryCache cache = new MemoryCache();

        // Initially, the cache should contain nothing.

        assertNotInCache(cache, "a");
        assertNotInCache(cache, "b");
        assertNotInCache(cache, "c");

        // We should be able to add items to the cache, then retrieve them.

        String a = "A";
        String b = "B";

        cache.set("a", a);
        cache.set("b", b);

        assertCachedEquals(cache, "a", a);
        assertCachedEquals(cache, "b", b);

        // Should be able to keep reading the items; they shouldn't be evicted.

        assertCachedEquals(cache, "a", a);
        assertCachedEquals(cache, "b", b);

        // We should be able to clear items from the cache too.
        // Other items should remain in the cache.

        assertEquals(true, cache.clear("a"));
        assertEquals(false, cache.clear("a"));

        assertNotInCache(cache, "a");
        assertCachedEquals(cache, "b", b);
    }

    /**
     * We should be able to limit the cache to a fixed memory capacity.
     * Adding items to the cache beyond the capacity should purge the
     * least recently read items.
     */
    @Test
    void capacity() {
        MemoryCache<String> cache = new MemoryCache(3);

        // Add items up to the capacity. All items should be retained.

        String a = "A";
        String b = "B";
        String c = "C";

        cache.set("a", a);
        cache.set("b", b);
        cache.set("c", c);

        assertCachedEquals(cache, "a", a);
        assertCachedEquals(cache, "b", b);
        assertCachedEquals(cache, "c", c);

        // Access the items in some different order.

        assertCachedEquals(cache, "a", a);
        assertCachedEquals(cache, "b", b);
        assertCachedEquals(cache, "a", a);
        assertCachedEquals(cache, "b", b); // b is least recently read
        assertCachedEquals(cache, "c", c);
        assertCachedEquals(cache, "a", a);
        assertCachedEquals(cache, "c", c); // c is most recently read

        // Add one more item to the cache. The least recently read item should be gone.

        String d = "D";

        cache.set("d", d);

        assertNotInCache(cache, "b");

        assertCachedEquals(cache, "a", a);
        assertCachedEquals(cache, "c", c);
        assertCachedEquals(cache, "d", d);

        // Access the current items in some different order again

        assertCachedEquals(cache, "c", c); // c is least recently read
        assertCachedEquals(cache, "d", d);
        assertCachedEquals(cache, "a", a); // a is most recently read

        // Add *two* items to the cache.
        // The *two* least recently read items should be gone.

        String e = "E";

        cache.set("b", b); // b again
        cache.set("e", e);

        assertNotInCache(cache, "c");
        assertNotInCache(cache, "d");

        assertCachedEquals(cache, "a", a);
        assertCachedEquals(cache, "b", b);
        assertCachedEquals(cache, "e", e);
    }

    /**
     * TODO: Test expiry.
     */
    @Test
    @Disabled
    void expiry() {
        // TODO: Implement!
    }
}
