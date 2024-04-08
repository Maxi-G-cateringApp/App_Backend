package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Categories;
import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Items,Integer> {

    List<Items> findFoodItemByCategories(Categories categories);



}
