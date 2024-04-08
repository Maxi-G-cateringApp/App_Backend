package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.FoodComboImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodComboImageRepository extends JpaRepository<FoodComboImage,Integer> {

    FoodComboImage findFoodCombosImageById(Integer id);
}
