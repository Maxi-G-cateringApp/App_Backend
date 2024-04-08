package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.teams.KitchenCrew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenCrewRepository extends JpaRepository<KitchenCrew,Integer> {


}
