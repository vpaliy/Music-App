package com.vpaliy.data.cache;
import com.google.common.cache.Cache;
import java.util.Map;
import io.reactivex.Single;

/**
 * A Cache-based reactive Store.
 */

public class CacheStore<K, V> {

    private final Cache<K, V> cache;

    public CacheStore(Cache<K, V> cache) {
        this.cache = cache;
    }

    public void invalidate(K key) {
        cache.invalidate(key);
    }

    public void put(K key, V value) {
        cache.put(key, value);
    }

    public void putAll(Map<? extends K,? extends V> m) {
        cache.putAll(m);
    }

    public Single<V> getStream(K key) {
        V value=cache.getIfPresent(key);
        if(value!=null){
            return Single.just(value);
        }
        return Single.error(new IllegalArgumentException("Wrong key!"));
    }

    public  boolean isInCache(K key) {
        return key!=null && cache.getIfPresent(key) != null;
    }

    public long size(){
        return cache.size();
    }
}