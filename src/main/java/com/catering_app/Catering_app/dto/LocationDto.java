package com.catering_app.Catering_app.dto;

import com.catering_app.Catering_app.model.Orders;
import com.catering_app.Catering_app.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
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
