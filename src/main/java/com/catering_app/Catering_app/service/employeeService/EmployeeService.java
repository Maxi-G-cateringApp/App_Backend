package com.catering_app.Catering_app.service.employeeService;

import com.catering_app.Catering_app.dto.EmployeeDto;
import com.catering_app.Catering_app.model.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {

    Employee addEmployee(EmployeeDto employeeDto);
    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByExpertise(String expertise);
    Optional<Employee>getEmployeeById(Long id);
    List<Employee>getEmployeesWithoutTeam();
    void deleteEmployeeById(Long id);

    void inactiveEmployee(Long id);
    public void activeEmployee(Long id);

    boolean exitByEmail(String email);
    Employee getEmployeeByEmail(String email);
    Object getEmployeeTeam(Employee emp);
}
