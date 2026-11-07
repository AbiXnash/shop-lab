package com.shoplab.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShipmentService {
    private static final Logger log = LoggerFactory.getLogger(ShipmentService.class);
    private final OrderRepository orders;

    public ShipmentService(OrderRepository orders) {
        this.orders = orders;
    }

    @Transactional
    public void markShipped(long orderId, int reserved) {
        try {
            orders.setStatus(orderId, "SHIPPED");
            orders.setReserved(orderId, reserved);
        } catch (RuntimeException e) {
            log.warn("could not mark {} shipped", orderId, e);
        }
    }
}
