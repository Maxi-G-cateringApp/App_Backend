package com.catering_app.Catering_app.service.orderProcessService;

import com.catering_app.Catering_app.dto.OrderProcessDto;
import com.catering_app.Catering_app.model.OrderProcessing;
import com.catering_app.Catering_app.model.Order;
import com.catering_app.Catering_app.model.Status;
import com.catering_app.Catering_app.model.teams.DecorationTeam;
import com.catering_app.Catering_app.model.teams.KitchenCrew;
import com.catering_app.Catering_app.model.teams.ServingTeam;
import com.catering_app.Catering_app.repository.OrderProcessingRepository;
import com.catering_app.Catering_app.repository.OrderRepository;
import com.catering_app.Catering_app.service.orderService.OrderService;
import com.catering_app.Catering_app.service.teamServices.decorationTeam.DecorationTeamService;
import com.catering_app.Catering_app.service.teamServices.kitchenCrew.KitchenCrewService;
import com.catering_app.Catering_app.service.teamServices.servingTeam.ServingTeamService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderProcessServiceImpl implements OrderProcessService{


    private final OrderService orderService;
    private final ServingTeamService servingTeamService;
    private final DecorationTeamService decorationTeamService;
    private final KitchenCrewService kitchenCrewService;
    private final OrderProcessingRepository orderProcessingRepository;
    private final OrderRepository orderRepository;
    public OrderProcessServiceImpl(OrderService orderService, ServingTeamService servingTeamService, DecorationTeamService decorationTeamService, KitchenCrewService kitchenCrewService, OrderProcessingRepository orderProcessingRepository, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.servingTeamService = servingTeamService;
        this.decorationTeamService = decorationTeamService;
        this.kitchenCrewService = kitchenCrewService;
        this.orderProcessingRepository = orderProcessingRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderProcessing orderProcessing(OrderProcessDto orderProcessDto) {
        
        OrderProcessing orderProcessing = new OrderProcessing();
        Optional<Order> optionalOrders = orderService.getOrderById(orderProcessDto.getOrderId());
//        Optional<ServingTeam> optionalServingTeam = servingTeamService.getServingTeamById(orderProcessDto.getServingTeamId());
//        Optional<DecorationTeam> optionalDecorationTeam = decorationTeamService.getDecorationTeamById(orderProcessDto.getDecorationTeamId());
//        Optional<KitchenCrew>optionalKitchenCrew = kitchenCrewService.getKitchenCrewTeamById(orderProcessDto.getKitchenCrewId());


        Order order = optionalOrders.orElseThrow(() -> new NoSuchElementException("Order not found"));
//        ServingTeam servingTeam = optionalServingTeam.orElseThrow(() -> new NoSuchElementException("Serving team not found"));
//        DecorationTeam decorationTeam = optionalDecorationTeam.orElseThrow(() -> new NoSuchElementException("Decoration team not found"));
//        KitchenCrew kitchenCrew = optionalKitchenCrew.orElseThrow(() -> new NoSuchElementException("Kitchen crew not found"));

        if (orderProcessDto.getServingTeamId() != null) {
            Optional<ServingTeam> optionalServingTeam = servingTeamService.getServingTeamById(orderProcessDto.getServingTeamId());
            if (optionalServingTeam.isEmpty()) {
                throw new NoSuchElementException("Serving team not found");
            }
            orderProcessing.setServingTeam(optionalServingTeam.get());
        }

        if (orderProcessDto.getDecorationTeamId() != null) {
            Optional<DecorationTeam> optionalDecorationTeam = decorationTeamService.getDecorationTeamById(orderProcessDto.getDecorationTeamId());
            if (optionalDecorationTeam.isEmpty()) {
                throw new NoSuchElementException("Decoration team not found");
            }
            orderProcessing.setDecorationTeam(optionalDecorationTeam.get());
        }

        if (orderProcessDto.getKitchenCrewId() != null) {
            Optional<KitchenCrew> optionalKitchenCrew = kitchenCrewService.getKitchenCrewTeamById(orderProcessDto.getKitchenCrewId());
            if (optionalKitchenCrew.isEmpty()) {
                throw new NoSuchElementException("Kitchen crew not found");
            }
            orderProcessing.setKitchenCrew(optionalKitchenCrew.get());
        }
        orderProcessing.setOrder(order);

     
        order.setStatus(Status.PROCESSING);
        orderRepository.save(order);
        return orderProcessingRepository.save(orderProcessing);

    }
}
