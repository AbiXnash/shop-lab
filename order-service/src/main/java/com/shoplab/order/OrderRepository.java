package com.shoplab.order;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    private final JdbcClient jdbc;

    public OrderRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public Optional<Order> findById(long id) {
        return jdbc.sql("SELECT * FROM orders WHERE id = ?").param(id).query(Order.class).optional();
    }

    public List<Order> page(int offset, int size) {
        return jdbc.sql("SELECT * FROM orders ORDER BY id LIMIT ? OFFSET ?").param(size).param(offset).query(Order.class).list();
    }

    /** A customer's most recent orders, newest first. */
    public List<Order> forCustomer(long customerId, long limit) {
        return jdbc.sql("SELECT * FROM orders WHERE customer_id = ? ORDER BY id DESC LIMIT ?")
            .param(customerId).param(limit).query(Order.class).list();
    }

    public void setStatus(long id, String status) {
        jdbc.sql("UPDATE orders SET status = ? WHERE id = ?").param(status).param(id).update();
    }

    public void setReserved(long id, int reserved) {
        jdbc.sql("UPDATE orders SET reserved = ? WHERE id = ?").param(reserved).param(id).update();
    }
}
