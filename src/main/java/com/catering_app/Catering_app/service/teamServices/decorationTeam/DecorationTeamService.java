package com.catering_app.Catering_app.service.teamServices.decorationTeam;

import com.catering_app.Catering_app.dto.team.TeamDto;
import com.catering_app.Catering_app.model.teams.DecorationTeam;

import java.util.List;
import java.util.Optional;

public interface DecorationTeamService {


    DecorationTeam addDecorationTeam(TeamDto teamDto);
    Optional<DecorationTeam> getDecorationTeamById(Integer id);
    List<DecorationTeam> getAllDecorationTeam();
    void deleteDecorationTeamById(Integer id);


}
