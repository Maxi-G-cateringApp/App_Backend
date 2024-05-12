package com.catering_app.Catering_app.service.employeeService;

import com.catering_app.Catering_app.dto.EmployeeDto;
import com.catering_app.Catering_app.model.Employee;
import com.catering_app.Catering_app.model.teams.*;
import com.catering_app.Catering_app.repository.DecorationEmployeeRepository;
import com.catering_app.Catering_app.repository.EmployeeRepository;
import com.catering_app.Catering_app.repository.KitchenCrewEmployeesRepository;
import com.catering_app.Catering_app.repository.ServingEmployeesRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ServingEmployeesRepository servingEmployeesRepository;
    private final KitchenCrewEmployeesRepository kitchenCrewEmployeesRepository;
    private final DecorationEmployeeRepository decorationEmployeeRepository;

    @Override
    public Employee addEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmp_name(employeeDto.getEmp_name());
        employee.setExpertise(employeeDto.getExpertise());
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesWithoutTeam() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<Long> allEmpIdInTeams = getAllEmpIdInTeams();
        return employeeList.stream().filter(employee -> !allEmpIdInTeams.contains(employee.getId())).collect(Collectors.toList());
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    @NotNull
    private List<Long> getAllEmpIdInTeams() {
        List<ServingEmployees> servingEmployeesList = servingEmployeesRepository.findAll();
        List<KitchenCrewEmployees> kitchenCrewEmployeesList = kitchenCrewEmployeesRepository.findAll();
        List<DecorationEmployees> decorationEmployeesList = decorationEmployeeRepository.findAll();
        List<Long> allEmpIdInTeams = servingEmployeesList.stream().map(emp -> emp.getEmp().getId()).collect(Collectors.toList());
        allEmpIdInTeams.addAll(kitchenCrewEmployeesList.stream().map(emp -> emp.getEmp().getId()).toList());
        allEmpIdInTeams.addAll(decorationEmployeesList.stream().map(emp -> emp.getEmp().getId()).toList());
        return allEmpIdInTeams;
    }

    @Override
    public List<Employee> getEmployeesByExpertise(String expertise) {
        return employeeRepository.findByExpertise(expertise);
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
}
