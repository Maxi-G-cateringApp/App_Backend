package com.catering_app.Catering_app.service.imageService;

import com.catering_app.Catering_app.dto.ProfileResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ImageService {

    boolean updateProfilePicture(MultipartFile file, UUID userId) throws IOException;
//    boolean updateComboPicture(MultipartFile file,Integer comboId) throws IOException;

//    byte[] getImage(UUID userId) throws IOException;

//    byte[] getComboImage(Integer comboId)throws IOException;

}
