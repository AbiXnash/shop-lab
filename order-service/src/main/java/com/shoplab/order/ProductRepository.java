package com.shoplab.order;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final JdbcClient jdbc;

    public ProductRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<Long> priceOf(long productId) {
        return jdbc.sql("SELECT price_paise FROM products WHERE id = ?").param(productId).query(Long.class).optional();
    }

    public List<Long> pricesOf(List<Long> productIds) {
        return jdbc.sql("SELECT price_paise FROM products WHERE id IN (:ids)").param("ids", productIds).query(Long.class).list();
    }
}
