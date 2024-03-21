package com.catering_app.Catering_app.service.imageService;

import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.model.UserProfileImage;
import com.catering_app.Catering_app.repository.ImageRepository;
import com.catering_app.Catering_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;
@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;

    public static final String UPLOAD_DIR = "/Users/sreekanth/Desktop/Catering_app/src/main/resources/static/images";

    @Override
    public boolean updateProfilePicture(MultipartFile file, UUID userId) throws IOException {
        String filePath = UPLOAD_DIR+file.getOriginalFilename();

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();

            UserProfileImage userProfileImage = user.getUserProfileImage();
            if (userProfileImage != null){
                userProfileImage.setName(file.getOriginalFilename());
                userProfileImage.setType(file.getContentType());
                userProfileImage.setFilePath(filePath);
            }else {
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
        }else {
            return false;
        }
    }

    @Override
    public byte[] getImage(UUID userId) throws IOException {

       try {
           UserProfileImage userProfileImage = imageRepository.findUserProfileImageByUserId(userId);
           if (userProfileImage!= null){
               User user = userProfileImage.getUser();
               String filePath = user.getUserProfileImage().getFilePath();
               return Files.readAllBytes(new File(filePath).toPath());
           }else{
               return null;
           }
       }catch (Exception e){
           e.printStackTrace();
       }
        return null;
    }
}


