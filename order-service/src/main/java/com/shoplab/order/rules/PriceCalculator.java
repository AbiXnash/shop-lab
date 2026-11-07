package com.shoplab.order.rules;

/** Prices in paise. */
public final class PriceCalculator {
    private PriceCalculator() {
    }

    /** The price after the better of the two discounts; they never stack. Percentages are 0 to 100. */
    public static long discounted(long subtotalPaise, int couponPercent, int loyaltyPercent) {
        long afterCoupon = subtotalPaise - subtotalPaise * couponPercent / 100;
        return afterCoupon - afterCoupon * loyaltyPercent / 100;
    }
}
