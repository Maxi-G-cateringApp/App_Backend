package com.catering_app.Catering_app.service.teamServices.decorationEmployees;

import com.catering_app.Catering_app.dto.team.DecorEmpDto;
import com.catering_app.Catering_app.model.teams.DecorationEmployees;
import com.catering_app.Catering_app.model.teams.DecorationTeam;
import com.catering_app.Catering_app.repository.DecorationEmployeeRepository;
import com.catering_app.Catering_app.service.teamServices.decorationTeam.DecorationTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DecorationEmployeeServiceImpl implements DecorationEmployeeService{

    @Autowired
    private DecorationEmployeeRepository decorationEmployeeRepository;
    @Autowired
    private DecorationTeamService decorationTeamService;

    @Override
    public void addDecorationEmployees(DecorEmpDto decorEmpDto) {
        DecorationEmployees decorationEmployees = new DecorationEmployees();

        Optional<DecorationTeam> optionalDecorationTeam = decorationTeamService.getDecorationTeamById(decorEmpDto.getDecorationTeamId());
        if (optionalDecorationTeam.isPresent()){
            DecorationTeam decorationTeam = optionalDecorationTeam.get();
            decorationEmployees.setDecorationEmpName(decorEmpDto.getDecorationEmpName());
            decorationEmployees.setDecorationTeam(decorationTeam);
            decorationEmployeeRepository.save(decorationEmployees);
        }else{
            throw new RuntimeException("team not found");
        }


    }

    @Override
    public List<DecorationEmployees> getAllDecorationEmployees() {
        return decorationEmployeeRepository.findAll();
    }
}
