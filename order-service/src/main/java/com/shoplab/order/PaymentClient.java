package com.shoplab.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", path = "/payments")
public interface PaymentClient {
    @PostMapping("/charge")
    String charge(@RequestBody ChargeRequest request);

    record ChargeRequest(long orderId, long amountPaise) {
    }
}
