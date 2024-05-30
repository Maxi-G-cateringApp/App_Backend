package com.catering_app.Catering_app.service.teamServices.servingEmployees;

import com.catering_app.Catering_app.dto.team.ServingEmpDto;
import com.catering_app.Catering_app.model.Employee;
import com.catering_app.Catering_app.model.teams.ServingEmployees;
import com.catering_app.Catering_app.model.teams.ServingTeam;
import com.catering_app.Catering_app.repository.ServingEmployeesRepository;
import com.catering_app.Catering_app.repository.ServingTeamRepository;
import com.catering_app.Catering_app.service.employeeService.EmployeeService;
import com.catering_app.Catering_app.service.teamServices.servingTeam.ServingTeamService;
import jakarta.persistence.EntityNotFoundException;
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
    private final ServingTeamRepository servingTeamRepository;
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
        servingEmployees.setActive(true);
        servingEmployees.setEmp(optionalEmployee.get());

        int count = servingTeam.getServingTeamMembers().size();
        servingTeam.setCount(count+1);
        servingEmployeesRepository.save(servingEmployees);
    }


    @Override
    public List<ServingEmployees> getAllServingEmployees() {
        return servingEmployeesRepository.findAll();
    }

    @Override
    public List<ServingEmployees> getAllServingTeamByTeamId(Integer id) {
        return servingEmployeesRepository.getServEmpByServingTeamId(id);
    }

    @Override
    public void removeEmpFromTeam(Integer empId,Integer teamId) {
        ServingTeam servingTeam = servingTeamRepository.findById(teamId).orElseThrow(()->new EntityNotFoundException("Not Found"));

       ServingEmployees servingEmployees = servingEmployeesRepository.findByServingEmpId(empId);
       if (servingEmployees == null || !servingEmployees.getServingTeam().getId().equals(teamId)){
           throw new EntityNotFoundException();
       }
       servingTeam.getServingTeamMembers().remove(servingEmployees);
       servingTeam.setCount(servingTeam.getCount()-1);
       servingEmployees.setEmp(null);
       servingEmployees.setServingTeam(null);

       servingEmployeesRepository.save(servingEmployees);
       servingTeamRepository.save(servingTeam);

    }

    @Override
    public void setMemberAsInactive(Integer empId) {
        ServingEmployees servingEmployees = servingEmployeesRepository.findByServingEmpId(empId);
        servingEmployees.setActive(false);
        servingEmployeesRepository.save(servingEmployees);
    }
}
