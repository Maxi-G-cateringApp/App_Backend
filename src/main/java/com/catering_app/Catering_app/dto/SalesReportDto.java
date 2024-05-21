package com.catering_app.Catering_app.dto;

import com.catering_app.Catering_app.model.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SalesReportDto {
    private List<Order> orderList;
    private Double totalSales;
    private Integer orderCount;
    private String token;
}
