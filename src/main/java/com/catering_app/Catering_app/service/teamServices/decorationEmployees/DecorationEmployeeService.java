package com.catering_app.Catering_app.service.teamServices.decorationEmployees;

import com.catering_app.Catering_app.dto.team.DecorEmpDto;
import com.catering_app.Catering_app.dto.team.ServingEmpDto;
import com.catering_app.Catering_app.model.teams.DecorationEmployees;
import com.catering_app.Catering_app.model.teams.ServingEmployees;

import java.util.List;

public interface DecorationEmployeeService {

    void addDecorationEmployees(DecorEmpDto decorEmpDto);
    List<DecorationEmployees> getAllDecorationEmployees();
    List<DecorationEmployees> getDecorationEmpByDecorationTeamId(Integer id);
}
