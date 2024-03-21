package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.UserProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<UserProfileImage,Integer> {

    UserProfileImage findUserProfileImageByUserId(UUID userId);
}
