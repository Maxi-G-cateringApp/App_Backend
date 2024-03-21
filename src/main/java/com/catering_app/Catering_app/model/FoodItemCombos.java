package com.catering_app.Catering_app.model;

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

    private String imageFile;
    @Enumerated(EnumType.STRING)
    private Categories category;

//    @ManyToOne
//    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
//    private Categories categories;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Orders order;
}
