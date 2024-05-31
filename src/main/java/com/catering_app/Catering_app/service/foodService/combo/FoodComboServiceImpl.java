package com.catering_app.Catering_app.service.foodService.combo;

import com.catering_app.Catering_app.dto.FoodComboDto;
import com.catering_app.Catering_app.model.Categories;
import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.repository.FoodItemComboRepository;
import com.catering_app.Catering_app.service.categoryService.CategoriesService;
import com.catering_app.Catering_app.service.cloudinaryService.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodComboServiceImpl implements FoodComboService {


    private final FoodItemComboRepository foodItemComboRepository;
    private final CategoriesService categoriesService;
    private final CloudinaryService cloudinaryService;

    @Override
    public boolean addFoodCombo(FoodComboDto foodComboDto, MultipartFile file) throws IOException {
        if (foodComboDto != null) {
            return createFoodComboItem(foodComboDto, file);
        } else {
            return false;
        }
    }

    private boolean createFoodComboItem(FoodComboDto foodComboDto, MultipartFile file) {
        Optional<FoodItemCombos> optionalFoodItemCombos = foodItemComboRepository.findByComboName(foodComboDto.getComboName());
        Optional<Categories> optionalCategories = categoriesService.getCategoryById(foodComboDto.getCategoryId());
        if (optionalFoodItemCombos.isPresent()) {
            return false;
        } else {
            if (optionalCategories.isPresent()) {
                try {
                    Categories category = optionalCategories.get();
                    createNewFoodCombo(foodComboDto, file, category);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    private void createNewFoodCombo(FoodComboDto foodComboDto, MultipartFile file, Categories category) {
        FoodItemCombos foodItemCombo = new FoodItemCombos();
        foodItemCombo.setComboName(foodComboDto.getComboName());
        foodItemCombo.setDescription(foodComboDto.getDescription());
        foodItemCombo.setComboPrice(foodComboDto.getComboPrice());
        foodItemCombo.setCategories(category);
        Map<?, ?> uploadFile = cloudinaryService.uploadImage(file, "FoodCombo_images");
        String imageUrl = (String) uploadFile.get("url");
        String publicId = (String) uploadFile.get("public_id");
        foodItemCombo.setImageUrl(imageUrl);
        foodItemCombo.setImageId(publicId);
        foodItemComboRepository.save(foodItemCombo);
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
        Optional<FoodItemCombos> optionalFoodItemCombo = foodItemComboRepository.findById(id);
        if (optionalFoodItemCombo.isPresent()) {
            FoodItemCombos foodItemCombo = optionalFoodItemCombo.get();

            Optional<FoodItemCombos> existingCombo = foodItemComboRepository.findByComboName(foodComboDto.getComboName());
            if (existingCombo.isPresent() && !existingCombo.get().getId().equals(id)) {
                return false;
            }
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
    public List<FoodItemCombos> getAllCombosWithoutOffer() {
        return foodItemComboRepository.findAll().stream().filter(combo -> combo.getOffer() == null).toList();
    }

    @Override
    public List<FoodItemCombos> getCombosByCategoryId(Integer id) {
        Optional<Categories> optionalCategories = categoriesService.findById(id);
        if (optionalCategories.isPresent()) {
            Categories category = optionalCategories.get();
            if (category.getCategoriesName().equals("All")) {
                return foodItemComboRepository.findAll();
            } else {
                return foodItemComboRepository.findFoodCombosByCategories(category);
            }
        } else {
            return null;
        }
    }

    @Override
    public Optional<FoodItemCombos> findById(Integer id) {
        return foodItemComboRepository.findById(id);
    }

    @Override
    public void save(FoodItemCombos foodItemCombos) {
        foodItemComboRepository.save(foodItemCombos);
    }

    public boolean updateComboPicture(MultipartFile file, Integer comboId) {
        FoodItemCombos foodItemCombo = findById(comboId).orElseThrow(() -> new EntityNotFoundException("Not found"));

        String imageId = foodItemCombo.getImageId();
        try {
            cloudinaryService.delete(imageId);
            Map<?, ?> uploadFile = cloudinaryService.uploadImage(file, "FoodCombo_images");
            String imageUrl = (String) uploadFile.get("url");
            String publicId = (String) uploadFile.get("public_id");
            foodItemCombo.setImageUrl(imageUrl);
            foodItemCombo.setImageId(publicId);
            foodItemComboRepository.save(foodItemCombo);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


}
