package com.catering_app.Catering_app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentRequest {

    private UUID orderId;

}
