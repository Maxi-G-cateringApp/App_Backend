package com.catering_app.Catering_app.dto;

import com.catering_app.Catering_app.model.Categories;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodItemDto {


    private String itemName;
    private Float itemPrice;
    private Integer categoryId;
}

