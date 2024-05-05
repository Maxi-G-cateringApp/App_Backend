package com.catering_app.Catering_app.service.teamServices.servingEmployees;

import com.catering_app.Catering_app.dto.team.ServingEmpDto;
import com.catering_app.Catering_app.model.Employee;
import com.catering_app.Catering_app.model.teams.ServingEmployees;
import com.catering_app.Catering_app.model.teams.ServingTeam;
import com.catering_app.Catering_app.repository.ServingEmployeesRepository;
import com.catering_app.Catering_app.repository.ServingTeamRepository;
import com.catering_app.Catering_app.service.employeeService.EmployeeService;
import com.catering_app.Catering_app.service.teamServices.servingTeam.ServingTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ServingEmployeesServiceImp implements ServingEmployeesService {


    private final ServingEmployeesRepository servingEmployeesRepository;
    private final ServingTeamService servingTeamService;
    private final EmployeeService employeeService;
    @Override
    public void addServingEmployees(ServingEmpDto servingEmpDto) {
        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(servingEmpDto.getEmp());
        Optional<ServingTeam> optionalServingTeam = servingTeamService.getServingTeamById(servingEmpDto.getServingTeamId());

        if (optionalEmployee.isEmpty()) {
            throw new IllegalArgumentException("Employee not found");
        }

        ServingTeam servingTeam = optionalServingTeam.orElseThrow(() -> new IllegalArgumentException("Team not found"));

        ServingEmployees servingEmployees = new ServingEmployees();
        servingEmployees.setServingTeam(servingTeam);
        servingEmployees.setEmp(optionalEmployee.get());

        int count = servingTeam.getCount();
        servingTeam.setCount(count + 1);
        servingEmployeesRepository.save(servingEmployees);
    }


    @Override
    public List<ServingEmployees> getAllServingEmployees() {
        return servingEmployeesRepository.findAll();
    }
}
