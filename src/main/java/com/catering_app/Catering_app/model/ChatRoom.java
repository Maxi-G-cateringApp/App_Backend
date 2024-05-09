package com.catering_app.Catering_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chatRoomName;
    private String participant1;
    private String participant2;

}
