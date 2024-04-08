package com.catering_app.Catering_app.model;

import com.catering_app.Catering_app.model.teams.DecorationTeam;
import com.catering_app.Catering_app.model.teams.KitchenCrew;
import com.catering_app.Catering_app.model.teams.ServingTeam;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_processing")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProcessing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Orders orders;

    @ManyToOne
    private ServingTeam servingTeam;

    @ManyToOne
    private DecorationTeam decorationTeam;

    @ManyToOne
    private KitchenCrew kitchenCrew;
}
