package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.FoodItemCombos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemComboRepository extends JpaRepository<FoodItemCombos, Integer> {
}
