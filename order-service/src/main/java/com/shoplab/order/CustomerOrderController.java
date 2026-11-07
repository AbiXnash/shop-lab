package com.shoplab.order;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/customers/{customerId}/orders")
public class CustomerOrderController {
    private final OrderRepository orders;

    public CustomerOrderController(OrderRepository orders) {
        this.orders = orders;
    }

    @GetMapping
    public List<Order> recent(@PathVariable long customerId, @RequestParam(defaultValue = "10") long limit) {
        return orders.forCustomer(limit, customerId);
    }

    /** One of the customer's own orders; another customer's order is not found. */
    @GetMapping("/{orderId}")
    public Order one(@PathVariable long customerId, @PathVariable long orderId) {
        return orders.findById(orderId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
