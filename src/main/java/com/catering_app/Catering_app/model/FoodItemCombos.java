package com.catering_app.Catering_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "combo_items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemCombos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "combo_id")
    private Integer id;
    private String comboName;
    private String description;
    private Float comboPrice;
    private Float offerPrice;

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    @JsonIgnore
    private Categories categories;

    @OneToOne
    @JoinColumn(name = "image_id")
    @JsonIgnore
    private FoodComboImage foodComboImage;

    @OneToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

}
