package com.catering_app.Catering_app.service.orderProcessService;

import com.catering_app.Catering_app.dto.OrderProcessDto;
import com.catering_app.Catering_app.model.OrderProcessing;

public interface OrderProcessService {

    OrderProcessing orderProcessing(OrderProcessDto orderProcessDto);
}
