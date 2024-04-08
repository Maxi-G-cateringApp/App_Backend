package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories,Integer> {

    Optional<Categories> findByCategoriesName(String categoriesName);
}
