package com.catering_app.Catering_app.controller.orderController;

import com.catering_app.Catering_app.dto.*;
import com.catering_app.Catering_app.model.Orders;
import com.catering_app.Catering_app.service.orderService.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/save-order")
    public ResponseEntity<OrderResponse>saveOrder(@RequestBody OrderDto orderDto){
        return ResponseEntity.ok(orderService.saveOrder(orderDto));
    }

    @GetMapping("/get/order/amount/{orderId}")
    public ResponseEntity<?> confirmOrder(@PathVariable("orderId")UUID orderId){
      return ResponseEntity.ok(orderService.getTotalAmount(orderId));
    }

    @GetMapping("/get-order/{orderId}")
    public ResponseEntity<Orders> getOrderByUserId(@PathVariable("orderId") UUID orderId){
        Optional<Orders> optionalOrders = orderService.getOrderById(orderId);
        if (optionalOrders.isPresent()){
            Orders order = optionalOrders.get();
            return ResponseEntity.ok(order);
        }
        return null;
    }

    @PostMapping("/add/location")
    public ResponseEntity<?>addLocation(@RequestBody LocationDto locationDto){
        orderService.addLocation(locationDto);
        return ResponseEntity.ok(new ResponseDto(true,"success"));

    }
    @GetMapping("/get-orders")
    public ResponseEntity<List<Orders>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping("/accept-order")
    public ResponseEntity<?> acceptOrder(@RequestParam("orderId") UUID orderId){
        orderService.acceptOrder(orderId);
        return ResponseEntity.ok(new ResponseDto(true,"order accepted"));
    }

    @GetMapping("/get-order/userId/{userId}")
    public ResponseEntity<List<Orders>>getOrderByUser(@PathVariable("userId") UUID userId){
        return ResponseEntity.ok(orderService.getOrderByUserId(userId));

    }

    @PostMapping("/order-success")
    public ResponseEntity<Orders>orderSuccess(@RequestBody OrderSuccessRequest orderSuccessRequest){
        return ResponseEntity.ok(orderService.orderSuccess(orderSuccessRequest));

    }

    @PostMapping("/cancel-order/{orderId}")
    public ResponseEntity<?>cancelOrder(@PathVariable UUID orderId){
       boolean cancelOrder = orderService.cancelOrder(orderId);
        if (cancelOrder){
            return ResponseEntity.ok(new ResponseDto(true,"cancelled"));
        }else{
            return ResponseEntity.ok(new ResponseDto(false," can't cancel "));
        }
    }

}
