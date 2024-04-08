package com.catering_app.Catering_app.controller.imageController;

import com.catering_app.Catering_app.dto.ProfileResponseDto;
import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.repository.UserRepository;
import com.catering_app.Catering_app.service.imageService.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/profile-picture/{userId}")
    public ResponseEntity<ProfileResponseDto> changeProfilePicture(@RequestPart MultipartFile file,
                                                                   @PathVariable("userId") UUID userId,
                                                                   HttpServletRequest httpServletRequest) throws IOException {
        boolean response = imageService.updateProfilePicture(file,userId);
        ProfileResponseDto profileResponseDto ;
        if (response){
            profileResponseDto = new ProfileResponseDto("success",true);
        }else {
            profileResponseDto = new ProfileResponseDto("failed",false);
        }
        return ResponseEntity.ok(profileResponseDto);
    }
    @PostMapping("/combo-picture/{comboId}")
    public ResponseEntity<?> updateComboPicture(@RequestPart MultipartFile file,
                                                @PathVariable("comboId") Integer comboId,
                                                HttpServletRequest httpServletRequest) throws IOException {
        boolean response = imageService.updateComboPicture(file,comboId);
        ProfileResponseDto profileResponseDto ;
        if (response){
            profileResponseDto = new ProfileResponseDto("success",true);
        }else {
            profileResponseDto = new ProfileResponseDto("failed",false);
        }
        return ResponseEntity.ok(profileResponseDto);
    }

    @GetMapping("/get-file/{userId}")
    public ResponseEntity<?> getImage(@PathVariable("userId") UUID userId) throws IOException {
            byte[] imageData = imageService.getImage(userId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(imageData);
    }
    @GetMapping("/get-comboimage/{id}")
    public ResponseEntity<?> getComboImage(@PathVariable("id") Integer id) throws IOException {
        byte[] imageData = imageService.getComboImage(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
