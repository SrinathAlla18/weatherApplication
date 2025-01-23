package com.srinath.weather.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
@Slf4j
public class ConditionalCacheManager implements CacheManager {

    private final CacheManager delegate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CacheManager fallbackCacheManager;

    @Autowired
    public ConditionalCacheManager(CacheManager delegate, RedisTemplate<String, Object> redisTemplate) {
        this.delegate = delegate;
        this.redisTemplate = redisTemplate;
//        this.fallbackCacheManager = new ConcurrentMapCacheManager(); // Fallback to in-memory cache
        this.fallbackCacheManager= new NoOpCacheManager();
    }

    @Override
    public Cache getCache(String name) {
        if (isRedisAvailable()) {
            return delegate.getCache(name);
        }
        log.warn("Redis is unavailable. Caching is disabled for cache: {}", name);
        return fallbackCacheManager.getCache(name); // Disable caching if Redis is unavailable
    }

    @Override
    public Collection<String> getCacheNames() {
        if (isRedisAvailable()) {
            return delegate.getCacheNames(); // Delegate to the underlying CacheManager
        }
        log.warn("Redis is unavailable. No caches are active.");
        return fallbackCacheManager.getCacheNames();

    }

    private boolean isRedisAvailable() {
        try {
             boolean available= Boolean.TRUE.equals(redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.ping() != null));
            if (!available) {
                log.info("Redis is unavailable. Falling back to in-memory cache.");
            }else{
                log.info("redis available");
            }
            return available;
        } catch (Exception e) {
            log.info(" Redis availability exception: " + e.getMessage());
            return false;
        }
    }
}
