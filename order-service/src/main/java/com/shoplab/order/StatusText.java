package com.shoplab.order;

public final class StatusText {
    private StatusText() {
    }

    /** What the dashboard shows for an order. */
    public static String label(Order order) {
        if ("CANCELLED".equals(order.status())) {
            return "Cancelled";
        }
        return "Active";
    }
}
