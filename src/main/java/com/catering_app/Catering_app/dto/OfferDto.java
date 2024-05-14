package com.catering_app.Catering_app.dto;


import lombok.Data;

@Data
public class OfferDto {

    private String offerName;
    private Integer discount;
    private Integer comboId;
    private boolean isEnabled;
}
