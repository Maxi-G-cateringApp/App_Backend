package com.catering_app.Catering_app.controller;

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

    @PostMapping("/profile-picture/{userId}")
    public ResponseEntity<ProfileResponseDto> changeProfilePicture(@RequestPart MultipartFile file,
                                                                   @PathVariable("userId") UUID userId
                                                                   ) throws IOException {
        boolean response = imageService.updateProfilePicture(file,userId);
        ProfileResponseDto profileResponseDto ;
        if (response){
            profileResponseDto = new ProfileResponseDto("success",true);
        }else {
            profileResponseDto = new ProfileResponseDto("failed",false);
        }
        return ResponseEntity.ok(profileResponseDto);
    }


}
