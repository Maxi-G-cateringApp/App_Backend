package com.catering_app.Catering_app.controller.paymentController;


import com.catering_app.Catering_app.model.TransactionDetails;
import com.catering_app.Catering_app.service.paymentService.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class PaymentController {


    @Autowired
    private PaymentService paymentService;
    @PostMapping("/payment/{orderId}")
    public ResponseEntity<TransactionDetails>payment(@PathVariable("orderId") UUID orderId) throws RazorpayException {
        return ResponseEntity.ok(paymentService.createTransaction(orderId));


    }
}
