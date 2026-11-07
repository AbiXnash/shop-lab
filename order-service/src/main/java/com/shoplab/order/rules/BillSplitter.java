package com.shoplab.order.rules;

/** Splits a bill between people so the parts add up to the total. */
public final class BillSplitter {
    private BillSplitter() {
    }

    public static long[] split(long totalPaise, int people) {
        if (people <= 0) {
            throw new IllegalArgumentException("people must be positive");
        }
        long[] parts = new long[people];
        long base = totalPaise / people;
        long left = totalPaise % people;
        for (int i = 0; i < people; i++) {
            parts[i] = base + (i < left ? 1 : 0);
        }
        return parts;
    }
}
