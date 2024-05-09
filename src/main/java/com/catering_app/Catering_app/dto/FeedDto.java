package com.catering_app.Catering_app.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class FeedDto {

    private String content;
    private MultipartFile file;
    private UUID userId;

}
