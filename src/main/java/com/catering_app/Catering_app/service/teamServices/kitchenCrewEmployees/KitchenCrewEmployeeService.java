package com.catering_app.Catering_app.service.teamServices.kitchenCrewEmployees;

import com.catering_app.Catering_app.dto.team.KitchenCrewEmpDto;
import com.catering_app.Catering_app.dto.team.ServingEmpDto;
import com.catering_app.Catering_app.model.teams.KitchenCrewEmployees;
import com.catering_app.Catering_app.model.teams.ServingEmployees;

import java.util.List;

public interface KitchenCrewEmployeeService {

    void addKitchenCrewEmployees(KitchenCrewEmpDto kitchenCrewEmpDto);
    List<KitchenCrewEmployees> getAllKitchenCrewEmployees();
}
