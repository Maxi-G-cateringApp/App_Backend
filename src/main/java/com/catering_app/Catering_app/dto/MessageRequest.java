package com.catering_app.Catering_app.dto;

import java.util.Date;

public record MessageRequest(String senderId, String recipientId, String content, Date timeStamp) {

}
