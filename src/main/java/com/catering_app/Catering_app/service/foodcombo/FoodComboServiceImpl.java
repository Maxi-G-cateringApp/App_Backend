package com.catering_app.Catering_app.service.foodcombo;

import com.catering_app.Catering_app.dto.FoodComboDto;
import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.repository.FoodItemComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodComboServiceImpl implements FoodComboService{

    @Autowired
    FoodItemComboRepository foodItemComboRepository;
    @Override
    public boolean addFoodCombo(FoodComboDto foodComboDto) {
        if(foodComboDto != null){
            return CreateFoodCombo(foodComboDto);
        }else {
            return false;
        }
    }

    private boolean CreateFoodCombo(FoodComboDto foodComboDto) {
        FoodItemCombos foodItemCombo = new FoodItemCombos();
        foodItemCombo.setFoodComboName(foodComboDto.getComboName());
        foodItemCombo.setDescription(foodComboDto.getDescription());
        foodItemCombo.setComboPrice(foodComboDto.getComboPrice());
        foodItemComboRepository.save(foodItemCombo);
        return true;
    }

    @Override
    public List<FoodItemCombos> getAllCombos() {
        return foodItemComboRepository.findAll();
    }
}
