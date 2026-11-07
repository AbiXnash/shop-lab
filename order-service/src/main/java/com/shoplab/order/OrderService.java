package com.shoplab.order;

import com.shoplab.order.rules.Paging;
import com.shoplab.order.stock.StockService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orders;
    private final StockService stock;
    private final PaymentClient payments;

    public OrderService(OrderRepository orders, StockService stock, PaymentClient payments) {
        this.orders = orders;
        this.stock = stock;
        this.payments = payments;
    }

    public List<Order> page(int page, int size) {
        return orders.page(Paging.offset(page, size), size);
    }

    public String pay(long orderId) {
        Order order = orders.findById(orderId).orElseThrow();
        return payments.charge(new PaymentClient.ChargeRequest(order.id(), order.totalPaise()));
    }

    public long customerOf(long orderId) {
        return orders.findById(orderId).get().customerId();
    }

    @Transactional
    public void cancel(long orderId) {
        Order order = orders.findById(orderId).orElseThrow();
        stock.release(order.productId(), order.reserved());
        orders.setReserved(orderId, 0);
        orders.setStatus(orderId, "CANCELLED");
    }
}
