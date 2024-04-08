package com.catering_app.Catering_app.service.teamServices.decorationTeam;

import com.catering_app.Catering_app.dto.team.teamDto;
import com.catering_app.Catering_app.model.teams.DecorationTeam;
import com.catering_app.Catering_app.model.teams.ServingTeam;

import java.util.List;
import java.util.Optional;

public interface DecorationTeamService {


    DecorationTeam addDecorationTeam(teamDto teamDto);
    Optional<DecorationTeam> getDecorationTeamById(Integer id);
    List<DecorationTeam> getAllDecorationTeam();


}
