package com.catering_app.Catering_app.service.teamServices.servingEmployees;

import com.catering_app.Catering_app.dto.team.ServingEmpDto;
import com.catering_app.Catering_app.model.teams.ServingEmployees;

import java.util.List;

public interface ServingEmployeesService {

    void addServingEmployees(ServingEmpDto servingEmpDto);
    List<ServingEmployees> getAllServingEmployees();
}
