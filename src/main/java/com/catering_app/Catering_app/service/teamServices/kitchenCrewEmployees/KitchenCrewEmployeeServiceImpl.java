package com.catering_app.Catering_app.service.teamServices.kitchenCrewEmployees;

import com.catering_app.Catering_app.dto.team.KitchenCrewEmpDto;
import com.catering_app.Catering_app.dto.team.teamDto;
import com.catering_app.Catering_app.model.teams.KitchenCrew;
import com.catering_app.Catering_app.model.teams.KitchenCrewEmployees;
import com.catering_app.Catering_app.repository.KitchenCrewEmployeesRepository;
import com.catering_app.Catering_app.service.teamServices.kitchenCrew.KitchenCrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KitchenCrewEmployeeServiceImpl implements KitchenCrewEmployeeService {

    @Autowired
    private KitchenCrewService kitchenCrewService;

    @Autowired
    private KitchenCrewEmployeesRepository kitchenCrewEmployeesRepository;
    @Override
    public void addKitchenCrewEmployees(KitchenCrewEmpDto kitchenCrewEmpDto) {

        KitchenCrewEmployees kitchenCrewEmployees = new KitchenCrewEmployees();

        Optional<KitchenCrew> optionalKitchenCrew = kitchenCrewService.getKitchenCrewTeamById(kitchenCrewEmpDto.getKitchenCrewTeamId());
        if (optionalKitchenCrew.isPresent()){
            KitchenCrew kitchenCrew = optionalKitchenCrew.get();
            kitchenCrewEmployees.setKitchenCrewEmpName(kitchenCrewEmpDto.getKitchenCrewEmpName());
            kitchenCrewEmployees.setKitchenCrew(kitchenCrew);
            kitchenCrewEmployeesRepository.save(kitchenCrewEmployees);
        }else {
            throw new RuntimeException("team not found");
        }
    }

    @Override
    public List<KitchenCrewEmployees> getAllKitchenCrewEmployees() {
        return kitchenCrewEmployeesRepository.findAll();
    }
}
