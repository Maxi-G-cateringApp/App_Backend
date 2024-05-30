package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Employee;
import com.catering_app.Catering_app.model.teams.DecorationEmployees;
import com.catering_app.Catering_app.model.teams.KitchenCrewEmployees;
import com.catering_app.Catering_app.model.teams.ServingEmployees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecorationEmployeeRepository extends JpaRepository<DecorationEmployees,Integer> {

    List<DecorationEmployees> getDecorationEmployeeByDecorationTeamId(Integer id);
    DecorationEmployees findByEmp (Employee emp);

}
