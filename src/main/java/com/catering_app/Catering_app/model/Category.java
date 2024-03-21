//package com.catering_app.Catering_app.model;
//
//import jakarta.persistence.*;
//
//import lombok.Data;
//
//import java.util.List;
//
//@Entity
//@Table(name = "categories")
//@Data
//public class Categories {
//
//   @Id
//   @GeneratedValue(strategy = GenerationType.AUTO)
//   @Column(name = "category_id")
//   private Integer id;
//   private String categoryName;
//
//   @OneToMany(mappedBy = "categories",cascade = CascadeType.ALL,orphanRemoval = true)
//   private List<FoodItemCombos> foodItemCombos;
//
//   @OneToMany(mappedBy = "categories",cascade = CascadeType.ALL,orphanRemoval = true)
//   private List<Items> items;
//}
