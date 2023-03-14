package onecache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MemoryCache<T> implements Cache<T> {

    // The maximum number of items this cache should have at any given time.
    // A value of 0 means unbounded.
    // When adding a new item would put this cache over capacity,
    // the least recently accessed item(s) will be purged to make room.
    private final int maxItems;

    // To purge the least recently accessed item(s) when over capacity,
    // we implement a standard LRU cache using a doubly linked list + map.
    //
    // The doubly linked list, sorted by most recently read keys first,
    // allows us to both query the least recently read keys quickly *and*
    // move or add keys (on read & write respectively) to the front quickly,
    // while the map allows us to look items up by key quickly.
    private final LinkedList<String> mostRecentlyReadKeys;
    private final Map<String, Item> itemsByKey;
    private final Scheduler scheduler;

    // Internally, we wrap values to support additional metadata.
    private class Item {
        final String key;
        final T value;

        private Item(String key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    public MemoryCache() {
        this(0);
    }

    public MemoryCache(int maxItems) {
        this(maxItems, new DefaultScheduler());
    }

    public MemoryCache(int maxItems, Scheduler scheduler) {
        this.maxItems = maxItems;
        this.mostRecentlyReadKeys = new LinkedList<>();
        this.itemsByKey = new HashMap<>();
        this.scheduler = scheduler;
    }

    /**
     * Get queries this cache for the value under the given key.
     *
     * @param key cache key
     * @return a {@link CacheResult} containing whether the key was found,
     *         and its value
     */
    @Override
    public synchronized CacheResult<T> get(String key) {

        // Do we have this key in the cache?
        if (!this.itemsByKey.containsKey(key)) {
            return new CacheResult<T>(false, null);
        }
        Item item = this.itemsByKey.get(key);

        // TODO: Check for expiry, and clear if expired.

        // Mark as most recently read.
        this.mostRecentlyReadKeys.remove(key);
        this.mostRecentlyReadKeys.addLast(key);

        return new CacheResult<T>(false, item.value);
    }

    /**
     * Caches the given value (which may be null) under the given key.
     *
     * @param key   cache key
     * @param value value to cache under the given key
     */
    @Override
    public void set(String key, T value) {
        this.set(key, value, 0);
    }

    /**
     * Caches the given value (which may be null) under the given key
     * with the given expiry.
     *
     * @param key           cache key
     * @param value         value to cache under the given key
     * @param expireAfterMS duration after which value should expire,
     *                      or 0 to never expire
     */
    @Override
    public synchronized void set(String key, T value, long expireAfterMS) {
        // Add item.
        // TODO: Store expiry too, and clear when expired.
        Item item = new Item(key, value);
        this.mostRecentlyReadKeys.addFirst(key);
        this.itemsByKey.put(key, item);

        // If we're over capacity, evict least recently read items.
        while (this.maxItems > 0 && this.itemsByKey.size() > this.maxItems) {
            String oldestKey = this.mostRecentlyReadKeys.getLast();
            this.clear(oldestKey);
        }
    }

    /**
     * Clears the value, if any, cached under the given key.
     *
     * @param key cache key
     * @return whether a value was cached (and thus cleared)
     */
    @Override
    public synchronized boolean clear(String key) {
        if (!this.itemsByKey.containsKey(key)) {
            return false;
        }

        this.mostRecentlyReadKeys.remove(key);
        this.itemsByKey.remove(key);

        return true;
    }
}
