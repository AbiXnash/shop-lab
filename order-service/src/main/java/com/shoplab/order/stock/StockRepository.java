package com.shoplab.order.stock;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class StockRepository {
    private final JdbcClient jdbc;

    public StockRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public int free(long productId) {
        return jdbc.sql("SELECT free FROM stock WHERE product_id = ?").param(productId).query(Integer.class).optional().orElse(0);
    }

    public void add(long productId, int delta) {
        jdbc.sql("UPDATE stock SET free = free + ? WHERE product_id = ?").param(delta).param(productId).update();
    }
}
