package com.catering_app.Catering_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "items")
@Data
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Integer id;
    private String itemName;
    private Float itemPrice;
}
