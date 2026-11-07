package com.shoplab.payment;

/** The card processor. Charges with the same idempotency key are only made once. */
public interface CardGateway {
    String charge(String idempotencyKey, long amountPaise) throws GatewayTimeout;

    class GatewayTimeout extends Exception {
    }
}
