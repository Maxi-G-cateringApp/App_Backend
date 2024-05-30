package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Employee;
import com.catering_app.Catering_app.model.teams.KitchenCrewEmployees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitchenCrewEmployeesRepository extends JpaRepository<KitchenCrewEmployees,Integer> {

    List<KitchenCrewEmployees>getKitchenCrewsByKitchenCrewId(Integer id);
    KitchenCrewEmployees findByEmp(Employee emp);

}
