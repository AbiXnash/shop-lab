package com.shoplab.order.stock;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {
    private final StockRepository stock;

    public StockService(StockRepository stock) {
        this.stock = stock;
    }

    /** Reserves up to quantity and returns how many were reserved. */
    @Transactional
    public int reserve(long productId, int quantity) {
        int taken = Math.min(stock.free(productId), quantity);
        stock.add(productId, -taken);
        return taken;
    }

    @Transactional
    public void release(long productId, int quantity) {
        stock.add(productId, quantity);
    }
}
