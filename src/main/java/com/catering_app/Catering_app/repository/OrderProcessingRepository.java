package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.OrderProcessing;
import com.catering_app.Catering_app.model.teams.DecorationTeam;
import com.catering_app.Catering_app.model.teams.KitchenCrew;
import com.catering_app.Catering_app.model.teams.ServingTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProcessingRepository extends JpaRepository<OrderProcessing,Integer> {

    List<OrderProcessing> findByServingTeam(ServingTeam servingTeam);
    List<OrderProcessing> findByDecorationTeam(DecorationTeam decorationTeam);
    List<OrderProcessing> findByKitchenCrew(KitchenCrew kitchenCrew);


}
