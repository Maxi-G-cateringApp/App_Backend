package com.catering_app.Catering_app.service.categoryService;

import com.catering_app.Catering_app.dto.CategoriesDto;
import com.catering_app.Catering_app.model.Categories;
import com.catering_app.Catering_app.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesServiceImpl implements CategoriesService{

    @Autowired
    private CategoriesRepository categoriesRepository;
    @Override
    public boolean addCategory(CategoriesDto categoriesDto) {
        if (categoriesDto.getCategoriesName() != null){
            Categories categories = new Categories();
            categories.setCategoriesName(categoriesDto.getCategoriesName());
            categoriesRepository.save(categories);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Optional<Categories> findByCategoriesName(String categoriesName) {
        return categoriesRepository.findByCategoriesName(categoriesName);
    }

    @Override
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public void deleteCategoryById(Integer id) {
        categoriesRepository.deleteById(id);

    }

    @Override
    public Optional<Categories> getCategoryById(Integer id) {
        return categoriesRepository.findById(id);
    }

    @Override
    public Optional<Categories> findById(Integer id) {
        return categoriesRepository.findById(id);
    }
}
