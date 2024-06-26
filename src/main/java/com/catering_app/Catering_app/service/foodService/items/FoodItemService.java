package com.catering_app.Catering_app.service.foodService.items;

import com.catering_app.Catering_app.dto.FoodItemDto;
import com.catering_app.Catering_app.model.Items;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FoodItemService {

    boolean addFoodItem(FoodItemDto fooItemDto, MultipartFile file);
    List<Items> getAllFoodItems();
    Optional<Items> findByName(String itemName);
    Optional<Items> findById(Integer id);
    List<Items> getItemByCategoryId(Integer id);

    boolean editFoodItem(Integer id, FoodItemDto foodItemDto);

}
