package com.shoplab.order.rules;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/** The business day is the calendar day in India. */
public final class BusinessDay {
    static final ZoneId SHOP_ZONE = ZoneId.of("Asia/Kolkata");

    private BusinessDay() {
    }

    public static LocalDate of(Instant at) {
        return LocalDate.ofInstant(at, ZoneId.of("UTC"));
    }
}
