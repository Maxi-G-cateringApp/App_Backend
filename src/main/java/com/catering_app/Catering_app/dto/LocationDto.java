package com.catering_app.Catering_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDto {



    private String address;
    private String place;
    private String district;
    private double latitude;
    private double longitude;
    private String name;
    private UUID orderId;
    private UUID userId;
    private Float totalAmount;
    private Float advancedAmount;

}
