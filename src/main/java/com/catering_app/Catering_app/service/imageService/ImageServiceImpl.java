package com.catering_app.Catering_app.service.imageService;

import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.model.Items;
import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.repository.FoodItemComboRepository;
import com.catering_app.Catering_app.repository.ItemsRepository;
import com.catering_app.Catering_app.repository.UserRepository;
import com.catering_app.Catering_app.service.cloudinaryService.CloudinaryService;
import com.catering_app.Catering_app.service.foodService.combo.FoodComboService;
import com.catering_app.Catering_app.service.foodService.items.FoodItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final FoodComboService foodComboService;
    private final FoodItemComboRepository foodItemComboRepository;
    private final ItemsRepository itemsRepository;
    private final FoodItemService foodItemService;

    public ImageServiceImpl(UserRepository userRepository, CloudinaryService cloudinaryService, FoodComboService foodComboService, FoodItemComboRepository foodItemComboRepository, ItemsRepository itemsRepository, FoodItemService foodItemService) {
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.foodComboService = foodComboService;
        this.foodItemComboRepository = foodItemComboRepository;
        this.itemsRepository = itemsRepository;
        this.foodItemService = foodItemService;
    }

    @Override
    public boolean updateProfilePicture(MultipartFile file, UUID userId) throws IOException {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getImageId() != null) {
                cloudinaryService.delete(user.getImageId());
            }
            uploadProfilePicture(file, user);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    private void uploadProfilePicture(MultipartFile file, User user) {
        try {
            Map<?, ?> uploadFile = cloudinaryService.uploadImage(file, "user_profile_img");
            String imageUrl = (String) uploadFile.get("url");
            String publicId = (String) uploadFile.get("public_id");
            user.setImageUrl(imageUrl);
            user.setImageId(publicId);
        } catch (Exception e) {
            throw new RuntimeException("Can't upload photo");
        }
    }

    @Override
    public boolean updateComboPicture(MultipartFile file, Integer comboId) {
        FoodItemCombos foodItemCombo = foodComboService.findById(comboId).orElseThrow(() -> new EntityNotFoundException("Not found"));
        try {
            if(foodItemCombo.getImageId() != null){
                cloudinaryService.delete(foodItemCombo.getImageId());
            }
            uploadComboPicture(file, foodItemCombo);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean updateItemPicture(MultipartFile file, Integer itemId) {
        Items foodItem = foodItemService.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Not found"));
        try {
            if(foodItem.getImageId() != null){
                cloudinaryService.delete(foodItem.getImageId());
            }
            uploadItemPicture(file, foodItem);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void uploadItemPicture(MultipartFile file, Items foodItem) {
        Map<?, ?> uploadFile = cloudinaryService.uploadImage(file, "FoodItem_images");
        String imageUrl = (String) uploadFile.get("url");
        String publicId = (String) uploadFile.get("public_id");
        foodItem.setImageUrl(imageUrl);
        foodItem.setImageId(publicId);
        itemsRepository.save(foodItem);
    }
    private void uploadComboPicture(MultipartFile file, FoodItemCombos foodItemCombo) {
        Map<?, ?> uploadFile = cloudinaryService.uploadImage(file, "FoodCombo_images");
        String imageUrl = (String) uploadFile.get("url");
        String publicId = (String) uploadFile.get("public_id");
        foodItemCombo.setImageUrl(imageUrl);
        foodItemCombo.setImageId(publicId);
        foodItemComboRepository.save(foodItemCombo);
    }

}


