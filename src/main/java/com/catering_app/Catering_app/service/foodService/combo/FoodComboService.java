package com.catering_app.Catering_app.service.foodService.combo;

import com.catering_app.Catering_app.dto.FoodComboDto;
import com.catering_app.Catering_app.model.FoodItemCombos;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FoodComboService {

    boolean addFoodCombo(FoodComboDto foodComboDto,MultipartFile file) throws IOException;
    List<FoodItemCombos> getAllCombos();
    List<FoodItemCombos> getCombosByCategoryId(Integer id);
    Optional<FoodItemCombos> findById(Integer id);
    boolean editFoodCombo(Integer id, FoodComboDto foodComboDto);
    void deleteComboById(Integer id);

    void save(FoodItemCombos foodItemCombos);

}
