package com.catering_app.Catering_app.dto;

import lombok.Data;

@Data
public class MessageReq {

    String chatRoomName;
    String content;
    String senderId;
    String t_stamp;
}
