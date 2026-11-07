package com.shoplab.order.billing;

import java.time.LocalDate;

public record Settlement(long orderId, LocalDate businessDay, long amountPaise, long taxPaise) {
}
