package com.catering_app.Catering_app.service.categoryService;

import com.catering_app.Catering_app.dto.CategoriesDto;
import com.catering_app.Catering_app.model.Categories;

import java.util.List;
import java.util.Optional;

public interface CategoriesService {

    boolean addCategory(CategoriesDto categoriesDto);
    Optional<Categories>findByCategoriesName(String categoriesName);
    List<Categories> getAllCategories();

    void deleteCategoryById(Integer id);
    Optional<Categories> getCategoryById(Integer id);

    Optional<Categories> findById(Integer id);

    boolean editCategory(Integer id,CategoriesDto categoriesDto);
}
