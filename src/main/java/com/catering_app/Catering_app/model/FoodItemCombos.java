package com.catering_app.Catering_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "combo_items")
@Data
public class FoodItemCombos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "combo_id")
    private Integer id;
    private String foodComboName;
    private String description;
    private Float comboPrice;
}
