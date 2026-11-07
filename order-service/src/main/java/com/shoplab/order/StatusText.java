package com.shoplab.order;

public final class StatusText {
    private StatusText() {
    }

    /** What the dashboard shows for an order. */
    public static String label(Order order) {
        if (order.status() == "CANCELLED") {
            return "Cancelled";
        }
        return "Active";
    }
}
