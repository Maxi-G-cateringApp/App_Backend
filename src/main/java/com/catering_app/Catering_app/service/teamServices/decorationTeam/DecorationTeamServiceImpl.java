package com.catering_app.Catering_app.service.teamServices.decorationTeam;

import com.catering_app.Catering_app.dto.team.TeamDto;
import com.catering_app.Catering_app.model.teams.DecorationTeam;
import com.catering_app.Catering_app.repository.DecorationTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DecorationTeamServiceImpl implements DecorationTeamService {

    @Autowired
    private DecorationTeamRepository decorationTeamRepository;


    @Override
    public DecorationTeam addDecorationTeam(TeamDto teamDto) {
        return decorationTeamRepository.save(DecorationTeam.builder()
                .teamName(teamDto.getTeamName())
                .build());
    }

    @Override
    public Optional<DecorationTeam> getDecorationTeamById(Integer id) {
        return decorationTeamRepository.findById(id);
    }

    @Override
    public List<DecorationTeam> getAllDecorationTeam() {
        return decorationTeamRepository.findAll();
    }

    @Override
    public void deleteDecorationTeamById(Integer id) {
        decorationTeamRepository.deleteById(id);
    }
}
