package com.catering_app.Catering_app.service.foodService;

import com.catering_app.Catering_app.dto.FoodComboDto;
import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.repository.FoodItemComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FoodComboServiceImpl implements FoodComboService{

//    public static final String UPLOAD_DIR = "/Users/sreekanth/Desktop/Catering_app/src/main/resources/static/images";

    @Autowired
    FoodItemComboRepository foodItemComboRepository;
    @Override
    public boolean addFoodCombo(FoodComboDto foodComboDto) throws IOException {
        if(foodComboDto != null){
            return CreateFoodCombo(foodComboDto);
        }else {
            return false;
        }
    }
    private boolean CreateFoodCombo(FoodComboDto foodComboDto) throws IOException {
        FoodItemCombos foodItemCombo = getFoodItemCombos(foodComboDto);
//        if(imageFile!= null && !imageFile.isEmpty()){
//            String imageId = imageFile.getOriginalFilename();
//            Path fileNameAndPath = Paths.get(UPLOAD_DIR,imageId);
//                Files.write(fileNameAndPath,imageFile.getBytes());
//                foodItemCombo.setImageFile(imageId);
//        }
        foodItemComboRepository.save(foodItemCombo);
        return true;
    }

    private static FoodItemCombos getFoodItemCombos(FoodComboDto foodComboDto) {
        FoodItemCombos foodItemCombo = new FoodItemCombos();
        foodItemCombo.setCategory(foodComboDto.getCategory());
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
    public Optional<FoodItemCombos> findById(Integer id) {
        return foodItemComboRepository.findById(id);
    }

    @Override
    public void deleteComboById(Integer id) {
        if (id != null) {
            foodItemComboRepository.deleteById(id);
        }
    }
}
