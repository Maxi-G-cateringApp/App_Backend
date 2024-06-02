package com.catering_app.Catering_app.service.paymentService;

import com.catering_app.Catering_app.dto.PaymentConfirmationResponse;
import com.catering_app.Catering_app.dto.PaymentRequest;
import com.catering_app.Catering_app.model.TransactionDetails;

import java.util.UUID;

public interface PaymentService {


    TransactionDetails createTransaction(UUID orderId);
    TransactionDetails createBalanceTransaction(UUID orderId);
}
