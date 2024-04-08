package com.catering_app.Catering_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentConfirmationResponse {

    private String secretKey;
    private String razorpayOrderId;
    private String secretId;
    private String amount;

}
