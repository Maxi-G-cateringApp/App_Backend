package com.catering_app.Catering_app.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "combo_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodComboImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private Integer id;
    private String name;
    private String type;
    private String filePath;
    @OneToOne(mappedBy = "foodComboImage", cascade = CascadeType.ALL)
    @JoinColumn(name = "combo_id")
    private FoodItemCombos foodItemCombo;


}
