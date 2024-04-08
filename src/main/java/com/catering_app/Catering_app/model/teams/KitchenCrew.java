package com.catering_app.Catering_app.model.teams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "kitchen_crew")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KitchenCrew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String teamName;
    
    @OneToMany(mappedBy = "kitchenCrew",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<KitchenCrewEmployees> kitchenCrewTeamMembers;

}
