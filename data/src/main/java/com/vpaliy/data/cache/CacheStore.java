package com.vpaliy.data.cache;
import com.google.common.cache.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * A Cache-based reactive Store.
 */

public class CacheStore<K, V> {

    private final Cache<K, V> cache;
    private final Map<K, Subject<V, V>> subjectMap = new ConcurrentHashMap<>();

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
        for(Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            if (subjectMap.containsKey(entry.getKey())) {
                subjectMap.get(entry.getKey()).onNext(entry.getValue());
            }
        }
    }

    public Observable<V> getStream(K key) {
        subjectMap.put(key, PublishSubject.create());
        V cachedValue = cache.getIfPresent(key);
        if (cachedValue != null) {
            final Subject<V, V> subject = BehaviorSubject.create(cachedValue);
            subjectMap.get(key).subscribe(subject);
            return subject;
        } else {
            return subjectMap.get(key);
        }
    }

    public  boolean isInCache(K key) {
        return cache.getIfPresent(key) != null;
    }

    public long size(){
        return cache.size();
    }
}