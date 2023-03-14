package onecache;

public class RedisCache<T> implements Cache<T> {

    public RedisCache() {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    @Override
    public synchronized CacheResult<T> get(String key) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    @Override
    public void set(String key, T value) {
        this.set(key, value, 0);
    }

    @Override
    public synchronized void set(String key, T value, long expireAfterMS) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    @Override
    public synchronized boolean clear(String key) {
        throw new UnsupportedOperationException("TODO: Implement");
    }
}
