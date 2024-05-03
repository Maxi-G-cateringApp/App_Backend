package com.catering_app.Catering_app.service.employeeService;

import com.catering_app.Catering_app.dto.EmployeeDto;
import com.catering_app.Catering_app.model.Employee;

import java.util.List;

public interface EmployeeService {

    public Employee addEmployee(EmployeeDto employeeDto);

    List<Employee> getAllEmployees();
}
