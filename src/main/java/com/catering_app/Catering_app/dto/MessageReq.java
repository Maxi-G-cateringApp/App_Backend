package com.catering_app.Catering_app.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MessageReq {

    private String chatRoomName;
    private String content;
    private MultipartFile file;
    private String senderId;
    private String t_stamp;
    private String type;
    private String fileName;
    private String fileType;
    private String base64Data;
    private boolean seen;
}
