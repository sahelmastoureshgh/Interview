package onecache;

/**
 * POJO representing the result returned by getting from a {@link Cache}.
 * If found (& unexpired), returns true and the value -- which may be null.
 * If not found (or expired), returns false and null.
 */
public class CacheResult<T> {

    /** Does the cache contain a value for this key? */
    public final boolean cached;

    /**
     * Cached value. If cached is true, value is the result, which may be null.
     * If cached is false, value is null.
     */
    public final T value;

    public CacheResult(boolean cached, T value) {
        this.cached = cached;
        this.value = value;
    }
}
