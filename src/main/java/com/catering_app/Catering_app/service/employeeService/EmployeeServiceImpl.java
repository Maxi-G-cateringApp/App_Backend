package com.catering_app.Catering_app.service.employeeService;

import com.catering_app.Catering_app.dto.EmployeeDto;
import com.catering_app.Catering_app.dto.TeamAndOrders;
import com.catering_app.Catering_app.model.*;
import com.catering_app.Catering_app.model.teams.*;
import com.catering_app.Catering_app.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ServingEmployeesRepository servingEmployeesRepository;
    private final KitchenCrewEmployeesRepository kitchenCrewEmployeesRepository;
    private final DecorationEmployeeRepository decorationEmployeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderProcessingRepository orderProcessingRepository;



    @Override
    public Employee addEmployee(EmployeeDto employeeDto) {
        Employee employee = getEmployee(employeeDto);
        User user = getUser(employeeDto);
        userRepository.save(user);
        employeeRepository.save(employee);
                return employee;
    }
    @NotNull
    private User getUser(EmployeeDto employeeDto) {
        User user = new User();
        user.setName(employeeDto.getEmp_name());
        user.setEmail(employeeDto.getEmail());
        user.setRole(Role.EMPLOYEE);
        user.setPhoneNumber(employeeDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode("12345"));
        user.setGoogleSignIn(false);
        user.setActive(true);
        user.setRegisterDateTime(LocalDateTime.now());
        return user;
    }

    @NotNull
    private static Employee getEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmp_name(employeeDto.getEmp_name());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setExpertise(employeeDto.getExpertise());
        employee.setActive(true);
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

    @Override
    public void inactiveEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            employee.setActive(false);
            employeeRepository.save(employee);
        }else{
            throw new EntityNotFoundException();
        }
    }
    @Override
    public void activeEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            employee.setActive(true);
            employeeRepository.save(employee);
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    public boolean exitByEmail(String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        return optionalEmployee.isPresent();
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        Optional<Employee>optionalEmployee =employeeRepository.findByEmail(email);
            if (optionalEmployee.isPresent()){
                return optionalEmployee.get();
            }else{
                throw new EntityNotFoundException();
            }
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

    @Override
    public Object getEmployeeTeam(Employee emp){
        try {
            TeamAndOrders<?> decorationTeam = getTeamAndOrders(emp);
            if (decorationTeam != null) return decorationTeam;
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No team assigned to this employee");
    }

    @Nullable
    private TeamAndOrders<?> getTeamAndOrders(Employee emp) {
        DecorationEmployees decorationEmployees = decorationEmployeeRepository.findByEmp(emp);
        if (decorationEmployees != null) {
            DecorationTeam decorationTeam = decorationEmployees.getDecorationTeam();
            List<OrderProcessing> orderProcessingList = orderProcessingRepository.findByDecorationTeam(decorationTeam);
            List<Order> orders = orderProcessingList.stream().map(OrderProcessing::getOrder).toList();
            return new TeamAndOrders<>(decorationTeam, orders);
        }
        ServingEmployees servingEmployees = servingEmployeesRepository.findByEmp(emp);
        if (servingEmployees != null) {
            ServingTeam servingTeam = servingEmployees.getServingTeam();
            List<OrderProcessing> orderProcessingList = orderProcessingRepository.findByServingTeam(servingTeam);
            List<Order> orders = orderProcessingList.stream().map(OrderProcessing::getOrder).toList();
            return new TeamAndOrders<>(servingTeam, orders);
        }
        KitchenCrewEmployees kitchenCrewEmployees = kitchenCrewEmployeesRepository.findByEmp(emp);
        if (kitchenCrewEmployees != null) {
            KitchenCrew kitchenCrew = kitchenCrewEmployees.getKitchenCrew();
            List<OrderProcessing> orderProcessingList = orderProcessingRepository.findByKitchenCrew(kitchenCrew);
            List<Order>orders = orderProcessingList.stream().map(OrderProcessing::getOrder).toList();
            return new TeamAndOrders<>(kitchenCrew, orders);
        }
        return null;
    }
}
