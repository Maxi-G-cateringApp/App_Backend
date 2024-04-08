package com.catering_app.Catering_app.service.orderService;

import com.catering_app.Catering_app.dto.*;
import com.catering_app.Catering_app.model.Orders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    OrderResponse saveOrder(OrderDto orderDto);
    Optional<Orders> getOrderById(UUID id);
    PaymentResponse getTotalAmount(UUID orderId);
    void addLocation(LocationDto locationDto);
    List<Orders> getAllOrders();
    void acceptOrder(UUID orderId);
    List<Orders> getOrderByUserId(UUID userId);
    Orders orderSuccess(OrderSuccessRequest orderSuccessRequest);

    boolean cancelOrder(UUID orderId);

}
