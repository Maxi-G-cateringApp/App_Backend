package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findByExpertise(String expertise);
    Optional<Employee>findByEmail(String email);


}
