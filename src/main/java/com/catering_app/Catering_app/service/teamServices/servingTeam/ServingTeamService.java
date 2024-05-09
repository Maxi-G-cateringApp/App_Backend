package com.catering_app.Catering_app.service.teamServices.servingTeam;

import com.catering_app.Catering_app.dto.team.teamDto;
import com.catering_app.Catering_app.model.teams.ServingTeam;

import java.util.List;
import java.util.Optional;

public interface ServingTeamService {

    ServingTeam addServingTeam(teamDto teamDto);
    Optional<ServingTeam>getServingTeamById(Integer id);
    List<ServingTeam> getAllServingTeam();
    void deleteServingTeamById(Integer id);

}
