package com.catering_app.Catering_app.service.foodService;

import com.catering_app.Catering_app.dto.FoodComboDto;
import com.catering_app.Catering_app.dto.FoodItemDto;
import com.catering_app.Catering_app.model.Items;

import java.util.List;
import java.util.Optional;

public interface FoodItemService {

    boolean addFoodItem(FoodItemDto fooItemDto);
    List<Items> getAllFoodItems();

    void deleteItemById(Integer id);
    Optional<Items> findById(Integer id);

    boolean editFoodItem(Integer id, FoodItemDto foodItemDto);

}
