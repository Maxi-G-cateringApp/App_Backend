package com.catering_app.Catering_app.model.teams;

import com.catering_app.Catering_app.model.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "kitchenCrew_employees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KitchenCrewEmployees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer kitchenCrewEmpId;
    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee emp;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kitchenCrew_team_id")
    private KitchenCrew kitchenCrew;
}
