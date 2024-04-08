package com.catering_app.Catering_app.model.teams;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "decoration_employees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DecorationEmployees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer decorationEmpId;

    private String decorationEmpName;

    @ManyToOne
    @JoinColumn(name = "decoration_team_id")
    private DecorationTeam decorationTeam;
}
