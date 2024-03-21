package com.catering_app.Catering_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Integer id;
    private String itemName;
    private Float itemPrice;
    private String imageFileName;
    @Enumerated(EnumType.STRING)
    private Categories category;


//    @ManyToOne
//    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
//    private Categories categories;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Orders order;
}
