package com.catering_app.Catering_app.dto;

import com.catering_app.Catering_app.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.util.UUID;

public class UserProfileImageDto {

    private String name;
    private String type;
    private String filePath;
    private UUID userId;
}
