package com.catering_app.Catering_app.service.imageService;

import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.repository.UserRepository;
import com.catering_app.Catering_app.service.cloudinaryService.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

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
        }catch (Exception e){
            throw new RuntimeException("Can't upload photo");
        }
    }

//    @Override
//    public boolean updateComboPicture(MultipartFile file, Integer comboId) throws IOException {
//        String filePath = F_UPLOAD_DIR + file.getOriginalFilename();
//        FoodItemCombos foodItemCombo = foodComboService.findById(comboId).get();
//
//        FoodComboImage foodComboImage = foodItemCombo.getFoodComboImage();
//        if (foodComboImage != null) {
//            foodComboImage.setName(file.getOriginalFilename());
//            foodComboImage.setType(file.getContentType());
//            foodComboImage.setFilePath(filePath);
//        } else {
//            FoodComboImage newFoodComboImage = foodComboImageRepository.save(FoodComboImage.builder()
//                    .name(file.getOriginalFilename())
//                    .type(file.getContentType())
//                    .filePath(filePath)
//                    .foodItemCombo(foodItemCombo)
//                    .build());
//            foodItemCombo.setFoodComboImage(newFoodComboImage);
//        }
//            foodItemComboRepository.save(foodItemCombo);
//            file.transferTo(new File(filePath));
//            return true;
//        }

//        @Override
//        public byte[] getImage (UUID userId) throws IOException {
//
//            try {
//                UserProfileImage userProfileImage = imageRepository.findUserProfileImageByUserId(userId);
//                if (userProfileImage != null) {
//                    User user = userProfileImage.getUser();
//                    String filePath = user.getUserProfileImage().getFilePath();
//                    return Files.readAllBytes(new File(filePath).toPath());
//                } else {
//                    return null;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }

//        @Override
//        public byte[] getComboImage (Integer id) throws IOException{
//
//            FoodComboImage foodComboImage = foodComboImageRepository.findFoodCombosImageById(id);
//            try {
//                if (foodComboImage != null) {
//                    FoodItemCombos foodItemCombo = foodComboImage.getFoodItemCombo();
//                    String filePath = foodItemCombo.getFoodComboImage().getFilePath();
//                    return Files.readAllBytes(new File(filePath).toPath());
//                } else {
//                    return null;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
    }


