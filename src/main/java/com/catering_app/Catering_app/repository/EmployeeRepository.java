package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
