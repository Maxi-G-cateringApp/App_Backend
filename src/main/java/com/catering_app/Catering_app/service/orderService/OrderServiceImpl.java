package com.catering_app.Catering_app.service.orderService;

import com.catering_app.Catering_app.dto.*;
import com.catering_app.Catering_app.model.*;
import com.catering_app.Catering_app.repository.NotificationRepository;
import com.catering_app.Catering_app.repository.OrderRepository;
import com.catering_app.Catering_app.repository.UserLocationRepository;
import com.catering_app.Catering_app.repository.UserRepository;
import com.catering_app.Catering_app.service.authService.AuthenticationService;
import com.catering_app.Catering_app.service.eventService.EventService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final AuthenticationService authenticationService;
    private final NotificationRepository notificationRepository;
    private final OrderRepository orderRepository;
    private final UserLocationRepository userLocationRepository;
    private final EventService eventService;
    private final UserRepository userRepository;




    @Override
    public OrderResponse saveOrder(OrderDto orderDto) {
        Order order = new Order();
        Optional<User> optionalUser = authenticationService.getUserById(orderDto.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            order.setUser(user);
            createOrder(orderDto, order);

            List<OrderedCombos> OrderedCombosList = orderDto.getFoodCombos().stream().map(
                    foodCombo -> {
                        OrderedCombos orderedCombos = new OrderedCombos();
                        orderedCombos.setFoodCombos(foodCombo);
                        orderedCombos.setOrder(order);
                        return orderedCombos;
                    }).toList();
            List<OrderedItems> orderedItemsList = orderDto.getFoodItems().stream().map(
                    item -> {
                        OrderedItems orderedItems = new OrderedItems();
                        orderedItems.setFoodItems(item);
                        orderedItems.setOrder(order);
                        return orderedItems;
                    }).toList();
            Optional<Events> optionalEvents = eventService.getEventById(orderDto.getEventId());
            optionalEvents.ifPresent(order::setEvents);
            order.setOrderedCombos(OrderedCombosList);
            order.setOrderedItems(orderedItemsList);
            order.setPayFullAmount(false);
            orderRepository.save(order);
        } else {
            throw new EntityNotFoundException();
        }
        sendNotification(order);
        return new OrderResponse(order.getId());
    }

    private void sendNotification(Order order) {
        notificationRepository.save(Notifications
                .builder()
                        .message("new order received "+ order.getDate())
                        .order(order)
                        .date(LocalDate.now())
                        .isOpen(false)
                .build());
    }

    private static void createOrder(OrderDto orderDto, Order order) {
        order.setVenue(orderDto.getVenue());
        order.setPeopleCount(orderDto.getPeopleCount());
        order.setDate(orderDto.getDate());
        order.setOrderDate(LocalDate.now());
        order.setStatus(Status.PENDING);
        order.setTimeFrom(orderDto.getTimeFrom());
        order.setTimeTo(orderDto.getTimeTo());
        order.setDecorationOption(orderDto.getDecorationOption());
    }


    @Override
    public PaymentResponse getTotalAmount(UUID orderId) {
        Float comboPrice = 0f;
        Float itemPrice = 0f;
        float total = 0f;

        Optional<Order> optionalOrders = getOrderById(orderId);
        if (optionalOrders.isPresent()) {
            Order order = optionalOrders.get();
            comboPrice = getComboPrice(comboPrice, order);
            for (OrderedItems items : order.getOrderedItems()) {
                itemPrice += items.getFoodItems().getItemPrice();
            }
            Integer count = order.getPeopleCount();
            total = (comboPrice + itemPrice) * count;
        }
        return new PaymentResponse(total);
    }

    private static Float getComboPrice(Float comboPrice, Order order) {
        for (OrderedCombos combo : order.getOrderedCombos()) {
            Offer offer = combo.getFoodCombos().getOffer();
            if(offer == null){
                comboPrice += combo.getFoodCombos().getComboPrice();
            }else {
                if (offer.isEnabled()) {
                    comboPrice += combo.getFoodCombos().getOfferPrice();
                } else {
                    comboPrice += combo.getFoodCombos().getComboPrice();
                }
            }
        }
        return comboPrice;
    }

    @Override
    public Optional<Order> getOrderById(UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public void addLocation(LocationDto locationDto) {

        UserLocation userLocation = getUserLocation(locationDto);
        Order order = getOrderById(locationDto.getOrderId()).get();
        order.setUserLocation(userLocation);
        userLocationRepository.save(userLocation);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll().stream().
                sorted(Comparator.comparing(Order::getOrderDate).reversed()).toList();
    }

    @Override
    public void acceptOrder(UUID orderId) {
        Optional<Order> optionalOrders = getOrderById(orderId);
        if (optionalOrders.isPresent()) {
            Order order = optionalOrders.get();
            order.setStatus(Status.ACCEPTED);
            orderRepository.save(order);
        }

    }

    @Override
    public List<Order> getOrderByUserId(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            return orderRepository.findByUser(user).stream()
                    .sorted(Comparator.comparing(Order::getOrderDate).reversed()).toList();
        }
        return null;
    }


    @Override
    public Order orderSuccess(OrderSuccessRequest orderSuccessRequest) {
        Optional<Order>optionalOrders = orderRepository.findById(orderSuccessRequest.getOrderId());
        if (optionalOrders.isPresent()){
            Order order = optionalOrders.get();
            order.setTotalAmount(orderSuccessRequest.getTotalAmount());
            order.setAdvanceAmount(orderSuccessRequest.getAdvanceAmount());
            order.setTransactionId(orderSuccessRequest.getTransactionId());
            order.setStatus(Status.CONFIRMED);
            return orderRepository.save(order);
        }
        return null;
    }

@Override
public boolean cancelOrder(UUID orderId) {
    Optional<Order> optionalOrder = orderRepository.findById(orderId);
    if (optionalOrder.isPresent()) {
        Order order = optionalOrder.get();

        if (order.getPeopleCount() < 25 && order.getStatus() != Status.PROCESSING) {
            order.setStatus(Status.CANCELLED);
            orderRepository.save(order);
            return true;
        } else if (order.getPeopleCount() >= 25) {
            return false;
        } else {
            order.setStatus(Status.CANCELLED);
            orderRepository.save(order);
            return true;
        }
    }
    return false;
}


    @Override
    public boolean orderComplete(UUID orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            order.setPayFullAmount(true);
            order.setStatus(Status.COMPLETED);
            orderRepository.save(order);
            return true;
        }else{
            return false;
        }
    }

    private static UserLocation getUserLocation(LocationDto locationDto) {
        UserLocation userLocation = new UserLocation();
        userLocation.setAddress(locationDto.getAddress());
        userLocation.setPlace(locationDto.getPlace());
        userLocation.setDistrict(locationDto.getDistrict());
        userLocation.setName(locationDto.getName());
        userLocation.setLatitude(locationDto.getLatitude());
        userLocation.setLongitude(locationDto.getLongitude());
        return userLocation;
    }

    @Override
    public void confirmPayment(UUID orderId) {
        Order order = getOrderById(orderId).orElseThrow(()->new EntityNotFoundException("order not found"));
        order.setPayFullAmount(true);
        orderRepository.save(order);
    }
}
