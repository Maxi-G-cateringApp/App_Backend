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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "combo_id")
    private Integer id;
    private String comboName;
    private String description;
    private Float comboPrice;
    private Float offerPrice;
    private String imageUrl;
    private String imageId;

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    @JsonIgnore
    private Categories categories;

    @OneToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

}
