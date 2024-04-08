package com.catering_app.Catering_app.controller.ItemController;

import com.catering_app.Catering_app.dto.CategoriesDto;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.Categories;
import com.catering_app.Catering_app.service.categoryService.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {


    @Autowired
    private CategoriesService categoriesService;

    @PostMapping("/add-category")
    public ResponseEntity<?> addCategories(@RequestBody CategoriesDto categoriesDto){
        Optional<Categories> optionalCategories = categoriesService.findByCategoriesName(categoriesDto.getCategoriesName());
        if (optionalCategories.isPresent()){
            return ResponseEntity.ok(HttpStatus.CONFLICT);
        }else {
            return ResponseEntity.ok(categoriesService.addCategory(categoriesDto));
        }

    }
    @GetMapping("/all-categories")
    public ResponseEntity<List<Categories>> getAllCategories(){
        return ResponseEntity.ok(categoriesService.getAllCategories());
    }

    @DeleteMapping("/delete-category")
    public ResponseEntity<ResponseDto>deleteCombo(@RequestParam Integer id){
        categoriesService.deleteCategoryById(id);
        return  ResponseEntity.ok(new ResponseDto(true,"delete success"));
    }



}
