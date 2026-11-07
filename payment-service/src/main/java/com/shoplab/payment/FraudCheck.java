package com.shoplab.payment;

/** A payment at the limit is allowed; above it is blocked. */
public final class FraudCheck {
    private FraudCheck() {
    }

    public static boolean allowed(long amountPaise, long limitPaise) {
        return amountPaise <= limitPaise;
    }
}
