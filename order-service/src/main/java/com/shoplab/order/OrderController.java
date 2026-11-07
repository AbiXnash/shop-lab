package com.shoplab.order;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        return service.page(page, size);
    }

    @PostMapping("/{id}/pay")
    public String pay(@PathVariable long id) {
        return service.pay(id);
    }

    @PostMapping("/{id}/cancel")
    public void cancel(@PathVariable long id) {
        service.cancel(id);
    }
}
