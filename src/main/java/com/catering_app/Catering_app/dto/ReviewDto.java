package com.catering_app.Catering_app.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ReviewDto {


    private String review;
    private Integer rating;
    private Date reviewDate;
    private UUID userId;
    private UUID orderId;
}
