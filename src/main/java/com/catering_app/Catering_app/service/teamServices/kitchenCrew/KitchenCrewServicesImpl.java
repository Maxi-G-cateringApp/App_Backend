package com.catering_app.Catering_app.service.teamServices.kitchenCrew;

import com.catering_app.Catering_app.dto.team.teamDto;
import com.catering_app.Catering_app.model.teams.KitchenCrew;
import com.catering_app.Catering_app.model.teams.ServingTeam;
import com.catering_app.Catering_app.repository.KitchenCrewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KitchenCrewServicesImpl implements KitchenCrewService{

    @Autowired
    private KitchenCrewRepository kitchenCrewRepository;

    @Override
    public KitchenCrew addKitchenCrewTeam(teamDto teamDto) {
        return kitchenCrewRepository.save(KitchenCrew
                .builder()
                .teamName(teamDto.getTeamName())
                .build());
    }
    @Override
    public Optional<KitchenCrew> getKitchenCrewTeamById(Integer id) {
        return kitchenCrewRepository.findById(id);
    }

    @Override
    public List<KitchenCrew> getAllSKitchenCrewTeam() {
        return kitchenCrewRepository.findAll();
    }

    @Override
    public void deleteKitchenCrewById(Integer id) {
        kitchenCrewRepository.deleteById(id);
    }
}
