package com.shoplab.order.billing;

import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/** Only one settlement runs at a time across all instances. A crashed run frees the lock after 30 minutes. */
@Component
public class SettlementLock {
    private static final String KEY = "lock:settlement";
    private final StringRedisTemplate redis;

    public SettlementLock(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public boolean acquire(String owner) {
        return Boolean.TRUE.equals(redis.opsForValue().setIfAbsent(KEY, owner));
    }

    /** Frees the lock, but only if this owner still holds it. */
    public void release(String owner) {
        redis.delete(KEY);
    }
}
