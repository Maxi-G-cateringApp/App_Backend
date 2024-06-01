package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.ProfileResponseDto;
import com.catering_app.Catering_app.service.imageService.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/profile-picture/{userId}")
    public ResponseEntity<ProfileResponseDto> changeProfilePicture(@RequestPart MultipartFile file,
                                                                   @PathVariable("userId") UUID userId
    ) throws IOException {
        boolean response = imageService.updateProfilePicture(file, userId);
        ProfileResponseDto profileResponseDto;
        if (response) {
            profileResponseDto = new ProfileResponseDto("success", true);
        } else {
            profileResponseDto = new ProfileResponseDto("failed", false);
        }
        return ResponseEntity.ok(profileResponseDto);
    }

    @PostMapping("/combo-picture/{comboId}")
    public ResponseEntity<?> updateComboPicture(@RequestPart MultipartFile file,
                                                @PathVariable("comboId") Integer comboId
    ) {
        boolean response = imageService.updateComboPicture(file, comboId);
        ProfileResponseDto profileResponseDto;
        if (response) {
            profileResponseDto = new ProfileResponseDto("success", true);
        } else {
            profileResponseDto = new ProfileResponseDto("failed", false);
        }
        return ResponseEntity.ok(profileResponseDto);
    }

    @PostMapping("/item-picture/{itemId}")
    public ResponseEntity<?> updateItemPicture(@RequestPart MultipartFile file,
                                               @PathVariable("itemId") Integer itemId
    ) {
        boolean response = imageService.updateItemPicture(file, itemId);
        ProfileResponseDto profileResponseDto;
        if (response) {
            profileResponseDto = new ProfileResponseDto("success", true);
        } else {
            profileResponseDto = new ProfileResponseDto("failed", false);
        }
        return ResponseEntity.ok(profileResponseDto);
    }


}
