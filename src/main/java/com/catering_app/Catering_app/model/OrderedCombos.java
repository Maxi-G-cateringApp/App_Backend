package com.catering_app.Catering_app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordered_combos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedCombos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "combo_id")
    private FoodItemCombos foodCombos;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Orders orders;
}
