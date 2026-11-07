package com.shoplab.payment;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/charge")
    public String charge(@RequestBody ChargeRequest request) throws CardGateway.GatewayTimeout {
        return service.charge(request.orderId(), request.amountPaise());
    }

    public record ChargeRequest(long orderId, long amountPaise) {
    }
}
