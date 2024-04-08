package com.catering_app.Catering_app.model.teams;

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

    private String kitchenCrewEmpName;

    @ManyToOne
    @JoinColumn(name = "kitchenCrew_team_id")
    private KitchenCrew kitchenCrew;
}
