package com.shoplab.order.rules;

/** Converts between rupees and another currency. rate is rupees per one unit of that currency. */
public final class CurrencyConverter {
    private CurrencyConverter() {
    }

    /** Rupee paise to the other currency's cents. */
    public static long fromInr(long paise, double rate) {
        return Math.round(paise * rate);
    }

    /** The other currency's cents to rupee paise. */
    public static long toInr(long cents, double rate) {
        return Math.round(cents * rate);
    }
}
