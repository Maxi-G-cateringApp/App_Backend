package com.catering_app.Catering_app.service.teamServices.kitchenCrewEmployees;

import com.catering_app.Catering_app.dto.team.KitchenCrewEmpDto;
import com.catering_app.Catering_app.dto.team.teamDto;
import com.catering_app.Catering_app.model.Employee;
import com.catering_app.Catering_app.model.teams.KitchenCrew;
import com.catering_app.Catering_app.model.teams.KitchenCrewEmployees;
import com.catering_app.Catering_app.model.teams.ServingTeam;
import com.catering_app.Catering_app.repository.KitchenCrewEmployeesRepository;
import com.catering_app.Catering_app.service.employeeService.EmployeeService;
import com.catering_app.Catering_app.service.teamServices.kitchenCrew.KitchenCrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KitchenCrewEmployeeServiceImpl implements KitchenCrewEmployeeService {

    private final KitchenCrewService kitchenCrewService;
    private final EmployeeService employeeService;
    private final KitchenCrewEmployeesRepository kitchenCrewEmployeesRepository;
    @Override
    public void addKitchenCrewEmployees(KitchenCrewEmpDto kitchenCrewEmpDto) {

        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(kitchenCrewEmpDto.getEmp());
        Optional<KitchenCrew> optionalKitchenCrew = kitchenCrewService.getKitchenCrewTeamById(kitchenCrewEmpDto.getKitchenCrewTeamId());
        if (optionalEmployee.isEmpty()) {
            throw new IllegalArgumentException("Employee not found");
        }
        KitchenCrew kitchenCrew = optionalKitchenCrew.orElseThrow(() -> new IllegalArgumentException("Team not found"));

        KitchenCrewEmployees kitchenCrewEmployees = new KitchenCrewEmployees();
        kitchenCrewEmployees.setEmp(optionalEmployee.get());
        kitchenCrewEmployees.setKitchenCrew(kitchenCrew);
        int count = kitchenCrew.getCount();
        kitchenCrew.setCount(count+1);
        kitchenCrewEmployeesRepository.save(kitchenCrewEmployees);

    }

    @Override
    public List<KitchenCrewEmployees> getAllKitchenCrewEmployees() {
        return kitchenCrewEmployeesRepository.findAll();
    }
}
