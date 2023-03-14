package onecache;

public interface Cache<T> {

    /**
     * Get queries this cache for the value under the given key.
     *
     * @param key cache key
     * @return a {@link CacheResult} containing whether the key was found,
     *         and its value
     */
    CacheResult<T> get(String key);

    /**
     * Caches the given value (which may be null) under the given key.
     *
     * @param key   cache key
     * @param value value to cache under the given key
     */
    void set(String key, T value);

    /**
     * Caches the given value (which may be null) under the given key
     * with the given expiry.
     *
     * @param key           cache key
     * @param value         value to cache under the given key
     * @param expireAfterMS duration after which value should expire,
     *                      or 0 to never expire
     */
    void set(String key, T value, long expireAfterMS);

    /**
     * Clears the value, if any, cached under the given key.
     *
     * @param key cache key
     * @return whether a value was cached (and thus cleared)
     */
    boolean clear(String key);
}
