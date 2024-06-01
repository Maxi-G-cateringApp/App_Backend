package com.catering_app.Catering_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;
    private String itemName;
    private Float itemPrice;
    private String imageUrl;
    private String imageId;
    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    @JsonIgnore
    private Categories categories;


}
