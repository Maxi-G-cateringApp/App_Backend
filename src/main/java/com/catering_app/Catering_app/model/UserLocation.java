package com.catering_app.Catering_app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_location")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;
    private String place;
    private String district;
    private double latitude;
    private double longitude;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Order order;
//    @ManyToOne(cascade = CascadeType.ALL)
//    private User user;


}
