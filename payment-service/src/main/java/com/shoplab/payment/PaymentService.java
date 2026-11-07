package com.shoplab.payment;

import java.time.Duration;
import java.util.UUID;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    static final long LIMIT_PAISE = 5_000_000;
    private final CardGateway gateway;
    private final StringRedisTemplate redis;

    public PaymentService(CardGateway gateway, StringRedisTemplate redis) {
        this.gateway = gateway;
        this.redis = redis;
    }

    /** Charges an order once: the order's key is remembered in Redis for 24 hours. */
    public String charge(long orderId, long amountPaise) throws CardGateway.GatewayTimeout {
        if (!FraudCheck.allowed(amountPaise, LIMIT_PAISE)) {
            throw new IllegalStateException("blocked by the fraud limit");
        }
        String key = keyFor(orderId);
        return gateway.charge(key, amountPaise);
    }

    String keyFor(long orderId) {
        String slot = "idem:" + orderId;
        redis.opsForValue().setIfAbsent(slot, UUID.randomUUID().toString(), Duration.ofHours(24));
        return redis.opsForValue().get(slot);
    }
}
