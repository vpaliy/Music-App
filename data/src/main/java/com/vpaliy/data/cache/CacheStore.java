package com.vpaliy.data.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import io.reactivex.Single;

/**
 * A Cache-based reactive Store.
 */

public class CacheStore<K, V> {

  private final ConcurrentMap<K, V> cache;

  public CacheStore(ConcurrentMap<K, V> cache) {
    this.cache = cache;
  }

  public void invalidate(K key) {
    cache.remove(key);
  }

  public void put(K key, V value) {
    cache.put(key, value);
  }

  public void putAll(Map<? extends K, ? extends V> m) {
    cache.putAll(m);
  }

  public Single<V> getStream(K key) {
    V value = cache.get(key);
    if (value != null) {
      return Single.just(value);
    }
    return Single.error(new IllegalArgumentException("Wrong key!"));
  }

  public boolean isInCache(K key) {
    return key != null && cache.get(key) != null;
  }

  public long size() {
    return cache.size();
  }
}