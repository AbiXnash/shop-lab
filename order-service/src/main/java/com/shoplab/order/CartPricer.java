package com.shoplab.order;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CartPricer {
    private final ProductRepository products;

    public CartPricer(ProductRepository products) {
        this.products = products;
    }

    /** Total of the products in a cart, in paise. */
    public long total(List<Long> productIds) {
        long sum = 0;
        for (Long id : productIds) {
            sum += products.priceOf(id).orElse(0L);
        }
        return sum;
    }
}
