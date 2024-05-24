package com.catering_app.Catering_app.model.teams;

import com.catering_app.Catering_app.model.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "serving_employees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServingEmployees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer servingEmpId;
    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee emp;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "serving_team_id")
    private ServingTeam servingTeam;
    private boolean isActive;
}
