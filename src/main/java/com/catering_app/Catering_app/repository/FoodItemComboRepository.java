package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Categories;
import com.catering_app.Catering_app.model.FoodItemCombos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodItemComboRepository extends JpaRepository<FoodItemCombos, Integer> {

    List<FoodItemCombos>findFoodCombosByCategories(Categories categories);
    Optional<FoodItemCombos> findByComboName(String comboName);
}
