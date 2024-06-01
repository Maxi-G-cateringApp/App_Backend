package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.OrderProcessDto;
import com.catering_app.Catering_app.model.OrderProcessing;
import com.catering_app.Catering_app.service.orderProcessService.OrderProcessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderProcessingController {

    private final OrderProcessService orderProcessService;
    public OrderProcessingController(OrderProcessService orderProcessService) {
        this.orderProcessService = orderProcessService;
    }

    @PostMapping("/process_order")
    public ResponseEntity<OrderProcessing> orderProcessing(@RequestBody OrderProcessDto orderProcessDto){
        return ResponseEntity.ok(orderProcessService.orderProcessing(orderProcessDto));
    }


}
