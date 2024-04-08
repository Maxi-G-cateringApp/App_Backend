package com.catering_app.Catering_app.service.orderService;

import com.catering_app.Catering_app.dto.*;
import com.catering_app.Catering_app.model.*;
import com.catering_app.Catering_app.repository.OrderRepository;
import com.catering_app.Catering_app.repository.UserLocationRepository;
import com.catering_app.Catering_app.repository.UserRepository;
import com.catering_app.Catering_app.service.authService.AuthenticationService;
import com.catering_app.Catering_app.service.eventService.EventService;
import com.catering_app.Catering_app.service.foodService.combo.FoodComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private FoodComboService foodComboService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserLocationRepository userLocationRepository;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserRepository userRepository;



    @Override
    public OrderResponse saveOrder(OrderDto orderDto) {

        Orders orders = new Orders();
        Optional<User> optionalUser = authenticationService.getUserById(orderDto.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            orders.setUser(user);
            createOrder(orderDto, orders);

            List<OrderedCombos> OrderedCombosList = orderDto.getFoodCombos().stream().map(
                    foodCombo -> {
                        OrderedCombos orderedCombos = new OrderedCombos();
                        orderedCombos.setFoodCombos(foodCombo);
                        orderedCombos.setOrders(orders);
                        return orderedCombos;
                    }).toList();
            List<OrderedItems> orderedItemsList = orderDto.getFoodItems().stream().map(
                    item -> {
                        OrderedItems orderedItems = new OrderedItems();
                        orderedItems.setFoodItems(item);
                        orderedItems.setOrders(orders);
                        return orderedItems;
                    }).toList();
            Events event = eventService.getEventById(orderDto.getEventId()).get();
            orders.setEvents(event);
            orders.setOrderedCombos(OrderedCombosList);
            orders.setOrderedItems(orderedItemsList);
            orderRepository.save(orders);
        } else {
            throw new RuntimeException();
        }
        return new OrderResponse(orders.getId());
    }

    private static void createOrder(OrderDto orderDto, Orders orders) {
        orders.setVenue(orderDto.getVenue());
        orders.setPeopleCount(orderDto.getPeopleCount());
        orders.setDate(orderDto.getDate());
        orders.setOrderDate(new Date(System.currentTimeMillis()));
        orders.setStatus(Status.PENDING);
        orders.setTimeFrom(orderDto.getTimeFrom());
        orders.setTimeTo(orderDto.getTimeTo());
        orders.setDecorationOption(orderDto.getDecorationOption());
    }


    @Override
    public PaymentResponse getTotalAmount(UUID orderId) {
        Float comboPrice = 0f;
        Float itemPrice = 0f;
        float total = 0f;

        Optional<Orders> optionalOrders = getOrderById(orderId);
        if (optionalOrders.isPresent()) {
            Orders orders = optionalOrders.get();

            for (OrderedCombos combo : orders.getOrderedCombos()) {
                comboPrice += combo.getFoodCombos().getComboPrice();
            }
            for (OrderedItems items : orders.getOrderedItems()) {
                itemPrice += items.getFoodItems().getItemPrice();
            }
            Integer count = orders.getPeopleCount();
            total = (comboPrice + itemPrice) * count;
        }
        return new PaymentResponse(total);
    }
    @Override
    public Optional<Orders> getOrderById(UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public void addLocation(LocationDto locationDto) {

        UserLocation userLocation = getUserLocation(locationDto);
        Orders order = getOrderById(locationDto.getOrderId()).get();
        userLocation.setOrders(order);
        order.setUserLocation(userLocation);


        userLocationRepository.save(userLocation);
    }

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll().stream().
                sorted(Comparator.comparing(Orders::getOrderDate).reversed()).toList();
    }

    @Override
    public void acceptOrder(UUID orderId) {
        Optional<Orders> optionalOrders = getOrderById(orderId);
        if (optionalOrders.isPresent()) {
            Orders order = optionalOrders.get();
            order.setStatus(Status.ACCEPTED);
            orderRepository.save(order);
        }

    }

    @Override
    public List<Orders> getOrderByUserId(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            return orderRepository.findByUser(user);
        }
        return null;
    }

    @Override
    public Orders orderSuccess(OrderSuccessRequest orderSuccessRequest) {
        Optional<Orders>optionalOrders = orderRepository.findById(orderSuccessRequest.getOrderId());
        if (optionalOrders.isPresent()){
            Orders order = optionalOrders.get();
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
        Optional<Orders> optionalOrders = orderRepository.findById(orderId);
        if(optionalOrders.isPresent()){
            Orders order = optionalOrders.get();
            Date date = order.getDate();
            Date today = new Date();

            long diffInMillies = Math.abs(today.getTime() - date.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (diffInDays > 2){
                order.setStatus(Status.CANCELLED);
                orderRepository.save(order);
                return true;
            }else{
                return false;
            }
        }
        return false;
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
}
