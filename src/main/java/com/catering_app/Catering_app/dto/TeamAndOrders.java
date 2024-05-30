package com.catering_app.Catering_app.dto;

import com.catering_app.Catering_app.model.Order;
import com.catering_app.Catering_app.model.OrderProcessing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TeamAndOrders<T> {

    private T team;
    private List<Order> orders;
}
