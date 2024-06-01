package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.CategoriesDto;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.Categories;
import com.catering_app.Catering_app.service.categoryService.CategoriesService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    private final CategoriesService categoriesService;

    public CategoryController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @PostMapping("/add-category")
    public ResponseEntity<?> addCategories(@RequestBody CategoriesDto categoriesDto){
        Optional<Categories> optionalCategories = categoriesService.findByCategoriesName(categoriesDto.getCategoriesName());
        if (optionalCategories.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data Exist");
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
    @GetMapping("/get/category")
    public ResponseEntity<Categories>getCategoryById(@RequestParam Integer id){
        Optional<Categories>optionalCategories=categoriesService.getCategoryById(id);
        if (optionalCategories.isPresent()){
            return ResponseEntity.ok(optionalCategories.get());
        }else{
            throw new EntityNotFoundException();
        }
    }

    @PutMapping("/edit/category")
    public ResponseEntity<ResponseDto>editCombo(@RequestParam Integer id,
                                                @RequestBody CategoriesDto categoriesDto){
        boolean response = categoriesService.editCategory(id,categoriesDto);
        if (response){
            return ResponseEntity.ok(new ResponseDto(true,"success"));
        }else {
            return ResponseEntity.ok(new ResponseDto(false, "something wrong"));
        }
    }



}
