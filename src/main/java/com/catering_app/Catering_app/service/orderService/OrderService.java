package com.catering_app.Catering_app.service.orderService;

import com.catering_app.Catering_app.dto.*;
import com.catering_app.Catering_app.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    OrderResponse saveOrder(OrderDto orderDto);
    Optional<Order> getOrderById(UUID id);
    PaymentResponse getTotalAmount(UUID orderId);
    void addLocation(LocationDto locationDto);
    List<Order> getAllOrders();
    void acceptOrder(UUID orderId);
    List<Order> getOrderByUserId(UUID userId);
    Order orderSuccess(OrderSuccessRequest orderSuccessRequest);
    boolean cancelOrder(UUID orderId);
    boolean orderComplete(UUID orderId);
    void confirmPayment(UUID orderId);

}
