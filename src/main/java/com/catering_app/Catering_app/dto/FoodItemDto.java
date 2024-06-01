package com.catering_app.Catering_app.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FoodItemDto {


    private String itemName;
    private Float itemPrice;
    private Integer categoryId;
    private MultipartFile file;
}

