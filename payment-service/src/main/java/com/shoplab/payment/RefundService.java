package com.shoplab.payment;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/** Refunds against a paid order. The total refunded for an order never goes above what was paid. */
@Service
public class RefundService {
    private final StringRedisTemplate redis;

    public RefundService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    /** Records a refund and returns the total refunded so far. */
    public long refund(long orderId, long amountPaise, long paidPaise) {
        if (amountPaise <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        long already = refunded(orderId);
        if (already + amountPaise > paidPaise) {
            throw new IllegalStateException("refund would exceed the amount paid");
        }
        Long total = redis.opsForValue().increment("refunded:" + orderId, amountPaise);
        return total == null ? already + amountPaise : total;
    }

    public long refunded(long orderId) {
        String v = redis.opsForValue().get("refunded:" + orderId);
        return v == null ? 0 : Long.parseLong(v);
    }
}
