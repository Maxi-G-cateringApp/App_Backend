package com.catering_app.Catering_app.model.teams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "serving_team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServingTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String teamName;
    @OneToMany(mappedBy = "servingTeam",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ServingEmployees> servingTeamMembers;

}
