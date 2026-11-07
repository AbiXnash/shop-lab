package com.shoplab.order;

import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/** Prices per product and currency, cached in Redis for ten minutes. */
@Component
public class PriceCache {
    private final StringRedisTemplate redis;

    public PriceCache(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public Long get(long productId, String currency) {
        String v = redis.opsForValue().get(key(productId, currency));
        return v == null ? null : Long.valueOf(v);
    }

    public void put(long productId, String currency, long price) {
        redis.opsForValue().set(key(productId, currency), Long.toString(price), Duration.ofMinutes(10));
    }

    static String key(long productId, String currency) {
        return "price:" + currency + ":" + productId;
    }
}
