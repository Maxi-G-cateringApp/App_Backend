package com.catering_app.Catering_app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionDetails {

    private String orderId;
    private String currency;
    private Integer amount;
    private String key;
}
