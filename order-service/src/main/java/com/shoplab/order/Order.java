package com.shoplab.order;

public record Order(long id, long customerId, long productId, String status, long totalPaise, int quantity, int reserved) {
}
