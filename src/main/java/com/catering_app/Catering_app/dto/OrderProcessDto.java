package com.catering_app.Catering_app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderProcessDto {

    private UUID orderId;
    private Integer servingTeamId;
    private Integer decorationTeamId;
    private Integer kitchenCrewId;

}
