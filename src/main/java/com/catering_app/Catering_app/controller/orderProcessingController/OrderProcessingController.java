package com.catering_app.Catering_app.controller.orderProcessingController;

import com.catering_app.Catering_app.dto.OrderProcessDto;
import com.catering_app.Catering_app.model.OrderProcessing;
import com.catering_app.Catering_app.service.orderProcessService.OrderProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderProcessingController {

    @Autowired
    private OrderProcessService orderProcessService;
    @PostMapping("/process_order")
    public ResponseEntity<OrderProcessing> orderProcessing(@RequestBody OrderProcessDto orderProcessDto){
        return ResponseEntity.ok(orderProcessService.orderProcessing(orderProcessDto));
    }


}
