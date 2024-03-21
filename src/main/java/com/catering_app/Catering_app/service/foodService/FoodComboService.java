package com.catering_app.Catering_app.service.foodService;

import com.catering_app.Catering_app.dto.FoodComboDto;
import com.catering_app.Catering_app.model.FoodItemCombos;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FoodComboService {

    boolean addFoodCombo(FoodComboDto foodComboDto) throws IOException;

    List<FoodItemCombos> getAllCombos();
    Optional<FoodItemCombos> findById(Integer id);
    boolean editFoodCombo(Integer id, FoodComboDto foodComboDto);
    void deleteComboById(Integer id);

}
