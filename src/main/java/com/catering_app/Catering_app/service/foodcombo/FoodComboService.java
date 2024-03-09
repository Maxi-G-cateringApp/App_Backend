package com.catering_app.Catering_app.service.foodcombo;

import com.catering_app.Catering_app.dto.FoodComboDto;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.FoodItemCombos;

import java.util.List;

public interface FoodComboService {

    boolean addFoodCombo(FoodComboDto foodComboDto);

    List<FoodItemCombos> getAllCombos();
}
