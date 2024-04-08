package com.catering_app.Catering_app.service.imageService;

import com.catering_app.Catering_app.model.FoodComboImage;
import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.model.UserProfileImage;
import com.catering_app.Catering_app.repository.FoodComboImageRepository;
import com.catering_app.Catering_app.repository.FoodItemComboRepository;
import com.catering_app.Catering_app.repository.ImageRepository;
import com.catering_app.Catering_app.repository.UserRepository;
import com.catering_app.Catering_app.service.foodService.combo.FoodComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private FoodComboImageRepository foodComboImageRepository;
    @Autowired
    private FoodComboService foodComboService;

    @Autowired
    private FoodItemComboRepository foodItemComboRepository;

    static final String UPLOAD_DIR = "/Users/sreekanth/Desktop/Catering_app/src/main/resources/static/images";
    static final String F_UPLOAD_DIR = "/Users/sreekanth/Desktop/Catering_app/src/main/resources/static/comboImages";


    @Override
    public boolean updateProfilePicture(MultipartFile file, UUID userId) throws IOException {
        String filePath = UPLOAD_DIR + file.getOriginalFilename();

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            UserProfileImage userProfileImage = user.getUserProfileImage();
            if (userProfileImage != null) {
                userProfileImage.setName(file.getOriginalFilename());
                userProfileImage.setType(file.getContentType());
                userProfileImage.setFilePath(filePath);
            } else {
                UserProfileImage newUserProfileImage = imageRepository.save(UserProfileImage.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filePath)
                        .user(user)
                        .build());
                user.setUserProfileImage(newUserProfileImage);
            }
            userRepository.save(user);
            file.transferTo(new File(filePath));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateComboPicture(MultipartFile file, Integer comboId) throws IOException {
        String filePath = F_UPLOAD_DIR + file.getOriginalFilename();
        FoodItemCombos foodItemCombo = foodComboService.findById(comboId).get();

        FoodComboImage foodComboImage = foodItemCombo.getFoodComboImage();
        if (foodComboImage != null) {
            foodComboImage.setName(file.getOriginalFilename());
            foodComboImage.setType(file.getContentType());
            foodComboImage.setFilePath(filePath);
        } else {
            FoodComboImage newFoodComboImage = foodComboImageRepository.save(FoodComboImage.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .filePath(filePath)
                    .foodItemCombo(foodItemCombo)
                    .build());
            foodItemCombo.setFoodComboImage(newFoodComboImage);
        }
            foodItemComboRepository.save(foodItemCombo);
            file.transferTo(new File(filePath));
            return true;
        }

        @Override
        public byte[] getImage (UUID userId) throws IOException {

            try {
                UserProfileImage userProfileImage = imageRepository.findUserProfileImageByUserId(userId);
                if (userProfileImage != null) {
                    User user = userProfileImage.getUser();
                    String filePath = user.getUserProfileImage().getFilePath();
                    return Files.readAllBytes(new File(filePath).toPath());
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public byte[] getComboImage (Integer id) throws IOException{

            FoodComboImage foodComboImage = foodComboImageRepository.findFoodCombosImageById(id);
            try {
                if (foodComboImage != null) {
                    FoodItemCombos foodItemCombo = foodComboImage.getFoodItemCombo();
                    String filePath = foodItemCombo.getFoodComboImage().getFilePath();
                    return Files.readAllBytes(new File(filePath).toPath());
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


