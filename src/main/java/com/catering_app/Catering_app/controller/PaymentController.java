package com.catering_app.Catering_app.controller;


import com.catering_app.Catering_app.model.TransactionDetails;
import com.catering_app.Catering_app.service.paymentService.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment/{orderId}")
    public ResponseEntity<TransactionDetails>payment(@PathVariable("orderId") UUID orderId) throws RazorpayException {
        return ResponseEntity.ok(paymentService.createTransaction(orderId));

    }

    @PostMapping("/payment/balance/{orderId}")
    public ResponseEntity<TransactionDetails>balancePayment(@PathVariable("orderId") UUID orderId) throws RazorpayException {
        return ResponseEntity.ok(paymentService.createBalanceTransaction(orderId));
    }
}
