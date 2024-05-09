package com.catering_app.Catering_app.service.teamServices.kitchenCrew;

import com.catering_app.Catering_app.dto.team.teamDto;
import com.catering_app.Catering_app.model.teams.KitchenCrew;
import com.catering_app.Catering_app.model.teams.ServingTeam;

import java.util.List;
import java.util.Optional;

public interface KitchenCrewService {


    KitchenCrew addKitchenCrewTeam(teamDto teamDto);
    Optional<KitchenCrew> getKitchenCrewTeamById(Integer id);
    List<KitchenCrew> getAllSKitchenCrewTeam();
    void deleteKitchenCrewById(Integer id);
}
