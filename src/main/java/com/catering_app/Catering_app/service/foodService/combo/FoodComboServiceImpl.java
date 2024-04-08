package com.catering_app.Catering_app.service.foodService.combo;

import com.catering_app.Catering_app.dto.FoodComboDto;
import com.catering_app.Catering_app.model.Categories;
import com.catering_app.Catering_app.model.FoodComboImage;
import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.repository.FoodComboImageRepository;
import com.catering_app.Catering_app.repository.FoodItemComboRepository;
import com.catering_app.Catering_app.service.categoryService.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FoodComboServiceImpl implements FoodComboService{



    @Autowired
    private FoodItemComboRepository foodItemComboRepository;
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private FoodComboImageRepository foodComboImageRepository;
    static final String F_UPLOAD_DIR = "/Users/sreekanth/Desktop/Catering_app/src/main/resources/static/comboImages";

    @Override
    public boolean addFoodCombo(FoodComboDto foodComboDto, MultipartFile file) throws IOException {
        if(foodComboDto != null){
            return createFoodCombo(foodComboDto,file);
        }else {
            return false;
        }
    }
    private boolean createFoodCombo(FoodComboDto foodComboDto, MultipartFile file) throws IOException {
        String filePath = F_UPLOAD_DIR + file.getOriginalFilename();

        Optional<Categories> optionalCategories = categoriesService.getCategoryById(foodComboDto.getCategoryId());

        if (optionalCategories.isPresent()) {
            try {
            Categories category = optionalCategories.get();
            FoodItemCombos foodItemCombo = getFoodItemCombos(foodComboDto);
            foodItemCombo.setCategories(category);

            FoodComboImage foodComboImage = new FoodComboImage();
                foodComboImage.setName(file.getOriginalFilename());
                foodComboImage.setType(file.getContentType());
                foodComboImage.setFilePath(filePath);
                foodComboImage.setFoodItemCombo(foodItemCombo);
                foodComboImageRepository.save(foodComboImage);
                file.transferTo(new File(filePath));

                foodItemCombo.setFoodComboImage(foodComboImage);
                foodItemComboRepository.save(foodItemCombo);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    private static FoodItemCombos getFoodItemCombos(FoodComboDto foodComboDto) {

        FoodItemCombos foodItemCombo = new FoodItemCombos();
        foodItemCombo.setComboName(foodComboDto.getComboName());
        foodItemCombo.setDescription(foodComboDto.getDescription());
        foodItemCombo.setComboPrice(foodComboDto.getComboPrice());
        return foodItemCombo;
    }

    @Override
    public boolean editFoodCombo(Integer id, FoodComboDto foodComboDto) {
        Optional<FoodItemCombos> optionalFoodCombo = foodItemComboRepository.findById(id);
        if (optionalFoodCombo.isPresent()) {
            FoodItemCombos foodItemCombo = optionalFoodCombo.get();
            foodItemCombo.setComboName(foodComboDto.getComboName());
            foodItemCombo.setDescription(foodComboDto.getDescription());
            foodItemCombo.setComboPrice(foodComboDto.getComboPrice());
            foodItemComboRepository.save(foodItemCombo);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public List<FoodItemCombos> getAllCombos() {
        return foodItemComboRepository.findAll();
    }

    @Override
    public List<FoodItemCombos> getCombosByCategoryId(Integer id) {
        Optional<Categories> optionalCategories = categoriesService.findById(id);
        if (optionalCategories.isPresent()){
            Categories category = optionalCategories.get();
            if(category.getCategoriesName().equals("All")){
                return foodItemComboRepository.findAll();
            }else {
                return foodItemComboRepository.findFoodCombosByCategories(category);
            }
        }else {
            return null;
        }
    }

    @Override
    public Optional<FoodItemCombos> findById(Integer id) {
        return foodItemComboRepository.findById(id);
    }

    @Override
    public void deleteComboById(Integer id) {
       Optional<FoodItemCombos> optionalFoodItemCombos = foodItemComboRepository.findById(id);
       if (optionalFoodItemCombos.isPresent()){
           FoodItemCombos foodItemCombo = optionalFoodItemCombos.get();
           FoodComboImage foodComboImage = foodItemCombo.getFoodComboImage();
           foodComboImageRepository.delete(foodComboImage);
           foodItemComboRepository.delete(foodItemCombo);
       }else {
           throw new RuntimeException("no item found");
       }
    }
}
