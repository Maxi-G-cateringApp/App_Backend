package com.catering_app.Catering_app.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ChatMessageData {

    private String chatRoomName;
    private String senderId;
    private String content;
    private MultipartFile file;
    private String type;
}
