package com.catering_app.Catering_app.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderSuccessRequest {

    private UUID orderId;
    private Float totalAmount;
    private Float advanceAmount;
    private String transactionId;

}
