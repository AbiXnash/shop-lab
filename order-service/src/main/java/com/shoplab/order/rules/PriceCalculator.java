package com.shoplab.order.rules;

/** Prices in paise. */
public final class PriceCalculator {
    private PriceCalculator() {
    }

    /** The price after the better of the two discounts; they never stack. Percentages are 0 to 100. */
    public static long discounted(long subtotalPaise, int couponPercent, int loyaltyPercent) {
        int best = Math.max(couponPercent, loyaltyPercent);
        return subtotalPaise - subtotalPaise * best / 100;
    }
}
