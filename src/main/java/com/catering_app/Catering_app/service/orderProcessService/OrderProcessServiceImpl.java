package com.catering_app.Catering_app.service.orderProcessService;

import com.catering_app.Catering_app.dto.OrderProcessDto;
import com.catering_app.Catering_app.model.OrderProcessing;
import com.catering_app.Catering_app.model.Order;
import com.catering_app.Catering_app.model.teams.DecorationTeam;
import com.catering_app.Catering_app.model.teams.KitchenCrew;
import com.catering_app.Catering_app.model.teams.ServingTeam;
import com.catering_app.Catering_app.repository.OrderProcessingRepository;
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
    public OrderProcessServiceImpl(OrderService orderService, ServingTeamService servingTeamService, DecorationTeamService decorationTeamService, KitchenCrewService kitchenCrewService, OrderProcessingRepository orderProcessingRepository) {
        this.orderService = orderService;
        this.servingTeamService = servingTeamService;
        this.decorationTeamService = decorationTeamService;
        this.kitchenCrewService = kitchenCrewService;
        this.orderProcessingRepository = orderProcessingRepository;
    }

    @Override
    public OrderProcessing orderProcessing(OrderProcessDto orderProcessDto) {
        OrderProcessing orderProcessing = new OrderProcessing();
        Optional<Order> optionalOrders = orderService.getOrderById(orderProcessDto.getOrderId());
        Optional<ServingTeam> optionalServingTeam = servingTeamService.getServingTeamById(orderProcessDto.getServingTeamId());
        Optional<DecorationTeam> optionalDecorationTeam = decorationTeamService.getDecorationTeamById(orderProcessDto.getDecorationTeamId());
        Optional<KitchenCrew>optionalKitchenCrew = kitchenCrewService.getKitchenCrewTeamById(orderProcessDto.getKitchenCrewId());


        Order order = optionalOrders.orElseThrow(() -> new NoSuchElementException("Order not found"));
        ServingTeam servingTeam = optionalServingTeam.orElseThrow(() -> new NoSuchElementException("Serving team not found"));
        DecorationTeam decorationTeam = optionalDecorationTeam.orElseThrow(() -> new NoSuchElementException("Decoration team not found"));
        KitchenCrew kitchenCrew = optionalKitchenCrew.orElseThrow(() -> new NoSuchElementException("Kitchen crew not found"));

        orderProcessing.setOrder(order);
        orderProcessing.setServingTeam(servingTeam);
        orderProcessing.setDecorationTeam(decorationTeam);
        orderProcessing.setKitchenCrew(kitchenCrew);
        return orderProcessingRepository.save(orderProcessing);

    }
}
