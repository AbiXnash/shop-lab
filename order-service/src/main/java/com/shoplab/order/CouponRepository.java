package com.shoplab.order;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class CouponRepository {
    private final JdbcClient jdbc;

    public CouponRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    /** The percent off for a coupon that is valid on the given day, if there is one. */
    public Optional<Integer> percentOn(String code, LocalDate day) {
        return jdbc.sql("SELECT percent FROM coupons WHERE code = ? AND valid_from <= ? AND valid_to >= ?")
            .param(code).param(day).param(day).query(Integer.class).optional();
    }

    /** Uses one redemption of a coupon if it has any left; true when one was used. */
    public boolean redeem(String code) {
        return jdbc.sql("UPDATE coupons SET used = used + 1 WHERE code = ? AND used < max_uses")
            .param(code).update() == 1;
    }
}
