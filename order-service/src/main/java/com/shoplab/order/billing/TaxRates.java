package com.shoplab.order.billing;

import java.util.Map;

/** GST rate in basis points per state code. */
public final class TaxRates {
    static final Map<String, Integer> RATES = Map.of("KA", 1800, "MH", 1800, "TN", 1200);

    private TaxRates() {
    }

    public static int rateFor(String state) {
        return RATES.getOrDefault(state, 1800);
    }

    /** Tax on an amount, rounded to the nearest paisa. */
    public static long taxOn(long amountPaise, String state) {
        return Math.round(amountPaise * rateFor(state) / 10_000.0);
    }
}
