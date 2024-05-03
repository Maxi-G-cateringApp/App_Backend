package com.catering_app.Catering_app.dto;

import com.catering_app.Catering_app.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {

    Long id;
    String chatRoomName;
    User user;
}
