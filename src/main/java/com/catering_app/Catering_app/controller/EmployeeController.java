package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.EmployeeDto;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.Employee;
import com.catering_app.Catering_app.repository.EmployeeRepository;
import com.catering_app.Catering_app.service.employeeService.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add-employee")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDto employeeDto){
        if (employeeService.exitByEmail(employeeDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(" Email  Exist");
        }else{
            return ResponseEntity.ok(employeeService.addEmployee(employeeDto));
        }
    }
    @GetMapping("/all-employees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    @GetMapping("/employees/without-team")
    public ResponseEntity<List<Employee>> getEmployeesWithoutTeam(){
        return ResponseEntity.ok(employeeService.getEmployeesWithoutTeam());
    }
    @DeleteMapping("/delete/emp")
    public ResponseEntity<?>deleteEmpById(@RequestParam Long id){
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok(new ResponseDto(true,"deleted"));
    }
    @PutMapping("/inactive/emp")
    public ResponseEntity<?>inactiveEmp(@RequestParam Long id){
        employeeService.inactiveEmployee(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/active/emp")
    public ResponseEntity<?>activeEmp(@RequestParam Long id){
        employeeService.activeEmployee(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/get-emp/email")
    public ResponseEntity<Employee>getEmployeeByEmail(@RequestParam String email){
        return ResponseEntity.ok(employeeService.getEmployeeByEmail(email));
    }
    @GetMapping("/get/team/order")
    public ResponseEntity<?>getTeamNdOrders(@RequestParam Long id){
        Employee employee = employeeService.getEmployeeById(id).orElseThrow();
        return ResponseEntity.ok(employeeService.getEmployeeTeam(employee));
    }
}
