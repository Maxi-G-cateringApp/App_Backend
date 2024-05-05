package com.catering_app.Catering_app.model.teams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "decoration_team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DecorationTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String teamName;
    private int count;
    @OneToMany(mappedBy = "decorationTeam",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DecorationEmployees> decorationTeamMembers;
}
