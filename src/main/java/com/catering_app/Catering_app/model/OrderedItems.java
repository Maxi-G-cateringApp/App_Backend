package com.catering_app.Catering_app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordered_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedItems {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Items foodItems;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;


}
