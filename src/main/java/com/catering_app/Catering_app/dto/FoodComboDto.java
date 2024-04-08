package com.catering_app.Catering_app.dto;

import com.catering_app.Catering_app.model.Categories;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FoodComboDto {

    private String comboName;
    private String description;
    private Float comboPrice;
    private Integer categoryId;
    private Integer foodComboId;
    private MultipartFile file;
}
