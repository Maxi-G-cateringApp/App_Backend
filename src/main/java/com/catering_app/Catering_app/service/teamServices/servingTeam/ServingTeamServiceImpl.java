package com.catering_app.Catering_app.service.teamServices.servingTeam;

import com.catering_app.Catering_app.dto.team.teamDto;
import com.catering_app.Catering_app.model.teams.ServingTeam;
import com.catering_app.Catering_app.repository.ServingTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ServingTeamServiceImpl implements ServingTeamService {

    @Autowired
    private ServingTeamRepository servingTeamRepository;

    @Override
    public ServingTeam addServingTeam(@RequestBody teamDto teamDto) {
        return servingTeamRepository.save(ServingTeam.builder().
                teamName(teamDto.getTeamName()).build());
    }

    @Override
    public Optional<ServingTeam> getServingTeamById(Integer id) {
        return servingTeamRepository.findById(id);
    }

    @Override
    public List<ServingTeam> getAllServingTeam() {
        return servingTeamRepository.findAll();
    }

    @Override
    public void deleteServingTeamById(Integer id) {
        servingTeamRepository.deleteById(id);
    }
}
