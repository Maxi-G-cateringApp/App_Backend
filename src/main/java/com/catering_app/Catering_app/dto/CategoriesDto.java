package com.catering_app.Catering_app.dto;

import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.model.Items;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class CategoriesDto {


    private String categoriesName;
//    private List<FoodItemCombos> foodItemCombos;
//    private List<Items> items;



}
