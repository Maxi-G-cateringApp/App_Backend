package com.catering_app.Catering_app.service.teamServices.servingEmployees;

import com.catering_app.Catering_app.dto.team.ServingEmpDto;
import com.catering_app.Catering_app.model.teams.ServingEmployees;
import com.catering_app.Catering_app.model.teams.ServingTeam;
import com.catering_app.Catering_app.repository.ServingEmployeesRepository;
import com.catering_app.Catering_app.service.teamServices.servingTeam.ServingTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ServingEmployeesServiceImp implements ServingEmployeesService {

    @Autowired
    private ServingEmployeesRepository servingEmployeesRepository;
    @Autowired
    private ServingTeamService servingTeamService;
    @Override
    public void addServingEmployees(ServingEmpDto servingEmpDto) {
        ServingEmployees servingEmployees = new ServingEmployees();

        Optional<ServingTeam> optionalServingTeam = servingTeamService.getServingTeamById(servingEmpDto.getServingTeamId());
        if (optionalServingTeam.isPresent()){
            ServingTeam servingTeam = optionalServingTeam.get();
            servingEmployees.setServingTeam(servingTeam);
            servingEmployees.setServingEmpName(servingEmpDto.getServingEmpName());
            servingEmployeesRepository.save(servingEmployees);
        }else{
            throw new RuntimeException("team not found");
        }



    }

    @Override
    public List<ServingEmployees> getAllServingEmployees() {
        return servingEmployeesRepository.findAll();
    }
}
